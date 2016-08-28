package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
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
        IFilmDAO daoFilm = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        ICommentDAO daoComment = DAOFactory.getMySqlDAOFactory().getICommentDAO();

        if(id <= 0){
            throw new ServiceException("Film id must be positive number!");
        }
        final Film[] film = new Film[1];
        try {
            DAOHelper.transactionExecute(() -> {
                film[0] = daoFilm.get(id);
                if(film[0] == null){
                    throw new ServiceException("Wrong film id!");
                }
                film[0].setFilmMakerList(daoFilm.getMakersOfFilm(id));
                film[0].setGenreList(daoFilm.getAllGenresOfFilm(id));
                film[0].setCommentsList(daoComment.getAllOfFilm(id));
                return film[0];
            });

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return film[0];
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        if(id <= 0){
            throw new ServiceException("Film id must be positive number!");
        }
        try {
            return DAOHelper.execute(() -> dao.delete(id));
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
    public List<Film> getByYear(String year, int limit) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            int yearNum = Integer.parseInt(year);
            return DAOHelper.execute(() -> dao.getByYear(yearNum, limit));
        } catch (DAOException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Film> getAll(String order, int limit) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.getAll(order, limit));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
