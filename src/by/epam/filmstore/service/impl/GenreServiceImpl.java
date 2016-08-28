package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IGenreDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IGenreService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 17.08.2016.
 */
public class GenreServiceImpl implements IGenreService {
    @Override
    public boolean delete(int id) throws ServiceException {
        IGenreDAO dao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
        if(id <= 0){
            throw new ServiceException("Genre id must be positive number!");
        }
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
