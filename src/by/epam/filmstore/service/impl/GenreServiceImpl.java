package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IGenreDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class GenreServiceImpl implements IGenreService {

    @Override
    public void save(String genreName) throws ServiceException {
        IGenreDAO dao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
        Genre genre = new Genre(genreName);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(genre);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int id, String genreName) throws ServiceException {
        if(id <= 0){
            throw new ServiceValidationException("Genre id must be positive number!");
        }
        IGenreDAO dao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
        Genre genre = new Genre(id, genreName);
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(genre);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceValidationException("Genre id must be positive number!");
        }
        IGenreDAO dao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Genre> getAll() throws ServiceException {
        IGenreDAO dao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
        try {
            return DAOHelper.execute(() -> dao.getAll());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
