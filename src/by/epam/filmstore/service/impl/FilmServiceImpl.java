package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public class FilmServiceImpl implements IFilmService{

    @Override
    public void save(Film film) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();

        try {
            DAOHelper.transactionExecute(() -> {
                dao.save(film);
                dao.saveFilmMakers(film.getId(), film.getFilmMakerList());
                dao.saveFilmGenres(film.getId(), film.getGenreList());
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Film get(int id) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.get(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Film> getByGenre(String genre) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.getByGenre(genre));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Film> getByYear(String year) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            int yearNum = Integer.parseInt(year);
            return DAOHelper.execute(() -> dao.getByYear(yearNum));
        } catch (DAOException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Film> getAll() throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.getAll());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
