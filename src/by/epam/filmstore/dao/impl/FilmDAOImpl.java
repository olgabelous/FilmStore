package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 18.06.2016.
 *
 * Класс FilmDAOImpl позволяет совершить CRUD операции с сущностью Фильм и связанными сущностями Создатели Фильма, Жанры Фильма.
 * В каждом методе используется Connection, полученный из DAOHelper (см. AbstractDAO и DAOHelper).
 * Возврат Connection в пул происходит в DAOHelper
 */
public class FilmDAOImpl extends AbstractDAO implements IFilmDAO {

    private static final String INSERT_FILM = "INSERT INTO films(title, release_year, country_id, description, " +
            "duration, age_restriction, price, link)VALUES(?,?,?,?,?,?,?,?)";
    private static final String INSERT_FILM_GENRE = "INSERT INTO filmgenres (film_id, genre_id) VALUES(?,?)";
    private static final String DELETE_FILM_GENRE = "DELETE FROM filmgenres WHERE film_id=?";
    private static final String INSERT_FILM_MAKER = "INSERT INTO filmmakers (film_id, person_id) VALUES(?,?)";
    private static final String DELETE_FILM_MAKER = "DELETE FROM filmmakers WHERE film_id=?";
    private static final String SELECT_FILM_BY_ID = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, link, rating, films.country_id, country.country FROM Films INNER JOIN Country " +
            "ON films.country_id = country.id WHERE films.id=?";
    private static final String SELECT_FILM_GENRES = "SELECT DISTINCT filmgenres.genre_id, allgenres.genre FROM filmgenres, allgenres" +
            " WHERE allgenres.id = filmgenres.genre_id AND filmgenres.film_id = ?";
    private static final String SELECT_FILM_MAKERS = "SELECT DISTINCT filmmakers.person_id, allfilmmakers.name, allfilmmakers.profession" +
            " FROM allfilmmakers, filmmakers WHERE allfilmmakers.id = filmmakers.person_id AND filmmakers.film_id = ?";
    private static final String DELETE_FILM_BY_TITLE = "DELETE FROM films WHERE title=?";
    private static final String DELETE_FILM = "DELETE FROM films WHERE id=?";
    private static final String SELECT_ALL_FILMS = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, link, rating, films.country_id, country.country FROM Films INNER JOIN Country " +
            "ON films.country_id = country.id ORDER BY ? ASC LIMIT ?";
    private static final String SELECT_FILMS_BY_GENRE = "SELECT DISTINCT films.id, films.title, films.release_year, " +
            "films.description, films.duration, films.age_restriction, films.price, films.link, films.rating " +
            "FROM  FilmGenres, Films WHERE films.id = filmgenres.film_id AND filmgenres.genre_id = " +
            "(SELECT AllGenres.id FROM AllGenres WHERE Allgenres.genre = ?)";
    private static final String SELECT_FILMS_BY_YEAR = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, link, rating FROM Films WHERE films.release_year = ? LIMIT ?";
    private static final String SELECT_FILMS_BY_RATING = "SELECT films.id, title, release_year, description, duration, " +
            "age_restriction, price, link, rating FROM Films WHERE films.rating >= ?";
    private static final String UPDATE_FILM = "UPDATE films SET title=?, release_year=?, country_id=?, description=?, " +
            "duration=?, age_restriction=?, price=?, link=? WHERE id=?";



    @Override
    public void save(Film film) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setInt(2, film.getYear());
            preparedStatement.setInt(3, film.getCountry().getId());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setInt(5, film.getDuration());
            preparedStatement.setInt(6, film.getAgeRestriction());
            preparedStatement.setDouble(7, film.getPrice());
            preparedStatement.setString(8, film.getLink());

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

    @Override
    public void saveFilmGenres(int filmId, List<Genre> genres) throws DAOException {
        PreparedStatement psInsert = null;
        PreparedStatement psDelete = null;

        try {
            Connection connection = getConnection();
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

    @Override
    public void saveFilmMakers(int filmId, List<FilmMaker> filmMakers) throws DAOException {
        PreparedStatement psDelete = null;
        PreparedStatement psInsert = null;

        try {
            Connection connection = getConnection();
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

    @Override
    public void update(Film film) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FILM);

            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setInt(2, film.getYear());
            preparedStatement.setInt(3, film.getCountry().getId());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setInt(5, film.getDuration());
            preparedStatement.setInt(6, film.getAgeRestriction());
            preparedStatement.setDouble(7, film.getPrice());
            preparedStatement.setString(8, film.getLink());
            preparedStatement.setInt(9, film.getId());

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

    @Override
    public boolean delete(int id) throws DAOException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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
            Connection connection = getConnection();
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

    @Override
    public Film get(int id) throws DAOException {
        Film film = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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
                    film.setLink(rs.getString(8));
                    film.setRating(rs.getDouble(9));
                    film.setCountry(new Country(rs.getInt(10), rs.getString(11)));
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

    @Override
    public List<Film> getByGenre(String genre) throws DAOException {
        List<Film> filmList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_FILMS_BY_GENRE);

            preparedStatement.setString(1, genre);
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
                    film.setLink(rs.getString(8));
                    film.setRating(rs.getDouble(9));

                    filmList.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting films by genre", e);
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

    @Override
    public List<Film> getByYear(int year, int limit) throws DAOException {
        List<Film> filmList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_FILMS_BY_YEAR);

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, limit);

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
                    film.setLink(rs.getString(8));
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

    @Override
    public List<Film> getAll(String order, int limit) throws DAOException {
        List<Film> allFilms = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_FILMS);
            preparedStatement.setString(1, order);
            preparedStatement.setInt(2, limit);

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
                    film.setLink(rs.getString(8));
                    film.setRating(rs.getDouble(9));
                    film.setCountry(new Country(rs.getInt(10), rs.getString(11)));

                    allFilms.add(film);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all films", e);
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
        return allFilms;
    }

    @Override
    public List<Genre> getAllGenresOfFilm(int filmId) throws DAOException {
        List<Genre> genreList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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

    @Override
    public List<FilmMaker> getMakersOfFilm(int filmId) throws DAOException {
        List<FilmMaker> personList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnection();
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
