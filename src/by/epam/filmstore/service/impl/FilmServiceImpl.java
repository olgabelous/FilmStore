package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.ICommentDAO;
import by.epam.filmstore.dao.IFilmDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Country;
import by.epam.filmstore.domain.Film;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Genre;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceIncorrectParamLengthException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Class encapsulates the business logic for the entity Film, performing validation,
 * controlling transactions and coordinating responses in the implementation of its operations.</p>
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class FilmServiceImpl implements IFilmService{
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";

    /**
     * Method validates params, creates instance Film and passes to dao to save
     * @param genresId {@code String[]}
     * @param filmMakersId {@code String[]}
     * @param params
     * @throws ServiceException
     */
    @Override
    public void save(String[] genresId, String[] filmMakersId, Object... params) throws ServiceException {
        //validation
        if(ServiceValidation.isNullOrEmpty(genresId) || ServiceValidation.isNullOrEmpty(filmMakersId)){
            throw new ServiceValidationException("Genres and film makers must not be empty");
        }
        if (params.length != 9){
            throw new ServiceIncorrectParamLengthException("Param length must be 9");
        }
        List<Genre> genreList = new ArrayList<>();
        List<FilmMaker> filmMakerList = new ArrayList<>();
        try {
            for (String genreId : genresId) {
                int id = Integer.parseInt(genreId);
                if(id <= 0){
                    throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                }
                genreList.add(new Genre(id));
            }
            for (String filmMakerId : filmMakersId) {
                int id = Integer.parseInt(filmMakerId);
                if(id <= 0){
                    throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                }
                filmMakerList.add(new FilmMaker(id));
            }
        }
        catch (NumberFormatException e){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE, e);
        }
        String title = (String)params[0];
        int year = (int)params[1];
        int countryId = (int)params[2];
        int duration = (int)params[3];
        int ageRestriction = (int)params[4];
        double price = (double)params[5];
        String poster = (String)params[6];
        String video = (String)params[7];
        String description = (String)params[8];
        if (ServiceValidation.isNullOrEmpty(title, poster, video, description) || ServiceValidation.isNotPositive(price) ||
                ServiceValidation.isNotPositive(year, countryId, duration, ageRestriction)){
            throw new ServiceValidationException("Invalid arguments. Fields must not be empty or contain negative numbers.");
        }
        Country country = new Country(countryId);
        LocalDate dateAdd = LocalDate.now();
        //validation end
        Film film = new Film(title,year,country,description,duration,ageRestriction, price, poster, video, dateAdd, genreList, filmMakerList);
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

    /**
     * Method validates params, creates instance Film and passes to dao to update
     * @param filmId id of film
     * @param genresId {@code String[]}
     * @param filmMakersId {@code String[]}
     * @param params
     * @throws ServiceException
     */
    @Override
    public void update(int filmId, String[] genresId, String[] filmMakersId, Object... params) throws ServiceException {
        //validation
        if(ServiceValidation.isNullOrEmpty(genresId) || ServiceValidation.isNullOrEmpty(filmMakersId)){
            throw new ServiceValidationException("Genres and film makers must not be empty");
        }
        if (params.length != 10){
            throw new ServiceIncorrectParamLengthException("Param length must be 10");
        }
        List<Genre> genreList = new ArrayList<>();
        List<FilmMaker> filmMakerList = new ArrayList<>();
        try {
            for (String genreId : genresId) {
                int id = Integer.parseInt(genreId);
                if(id <= 0){
                    throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                }
                genreList.add(new Genre(id));
            }
            for (String filmMakerId : filmMakersId) {
                int id = Integer.parseInt(filmMakerId);
                if(id <= 0){
                    throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                }
                filmMakerList.add(new FilmMaker(id));
            }
        }
        catch (NumberFormatException e){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE, e);
        }
        String title = (String)params[0];
        int year = (int)params[1];
        int countryId = (int)params[2];
        int duration = (int)params[3];
        int ageRestriction = (int)params[4];
        double price = (double)params[5];
        String poster = (String)params[6];
        String video = (String)params[7];
        String description = (String)params[8];
        String date = (String)params[9];
        if (ServiceValidation.isNullOrEmpty(title, poster, video, description, date) || ServiceValidation.isNotPositive(price) ||
                ServiceValidation.isNotPositive(year, countryId, duration, ageRestriction)){
            throw new ServiceValidationException("Invalid arguments. Fields must not be empty or contain negative numbers.");
        }
        Country country = new Country(countryId);
        LocalDate dateAdd = LocalDate.parse(date);
        //validation end

        Film film = new Film(title,year,country,description,duration,ageRestriction, price, poster, video, dateAdd, genreList, filmMakerList);
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

    /**
     * @param id of film
     * @return film
     * @throws ServiceException
     */
    @Override
    public Film get(int id) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException("Film id must be positive number!");
        }
        IFilmDAO daoFilm = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        ICommentDAO daoComment = DAOFactory.getMySqlDAOFactory().getICommentDAO();


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

    /**
     * @param id of film
     * @return boolean result if film was deleted
     * @throws ServiceException
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException("Film id must be positive number!");
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param year for search
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return {@code List<Film>}
     * @throws ServiceException
     */
    @Override
    public List<Film> getByYear(int year, int offset, int count) throws ServiceException {
        if(ServiceValidation.isNotPositive(year)){
            throw new ServiceValidationException("Year must be positive number");
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.getByYear(year, offset, count));
        } catch (DAOException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Return DTO object that contains {@code List<Film>} displaying on page and count
     * of all films filtered by given parameters.
     * @param filterParams - filter parameters
     * @param order of selection
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return {@code PagingListDTO<Film>}
     * @throws ServiceException
     *
     * @see by.epam.filmstore.domain.dto.PagingListDTO
     */
    @Override
    public PagingListDTO<Film> getFilteredFilms(Map<String, List<String>> filterParams, String order, int offset, int count) throws ServiceException {
        //validation
        for(Map.Entry<String,List<String>> pair : filterParams.entrySet()){
            List<String> paramIdList = pair.getValue();
            String key = pair.getKey();
            try{
                if(key.equals("genre") || key.equals("country") || key.equals("filmMaker")){
                    for(String paramId : paramIdList){
                       int id = Integer.parseInt(paramId);
                        if(ServiceValidation.isNotPositive(id)){
                            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                        }
                    }
                }
                else if(key.equals("year")){

                }
                else if(key.equals("rating")){
                    double rating = Double.parseDouble(paramIdList.get(0));
                    if(ServiceValidation.isNotPositive(rating)){
                        throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
                    }
                }
                else{
                    throw new ServiceValidationException("Incorrect params. Unknown filter parameter");
                }
            }
            catch(NumberFormatException e){
                throw new ServiceValidationException("Incorrect params. Incorrect parameter type");
            }
        }
        if(ServiceValidation.isNullOrEmpty(order)){
            order = "rating";
        }
        //validation end

        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            final String finalOrder = order;
            return DAOHelper.transactionExecute(() -> {
                List<Film> films = dao.getFilteredFilms(filterParams, finalOrder, offset, count);
                int countFilm = dao.getCountFilm(filterParams);
                return new PagingListDTO<Film>(countFilm, films);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Return DTO object that contains {@code List<Film>} displaying on page and count
     * of all films.
     * @param order of selection
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return {@code PagingListDTO<Film>}
     * @throws ServiceException
     *
     * @see by.epam.filmstore.domain.dto.PagingListDTO
     */
    @Override
    public PagingListDTO<Film> getAll(String order, int offset, int count) throws ServiceException {
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.transactionExecute(() -> {
                List<Film> films = dao.getFilteredFilms(null, order, offset, count);
                int countFilm = dao.getCountFilm(null);
                return new PagingListDTO<Film>(countFilm, films);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Return DTO object that contains {@code List<Film>} displaying on page and count
     * of favorite films of user.
     * @param userId - id of user
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return {@code PagingListDTO<Film>}
     * @throws ServiceException
     *
     * @see by.epam.filmstore.domain.dto.PagingListDTO
     */
    @Override
    public PagingListDTO<Film> getFavoriteFilms(int userId, int offset, int count) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.transactionExecute(() -> {
                List<Film> films = dao.getFavoriteFilms(userId, offset, count);
                int countFilm = dao.getCountFavoriteFilm(userId);
                return new PagingListDTO<Film>(countFilm, films);
            });
        } catch (DAOException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates params and passes to dao to save favorite film of user
     * @param userId - id of user
     * @param filmId - id of film
     * @throws ServiceException
     */
    @Override
    public void saveFavoriteFilm(int userId, int filmId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId, filmId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            DAOHelper.execute(() -> {
                dao.saveFavoriteFilm(userId, filmId);
                return null;
            });
        } catch (DAOException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Methods removes favorite film of user
     * @param userId  - id of user
     * @param filmId - id of film
     * @return boolean result if film was deleted
     * @throws ServiceException
     */
    @Override
    public boolean deleteFavoriteFilm(int userId, int filmId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId, filmId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.deleteFavoriteFilm(userId, filmId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method searches films by query
     * @param searchLine - query for search
     * @return {@code List<Film>}
     * @throws ServiceException
     */
    @Override
    public List<Film> search(String searchLine) throws ServiceException {
        if(ServiceValidation.isNullOrEmpty(searchLine)){
            throw new ServiceValidationException("Query must not be empty");
        }
        String[] keywords = searchLine.split("\\s+");
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.search(keywords));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *  Method checks if film is favorite for user
     * @param userId - id of user
     * @param filmId - id of film
     * @return boolean result if film is favorite
     * @throws ServiceException
     */
    @Override
    public boolean isFavoriteFilm(int userId, int filmId) throws ServiceException {
        if(ServiceValidation.isNotPositive(userId, filmId)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IFilmDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
        try {
            return DAOHelper.execute(() -> dao.isFavoriteFilm(userId, filmId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
