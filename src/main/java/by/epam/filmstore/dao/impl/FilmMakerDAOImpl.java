package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.IFilmMakerDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Profession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 27.06.2016.
 */
public class FilmMakerDAOImpl implements IFilmMakerDAO{

    private static final String INSERT_FILM_MAKER = "INSERT INTO allfilmmakers (name, profession) VALUES(?,?)";
    private static final String SELECT_FILM_MAKER = "SELECT id, name, profession FROM allfilmmakers WHERE allfilmmakers.id =   ?";
    private static final String DELETE_FILM_MAKER = "DELETE FROM allfilmmakers WHERE allfilmmakers.id=?";
    private static final String SELECT_ALL_FILM_MAKERS = "SELECT id, name, profession FROM allfilmmakers";

    @Override
    public void save(FilmMaker filmMaker) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILM_MAKER, Statement.RETURN_GENERATED_KEYS)
        ){
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
    }

    @Override
    public boolean delete(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FILM_MAKER)) {
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row != 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error deleting film maker", e);
        }
    }

    @Override
    public FilmMaker get(int id) throws DAOException {
        FilmMaker filmMaker = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_MAKER)){
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
        return filmMaker;
    }

    @Override
    public List<FilmMaker> getAll() throws DAOException {
        List<FilmMaker> allFilmMakers = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILM_MAKERS)){
            try(ResultSet rs = preparedStatement.executeQuery()) {
                FilmMaker filmMaker = null;
                while(rs.next()) {
                    filmMaker = new FilmMaker();
                    filmMaker.setId(rs.getInt(1));
                    filmMaker.setName(rs.getString(2));
                    filmMaker.setProfession(Profession.valueOf(rs.getString(2).toUpperCase()));

                    allFilmMakers.add(filmMaker);
                }
            }
        }catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error getting all film makers", e);
        }
        return allFilmMakers;
    }
}
