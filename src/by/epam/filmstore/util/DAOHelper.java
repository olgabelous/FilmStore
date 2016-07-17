package by.epam.filmstore.util;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;

import java.sql.Connection;

import java.sql.SQLException;

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
    public static <T> T execute(SqlExecutor<T> executor) throws DAOException {

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
    public static <T> T transactionExecute(SqlExecutor<T> executor) throws DAOException {
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

}
