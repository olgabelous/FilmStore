package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.util.DAOHelper;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public class FilmServiceImpl implements IFilmService{

    IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();

    @Override
    public void save(Film film) throws ServiceException {
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
}
