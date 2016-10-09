package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.*;
import by.epam.filmstore.util.DAOHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class allows to perform CRUD operations with entity Film.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class FilmDAOImpl extends AbstractDAO implements IFilmDAO {

    private static final String INSERT_FILM = "INSERT INTO films(title, release_year, country_id, description, " +
            "duration, age_restriction, price, poster, video, date_add)VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_FILM_GENRE = "INSERT INTO filmgenres (film_id, genre_id) VALUES(?,?)";
    private static final String DELETE_FILM_GENRE = "DELETE FROM filmgenres WHERE film_id=?";
    private static final String INSERT_FILM_MAKER = "INSERT INTO filmmakers (film_id, person_id) VALUES(?,?)";
    private static final String DELETE_FILM_MAKER = "DELETE FROM filmmakers WHERE film_id=?";
    private static final String SELECT_FILM_BY_ID = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, poster, video, date_add, rating, films.country_id, country.country FROM films INNER JOIN country " +
            "ON films.country_id = country.id WHERE films.id=?";
    private static final String SELECT_FILM_GENRES = "SELECT DISTINCT filmgenres.genre_id, allgenres.genre FROM filmgenres, allgenres" +
            " WHERE allgenres.id = filmgenres.genre_id AND filmgenres.film_id = ?";
    private static final String SELECT_FILM_MAKERS = "SELECT DISTINCT filmmakers.person_id, allfilmmakers.name, allfilmmakers.profession" +
            " FROM allfilmmakers, filmmakers WHERE allfilmmakers.id = filmmakers.person_id AND filmmakers.film_id = ?";
    private static final String DELETE_FILM_BY_TITLE = "DELETE FROM films WHERE title=?";
    private static final String DELETE_FILM = "DELETE FROM films WHERE id=?";
    private static final String SELECT_FILMS_BY_YEAR = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, poster, rating FROM films WHERE films.release_year = ? LIMIT ?, ?";
    private static final String UPDATE_FILM = "UPDATE films SET title=?, release_year=?, country_id=?, description=?, " +
            "duration=?, age_restriction=?, price=?, poster=?, video=? WHERE id=?";
    private static final String COUNT_FAVORITE_FILMS = "SELECT count(films.id) FROM preferences, films where films.id=preferences.film_id AND preferences.user_id=?";
    private static final String SELECT_FAVORITE_FILMS = "SELECT films.id, title, release_year, duration, " +
            "age_restriction, price, poster, rating, country_id, country FROM preferences, films inner join country ON " +
            "country_id=country.id WHERE films.id=preferences.film_id AND preferences.user_id=? LIMIT ?, ?";
    private static final String INSERT_FAVORITE_FILM = "INSERT INTO preferences(user_id, film_id)VALUES(?,?)";
    private static final String DELETE_FAVORITE_FILM = "DELETE FROM preferences WHERE user_id=? AND film_id=?";
    private static final String COUNT_FAVORITE_FILM = "SELECT count(*) FROM preferences where preferences.user_id=? " +
            "AND preferences.film_id = ?";


    /**
     * Method saves @param film in database
     * @param film
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void save(Film film) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setInt(2, film.getYear());
            preparedStatement.setInt(3, film.getCountry().getId());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setInt(5, film.getDuration());
            preparedStatement.setInt(6, film.getAgeRestriction());
            preparedStatement.setDouble(7, film.getPrice());
            preparedStatement.setString(8, film.getPoster());
            preparedStatement.setString(9, film.getVideo());
            preparedStatement.setDate(10, Date.valueOf(film.getDateAdd()));

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving film");
            }
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    film.setId(rs.getInt(1));
                }
                else{
                    throw new DAOException("Error getting film id");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * Method saves @param {@code List<Genre>} in database
     * @param filmId - id of film which genres belong to
     * @param genres
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void saveFilmGenres(int filmId, List<Genre> genres) throws DAOException {
        PreparedStatement psInsert = null;
        PreparedStatement psDelete = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            psInsert = connection.prepareStatement(INSERT_FILM_GENRE);
            psDelete = connection.prepareStatement(DELETE_FILM_GENRE);

            psDelete.setInt(1, filmId);
            psDelete.executeUpdate();

            for(Genre genre : genres){
                psInsert.setInt(1, filmId);
                psInsert.setInt(2, genre.getId());
                psInsert.addBatch();
            }
            int[] rows = psInsert.executeBatch();
            for (int row : rows) {
                if(row == 0){
                    throw new DAOException("Error saving film genre");
                }
            }
        }
        catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving film genre", e);
        }
        finally {
            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
            if(psDelete
                    != null){
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement1 in film dao", e);
                }
            }
        }
    }

    /**
     * Method saves @param {@code List<FilmMaker>} in database
     * @param filmId- id of film which film makers belong to
     * @param filmMakers
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void saveFilmMakers(int filmId, List<FilmMaker> filmMakers) throws DAOException {
        PreparedStatement psDelete = null;
        PreparedStatement psInsert = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            psInsert = connection.prepareStatement(INSERT_FILM_MAKER);
            psDelete = connection.prepareStatement(DELETE_FILM_MAKER);

            psDelete.setInt(1, filmId);
            psDelete.executeUpdate();

            for(FilmMaker person: filmMakers){
                psInsert.setInt(1, filmId);
                psInsert.setInt(2, person.getId());
                psInsert.addBatch();
            }
            int[] rows = psInsert.executeBatch();

            for (int row : rows) {
                if(row == 0){
                    throw new DAOException("Error saving film makers");
                }
            }
        }
        catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving film makers", e);
        }
        finally {
            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
            if(psDelete != null){
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * Method updates @param film in database
     * @param film
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void update(Film film) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_FILM);

            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setInt(2, film.getYear());
            preparedStatement.setInt(3, film.getCountry().getId());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setInt(5, film.getDuration());
            preparedStatement.setInt(6, film.getAgeRestriction());
            preparedStatement.setDouble(7, film.getPrice());
            preparedStatement.setString(8, film.getPoster());
            preparedStatement.setString(9, film.getVideo());
            preparedStatement.setInt(10, film.getId());

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new DAOException("Error updating film");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param id of film
     * @return boolean result if film was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_FILM);

            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    //only for testing
    @Override
    public boolean deleteByTitle(String title) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_FILM_BY_TITLE);

            preparedStatement.setString(1, title);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param id of film
     * @return Film with given id
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public Film get(int id) throws DAOException {
        Film film = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_FILM_BY_ID);

            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    film = new Film();
                    film.setId(rs.getInt(1));
                    film.setTitle(rs.getString(2));
                    film.setYear(rs.getDate(3).toLocalDate().getYear());
                    film.setDescription(rs.getString(4));
                    film.setDuration(rs.getInt(5));
                    film.setAgeRestriction(rs.getInt(6));
                    film.setPrice(rs.getDouble(7));
                    film.setPoster(rs.getString(8));
                    film.setVideo(rs.getString(9));
                    film.setDateAdd(rs.getDate(10).toLocalDate());
                    film.setRating(rs.getDouble(11));
                    film.setCountry(new Country(rs.getInt(12), rs.getString(13)));
                }
                else{
                    throw new DAOException("Error getting film by id");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting film by id", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return film;
    }

    /**
     * @param filterParams {@code Map<String, List<String>>} where key is name of parameter and
     *                                                      value is list of parameter's values
     * @param orderBy - order of selection
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<Film>} films according to filter parameters
     * @throws DAOException
     *
     * @see by.epam.filmstore.util.DAOHelper
     */
    @Override
    @PartOfTransaction
    public List<Film> getFilteredFilms(Map<String, List<String>> filterParams, String orderBy, int offset, int count) throws DAOException {
        List<Film> filmList = new ArrayList<>();
        PreparedStatement prStatFimls = null;
        String query = DAOHelper.buildFilterFilmQuery(filterParams, orderBy);
        try {
            Connection connection = getConnectionFromThreadLocal();
            prStatFimls = connection.prepareStatement(query);

            prStatFimls.setInt(1, offset);
            prStatFimls.setInt(2, count);
            try(ResultSet rs = prStatFimls.executeQuery()) {
                Film film = null;
                while(rs.next()) {
                    film = new Film();
                    film.setId(rs.getInt(1));
                    film.setTitle(rs.getString(2));
                    film.setYear(rs.getDate(3).toLocalDate().getYear());
                    film.setDescription(rs.getString(4));
                    film.setDuration(rs.getInt(5));
                    film.setAgeRestriction(rs.getInt(6));
                    film.setPrice(rs.getDouble(7));
                    film.setPoster(rs.getString(8));
                    film.setRating(rs.getDouble(9));
                    film.setCountry(new Country(rs.getInt(10), rs.getString(11)));
                    film.setDateAdd(rs.getDate(12).toLocalDate());

                    filmList.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting films by filter", e);
        }
        finally {
            if(prStatFimls != null){
                try {
                    prStatFimls.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return filmList;
    }

    /**
     * @param filterParams {@code Map<String, List<String>>} where key is name of parameter and
     *                                                      value is list of parameter's values
     * @return count of films according to filter parameters
     * @throws DAOException
     * @see by.epam.filmstore.util.DAOHelper
     */
    @Override
    @PartOfTransaction
    public int getCountFilm(Map<String, List<String>> filterParams) throws DAOException {
       int filmCount = 0;

        PreparedStatement prStatCount = null;
        String query = DAOHelper.buildCountFilmQuery(filterParams);
        try {
            Connection connection = getConnectionFromThreadLocal();
            prStatCount = connection.prepareStatement(query);

            try(ResultSet rs = prStatCount.executeQuery()) {
                if(rs.next()) {
                    filmCount = rs.getInt(1);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting films by filter", e);
        }
        finally {
            if(prStatCount != null){
                try {
                    prStatCount.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return filmCount;
    }

    /**
     * @param userId - id of user who marked films as favorite
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<Film>}
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<Film> getFavoriteFilms(int userId, int offset, int count)  throws DAOException {
        List<Film> filmList = new ArrayList<>();
        PreparedStatement prStatFilms = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            prStatFilms = connection.prepareStatement(SELECT_FAVORITE_FILMS);

            prStatFilms.setInt(1, userId);
            prStatFilms.setInt(2, offset);
            prStatFilms.setInt(3, count);
            try(ResultSet rs = prStatFilms.executeQuery()) {
                Film film = null;
                while(rs.next()) {
                    film = new Film();
                    film.setId(rs.getInt(1));
                    film.setTitle(rs.getString(2));
                    film.setYear(rs.getDate(3).toLocalDate().getYear());
                    film.setDuration(rs.getInt(4));
                    film.setAgeRestriction(rs.getInt(5));
                    film.setPrice(rs.getDouble(6));
                    film.setPoster(rs.getString(7));
                    film.setRating(rs.getDouble(8));
                    film.setCountry(new Country(rs.getInt(9), rs.getString(10)));

                    filmList.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting favorite films", e);
        }
        finally {
            if(prStatFilms != null){
                try {
                    prStatFilms.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return filmList;
    }

    /**
     * @param userId - id of user who marked films as favorite
     * @return count of favorite films of given user
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public int getCountFavoriteFilm(int userId) throws DAOException {
        int count = 0;
        PreparedStatement prStatCount = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            prStatCount = connection.prepareStatement(COUNT_FAVORITE_FILMS);
            prStatCount.setInt(1, userId);

            try(ResultSet rs = prStatCount.executeQuery()) {
                if(rs.next()) {
                    count=rs.getInt(1);
                }
            }
            return count;
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting favorite films count", e);
        }
        finally {
            if(prStatCount != null){
                try {
                    prStatCount.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param userId - id of user who marked film as favorite
     * @param filmId - id of favorite user's film
     * @throws DAOException
     */
    @Override
    public void saveFavoriteFilm(int userId, int filmId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_FAVORITE_FILM);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving favorite film");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param userId - id of user who marked film as favorite
     * @param filmId - id of favorite user's film
     * @return boolean result if film was deleted
     * @throws DAOException
     */
    @Override
    public boolean deleteFavoriteFilm(int userId, int filmId) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_FAVORITE_FILM);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);

            int row = preparedStatement.executeUpdate();
            return row != 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting favorite film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param keywords - array of words for search films
     * @return a {@code List<Film>}
     * @throws DAOException
     */
    @Override
    public List<Film> search(String[] keywords) throws DAOException {
        List<Film> filmList = new ArrayList<>();

        PreparedStatement prStatFimls = null;
        String query = DAOHelper.buildSearchedFilmQuery(keywords);
        try {
            Connection connection = getConnectionFromThreadLocal();
            prStatFimls = connection.prepareStatement(query);

            try(ResultSet rs = prStatFimls.executeQuery()) {
                Film film = null;
                while(rs.next()) {
                    film = new Film();
                    film.setId(rs.getInt(1));
                    film.setTitle(rs.getString(2));
                    film.setYear(rs.getDate(3).toLocalDate().getYear());
                    film.setDescription(rs.getString(4));
                    film.setDuration(rs.getInt(5));
                    film.setAgeRestriction(rs.getInt(6));
                    film.setPrice(rs.getDouble(7));
                    film.setPoster(rs.getString(8));
                    film.setRating(rs.getDouble(9));
                    film.setCountry(new Country(rs.getInt(10), rs.getString(11)));
                    film.setDateAdd(rs.getDate(12).toLocalDate());

                    filmList.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error seaching films", e);
        }
        finally {
            if(prStatFimls != null){
                try {
                    prStatFimls.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return filmList;
    }

    /**
     * Method checks if given film is favorite for given user
     * @param userId - id of user
     * @param filmId - id of film
     * @return boolean result if film is favorite
     * @throws DAOException
     */
    @Override
    public boolean isFavoriteFilm(int userId, int filmId) throws DAOException {
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(COUNT_FAVORITE_FILM);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count = rs.getInt(1);
                }
            }
            return count == 1;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error gettint count favorite film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
    }

    /**
     * @param year - release year of film
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<Film>}
     * @throws DAOException
     */
    @Override
    public List<Film> getByYear(int year, int offset, int count) throws DAOException {
        List<Film> filmList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_FILMS_BY_YEAR);

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, count);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                Film film = null;
                while(rs.next()) {
                    film = new Film();
                    film.setId(rs.getInt(1));
                    film.setTitle(rs.getString(2));
                    film.setYear(rs.getDate(3).toLocalDate().getYear());
                    film.setDescription(rs.getString(4));
                    film.setDuration(rs.getInt(5));
                    film.setAgeRestriction(rs.getInt(6));
                    film.setPrice(rs.getDouble(7));
                    film.setPoster(rs.getString(8));
                    film.setRating(rs.getDouble(9));

                    filmList.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting films by year", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return filmList;
    }

    /**
     * @param filmId - id of film which genres belong to
     * @return  a {@code List<Genre>} of given film
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<Genre> getAllGenresOfFilm(int filmId) throws DAOException {
        List<Genre> genreList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_FILM_GENRES);

            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Genre genre = null;
                while (rs.next()) {
                    genre = new Genre();
                    genre.setId(rs.getInt(1));
                    genre.setGenreName(rs.getString(2));
                    genreList.add(genre);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting getting genres of film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return genreList;
    }

    /**
     * @param filmId - id of film which film makers belong to
     * @return  a {@code List<FilmMaker>} of given film
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<FilmMaker> getMakersOfFilm(int filmId) throws DAOException {
        List<FilmMaker> personList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_FILM_MAKERS);

            preparedStatement.setInt(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                FilmMaker person = null;
                while (rs.next()) {
                    person = new FilmMaker();
                    person.setId(rs.getInt(1));
                    person.setName(rs.getString(2));
                    person.setProfession(Profession.valueOf(rs.getString(3).toUpperCase()));
                    personList.add(person);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting getting genres of film", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film dao", e);
                }
            }
        }
        return personList;
    }

}
