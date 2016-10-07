package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IFilmMakerDAO;
import by.epam.filmstore.dao.PartOfTransaction;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Profession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class allows to perform CRUD operations with entity Film maker.
 * All methods use connection getting from method in class DAOHelper. Connection returns to pool in
 * class DAOHelper
 *
 * @see by.epam.filmstore.dao.impl.AbstractDAO
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class FilmMakerDAOImpl extends AbstractDAO implements IFilmMakerDAO{

    private static final String INSERT_FILM_MAKER = "INSERT INTO allfilmmakers (name, profession) VALUES(?,?)";
    private static final String SELECT_FILM_MAKER = "SELECT id, name, profession FROM allfilmmakers WHERE allfilmmakers.id = ?";
    private static final String DELETE_FILM_MAKER = "DELETE FROM allfilmmakers WHERE allfilmmakers.id=?";
    private static final String SELECT_ALL_FILM_MAKERS = "SELECT id, name, profession FROM allfilmmakers LIMIT ?,?";
    private static final String SELECT_ALL_FILM_MAKERS1 = "SELECT id, name, profession FROM allfilmmakers";
    private static final String UPDATE_FILM_MAKER = "UPDATE allfilmmakers SET name=?, profession=? WHERE id=?";
    private static final String COUNT_FILM_MAKER = "SELECT COUNT(id) FROM allfilmmakers";

    /**
     * Method saves @param filmMaker in database
     * @param filmMaker
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void save(FilmMaker filmMaker) throws DAOException {

        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(INSERT_FILM_MAKER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,filmMaker.getName());
            preparedStatement.setString(2,filmMaker.getProfession().name());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error saving film maker");
            }
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    filmMaker.setId(rs.getInt(1));
                }
                else{
                    throw new DAOException("Error getting film maker id");
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error saving film maker", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
    }

    /**
     * Method updates @param filmMaker in database
     * @param filmMaker
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public void update(FilmMaker filmMaker) throws DAOException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(UPDATE_FILM_MAKER);

            preparedStatement.setString(1,filmMaker.getName());
            preparedStatement.setString(2,filmMaker.getProfession().name());
            preparedStatement.setInt(3,filmMaker.getId());

            int row = preparedStatement.executeUpdate();
            if(row == 0){
                throw new DAOException("Error updating film maker");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error updating film maker", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
    }

    /**
     * @param id of film maker
     * @return boolean result if film maker was deleted
     * @throws DAOException
     */
    @Override
    public boolean delete(int id) throws DAOException {

        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(DELETE_FILM_MAKER);

            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting film maker", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
    }

    /**
     * @param id of required film maker
     * @return FilmMaker
     * @throws DAOException
     */
    @Override
    public FilmMaker get(int id) throws DAOException {
        FilmMaker filmMaker = null;
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_FILM_MAKER);

            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    filmMaker = new FilmMaker();
                    filmMaker.setId(rs.getInt(1));
                    filmMaker.setName(rs.getString(2));
                    filmMaker.setProfession(Profession.valueOf(rs.getString(2).toUpperCase()));
                }
                else{
                    throw new DAOException("Error getting film maker");
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting film maker", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
        return filmMaker;
    }

    /**
     * @param offset - is a start number of selection in db
     * @param count - is a count of required records from db
     * @return a {@code List<FilmMaker>}
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public List<FilmMaker> getAll(int offset, int count) throws DAOException {
        List<FilmMaker> allFilmMakers = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_FILM_MAKERS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, count);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                FilmMaker filmMaker = null;
                while(rs.next()) {
                    filmMaker = new FilmMaker();
                    filmMaker.setId(rs.getInt(1));
                    filmMaker.setName(rs.getString(2));
                    filmMaker.setProfession(Profession.valueOf(rs.getString(3).toUpperCase()));

                    allFilmMakers.add(filmMaker);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all film makers", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
        return allFilmMakers;
    }

    /**
     * @return a {@code List<FilmMaker>} return all film makers from db
     * @throws DAOException
     */
    @Override
    public List<FilmMaker> getAll() throws DAOException {
        List<FilmMaker> allFilmMakers = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(SELECT_ALL_FILM_MAKERS1);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                FilmMaker filmMaker = null;
                while(rs.next()) {
                    filmMaker = new FilmMaker();
                    filmMaker.setId(rs.getInt(1));
                    filmMaker.setName(rs.getString(2));
                    filmMaker.setProfession(Profession.valueOf(rs.getString(3).toUpperCase()));

                    allFilmMakers.add(filmMaker);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all film makers", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
        return allFilmMakers;
    }

    /**
     * @return count of film makers
     * @throws DAOException
     */
    @Override
    @PartOfTransaction
    public int getCountFilmMakers() throws DAOException {
        int count = 0;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = getConnectionFromThreadLocal();
            preparedStatement = connection.prepareStatement(COUNT_FILM_MAKER);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count = rs.getInt(1);
                }
            }
            return count;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting film maker", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing prepared statement in film maker", e);
                }
            }
        }
    }
}
