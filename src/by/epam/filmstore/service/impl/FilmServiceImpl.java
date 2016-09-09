package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 17.07.2016.
 */
public class FilmServiceImpl implements IFilmService{

    @Override
    public void save(String[] genresId, String[] filmMakersId, Object... params) throws ServiceException {
        if(ServiceValidation.isNullOrEmpty(genresId) || ServiceValidation.isNullOrEmpty(filmMakersId) || params.length != 8){
            throw new ServiceValidationException("Invalid arguments");
        }
        List<Genre> genreList = new ArrayList<>();
        List<FilmMaker> filmMakerList = new ArrayList<>();
        try {
            for (String genreId : genresId) {
                int id = Integer.parseInt(genreId);
                if(id <= 0){
                    throw new ServiceValidationException("Id must be positive number");
                }
                genreList.add(new Genre(id));
            }
            for (String filmMakerId : filmMakersId) {
                int id = Integer.parseInt(filmMakerId);
                if(id <= 0){
                    throw new ServiceValidationException("Id must be positive number");
                }
                filmMakerList.add(new FilmMaker(id));
            }
        }
        catch (NumberFormatException e){
            throw new ServiceValidationException("Id must be positive number", e);
        }
        String title = (String)params[0];
        int year = (int)params[1];
        int countryId = (int)params[2];
        int duration = (int)params[3];
        int ageRestriction = (int)params[4];
        double price = (double)params[5];
        String link = (String)params[6];
        String description = (String)params[7];
        if (ServiceValidation.isNullOrEmpty(title, link, description) || ServiceValidation.isNotPositive(price) ||
                ServiceValidation.isNotPositive(year, countryId, duration, ageRestriction)){
            throw new ServiceValidationException("Invalid arguments");
        }
        Country country = new Country(countryId);
        Film film = new Film(title,year,country,description,duration,ageRestriction,price,link,genreList,filmMakerList);
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
    public void update(int filmId, String[] genresId, String[] filmMakersId, Object... params) throws ServiceException {
        if(ServiceValidation.isNullOrEmpty(genresId) || ServiceValidation.isNullOrEmpty(filmMakersId) ||
                ServiceValidation.isNotPositive(filmId) || params.length != 8){
            throw new ServiceValidationException("Invalid arguments");
        }
        List<Genre> genreList = new ArrayList<>();
        List<FilmMaker> filmMakerList = new ArrayList<>();
        try {
            for (String genreId : genresId) {
                int id = Integer.parseInt(genreId);
                if(id <= 0){
                    throw new ServiceValidationException("Id must be positive number");
                }
                genreList.add(new Genre(id));
            }
            for (String filmMakerId : filmMakersId) {
                int id = Integer.parseInt(filmMakerId);
                if(id <= 0){
                    throw new ServiceValidationException("Id must be positive number");
                }
                filmMakerList.add(new FilmMaker(id));
            }
        }
        catch (NumberFormatException e){
            throw new ServiceValidationException("Id must be positive number", e);
        }
        String title = (String)params[0];
        int year = (int)params[1];
        int countryId = (int)params[2];
        int duration = (int)params[3];
        int ageRestriction = (int)params[4];
        double price = (double)params[5];
        String link = (String)params[6];
        String description = (String)params[7];
        if (ServiceValidation.isNullOrEmpty(title, link, description) || ServiceValidation.isNotPositive(price) ||
                ServiceValidation.isNotPositive(year, countryId, duration, ageRestriction)){
            throw new ServiceValidationException("Invalid arguments");
        }
        Country country = new Country(countryId);
        Film film = new Film(title,year,country,description,duration,ageRestriction,price,link,genreList,filmMakerList);
        film.setId(filmId);
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();

        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(film);
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
            throw new ServiceValidationException("Film id must be positive number!");
        }
        final Film[] film = new Film[1];
        try {
            DAOHelper.transactionExecute(() -> {
                film[0] = daoFilm.get(id);
                if(film[0] == null){
                    throw new ServiceValidationException("Wrong film id!");
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
            throw new ServiceValidationException("Film id must be positive number!");
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
    public List<Film> getByYear(String year, int offset, int count) throws ServiceException {
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            int yearNum = Integer.parseInt(year);
            return DAOHelper.execute(() -> dao.getByYear(yearNum, offset, count));
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
