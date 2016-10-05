package by.epam.filmstore.util;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.service.exception.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga Shahray on 17.07.2016.
 *
 * Вспомогательный класс для слоя DAO
 */
public class DAOHelper {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //ThreadLocal позволяет имея одну переменную Connection, иметь различное значение для каждого из потоков.
    //данная переменная необходима для передачи Connection в методы DAO
    //см. AbstractDAO
    private static ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    //получение Connection из ThreadLocal
    public static Connection getCurrentConnection() {
        return currentConnection.get();
    }

    //private констуктор необходим, чтобы нельзя было создать экземпляр класса
    private DAOHelper() {}


    //Параметризованный НЕтранзакционный метод выполнения запросов
    //В качестве паметра принимает функциональный интерфейс SqlExecutor<T>
    //коннекшн берем из пула с помощью конструкции try-with-resources. Метод close() вызывается автоматически и
    //возвращает connection в пул
    public static <T> T execute(SqlExecutor<T> executor) throws DAOException, ServiceException {

        try (Connection conn = connectionPool.takeConnection()) {
            //помещаем connection в ThreadLocal для передачи в DAO
            currentConnection.set(conn);
            return executor.execute();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            //удаляем из ThreadLocal
            currentConnection.remove();
        }
    }

    //Параметризованный транзакционный метод выполнения запросов
    //используется для операций, требующих проведения в рамках транзакции, например, получения фильма со списком актеров
    //В качестве паметра принимает функциональный интерфейс SqlExecutor<T>
    public static <T> T transactionExecute(SqlExecutor<T> executor) throws DAOException, ServiceException {
        try (Connection conn = connectionPool.takeConnection()) {
            currentConnection.set(conn);
            try {
                conn.setAutoCommit(false);
                T res = executor.execute();
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw new DAOException(e);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            currentConnection.remove();
        }
    }

    public static String buildFilterFilmQuery(Map<String, List<String>> filteredParams){
        StringBuilder start1 = new StringBuilder("SELECT DISTINCT films.id, films.title, films.release_year, films.description, films.duration, "+
                "films.age_restriction, films.price, films.poster, films.rating, films.country_id, country.country, films.date_add ");
        StringBuilder from = new StringBuilder("FROM ");
        String innerJoin = "  films INNER JOIN country ON films.country_id = country.id ";
        StringBuilder where = new StringBuilder(" WHERE ");
        String and = " AND ";
        String or = " OR ";
        String eq = " = ";
        String br = " >= ";
        String lr = " < ";
        String opRB = "(";
        String clRB = ")";
        String order = " ORDER BY ? DESC";
        String end = " LIMIT ?, ?";
        if(filteredParams==null || filteredParams.isEmpty()){
            return start1.append(from).append(innerJoin).append(order).append(end).toString();
        }

        for(Map.Entry<String, List<String>> pair : filteredParams.entrySet()){
            String key = pair.getKey();
            List<String> valueList = pair.getValue();
            if (key.equals("rating")){
                where.append(key).append(br).append(valueList.get(0)).append(and);
            }
            else if(key.equals("year")){
                if(valueList.size()==1){
                    where.append("release_year").append(eq).append(valueList.get(0)).append(and);
                }
                else {
                    where.append(opRB).append("release_year").append(br).append(valueList.get(0)).append(and);
                    where.append("release_year").append(lr).append(valueList.get(1)).append(clRB).append(and);
                }
            }
            else if(key.equals("country")){
                where.append(opRB);
                for (String countryId: valueList) {
                    where.append("country_id").append(eq).append(countryId).append(or);
                }
                where.delete(where.lastIndexOf(or), where.length()).append(clRB).append(and);
            }
            else if(key.equals("filmMaker")){
                where.append("films.id = filmmakers.film_id and ");
                for (String filmMakerId: valueList) {
                    where.append("filmmakers.person_id").append(eq).append(filmMakerId).append(or);
                }
                where.replace(where.lastIndexOf(or), where.length(), and);
                from.append(" filmmakers, ");
            }
            else if(key.equals("genre")){
                where.append("films.id = filmgenres.film_id AND ");
                where.append(opRB);
                for (String filmGenreId: valueList) {
                    where.append("filmgenres.genre_id").append(eq).append(filmGenreId).append(or);
                }
                where.delete(where.lastIndexOf(or), where.length()).append(clRB).append(and);
                from.append(" filmgenres, ");
            }
        }
        where.delete(where.lastIndexOf(and), where.length());
        return start1.append(from).append(innerJoin).append(where).append(order).append(end).toString();

    }

    public static String buildCountFilmQuery(Map<String, List<String>> filteredParams){

        StringBuilder start2 = new StringBuilder("SELECT COUNT(DISTINCT films.id) ");
        StringBuilder from = new StringBuilder("FROM films");
        StringBuilder where = new StringBuilder(" WHERE ");
        String and = " AND ";
        String or = " OR ";
        String eq = " = ";
        String br = " >= ";
        String lr = " < ";

        if(filteredParams==null || filteredParams.isEmpty()){
            return start2.append(from).toString();
        }
        for(Map.Entry<String, List<String>> pair : filteredParams.entrySet()){
            String key = pair.getKey();
            List<String> valueList = pair.getValue();
            if (key.equals("rating")){
                where.append(key).append(br).append(valueList.get(0)).append(and);
            }
            else if(key.equals("year")){
                if(valueList.size()==1){
                    where.append("release_year").append(eq).append(valueList.get(0)).append(and);
                }
                else {
                    where.append("release_year").append(br).append(valueList.get(0)).append(and);
                    where.append("release_year").append(lr).append(valueList.get(1)).append(and);
                }
            }
            else if(key.equals("country")){
                for (String countryId: valueList) {
                    where.append("country_id").append(eq).append(countryId).append(or);
                }
                where.replace(where.lastIndexOf(or), where.length(), and);
            }
            else if(key.equals("filmMaker")){
                where.append("films.id = filmmakers.film_id and ");
                for (String filmMakerId: valueList) {
                    where.append("filmmakers.person_id").append(eq).append(filmMakerId).append(or);
                }
                where.replace(where.lastIndexOf(or), where.length(), and);
                from.append(", filmmakers");
            }
            else if(key.equals("genre")){
                where.append("films.id = filmgenres.film_id AND ");
                for (String filmGenreId: valueList) {
                    where.append("filmgenres.genre_id").append(eq).append(filmGenreId).append(or);
                }
                where.replace(where.lastIndexOf(or), where.length(), and);
                from.append(", filmgenres");
            }
        }
        where.delete(where.lastIndexOf(and), where.length());
        return start2.append(from).append(where).toString();
    }

    public static String buildSearchedFilmQuery(String[] keywords) {
        StringBuilder startQuery = new StringBuilder("SELECT DISTINCT films.id, films.title, films.release_year, films.description, films.duration, "+
                "films.age_restriction, films.price, films.poster, films.rating, films.country_id, country.country, films.date_add " +
                "FROM films INNER JOIN country ON films.country_id = country.id, filmmakers INNER JOIN allfilmmakers ON " +
                "filmmakers.person_id = allfilmmakers.id WHERE films.id=filmmakers.film_id ");
        StringBuilder startSP0 = new StringBuilder("AND (");
        String searchPart1 = "(films.title LIKE '%";
        String searchPart2 = "%' OR country.country LIKE '%";
        String searchPart3 = "%' OR allfilmmakers.name LIKE '%";
        String endSP4 = "%')";
        String endQuery = ")";
        String or = " OR ";

        String end = " LIMIT ?, ?";
        if(keywords == null || keywords.length == 0){
            return startQuery.toString();
        }

        for(String keyword : keywords){
            startSP0.append(searchPart1).append(keyword)
                    .append(searchPart2).append(keyword)
                    .append(searchPart3).append(keyword)
                    .append(endSP4).append(or);
        }
        startSP0.delete(startSP0.lastIndexOf(or), startSP0.length());
        String s = startQuery.append(startSP0).append(endQuery).toString();
        return s;
    }
}
