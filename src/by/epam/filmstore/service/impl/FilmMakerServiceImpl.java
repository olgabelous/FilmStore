package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IFilmMakerDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.service.IFilmMakerService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public class FilmMakerServiceImpl implements IFilmMakerService {

    IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();

    @Override
    public void save(FilmMaker filmMaker) throws ServiceException {
        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(filmMaker);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public FilmMaker get(int id) throws ServiceException {
        try {
            return DAOHelper.execute(() -> dao.get(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<FilmMaker> getAll() throws ServiceException {
        try {
            return DAOHelper.execute(() -> dao.getAll());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
