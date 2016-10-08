package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IFilmMakerDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.FilmMaker;
import by.epam.filmstore.domain.Profession;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IFilmMakerService;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceIncorrectParamLengthException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.util.List;

/**
 * <p>Class encapsulates the business logic for the entity Film maker, performing validation,
 * controlling transactions and coordinating responses in the implementation of its operations.</p>
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public class FilmMakerServiceImpl implements IFilmMakerService {
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";
    private final static String MUST_NOT_BE_EMPTY = "Invalid parameters: Fields must not be empty";

    /**
     * Method validates params, creates instance Film maker and pass to dao to save
     * @param params
     * @throws ServiceException
     */
    @Override
    public void save(String... params) throws ServiceException {
        if (params.length != 2){
            throw new ServiceIncorrectParamLengthException("Param length must be 2");
        }
        String name = params[0];
        String prof = params[1];
        if(ServiceValidation.isNullOrEmpty(name, prof)){
            throw new ServiceValidationException(MUST_NOT_BE_EMPTY);
        }
        Profession profession = ServiceValidation.getProfession(prof);

        FilmMaker fm = new FilmMaker(name, profession);
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            DAOHelper.execute(() -> {
                dao.save(fm);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Method validates params, creates instance Film maker and pass to dao to update
     * @param id of film maker
     * @param params
     * @throws ServiceException
     */
    @Override
    public void update(int id, String... params) throws ServiceException {
        if (ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        if(params.length != 2){
            throw new ServiceIncorrectParamLengthException("Param length must be 2");
        }
        String name = params[0];
        String prof = params[1];
        if(ServiceValidation.isNullOrEmpty(name, prof)){
            throw new ServiceException(MUST_NOT_BE_EMPTY);
        }
        Profession profession = ServiceValidation.getProfession(prof);

        FilmMaker fm = new FilmMaker(id, name, profession);
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            DAOHelper.execute(() -> {
                dao.update(fm);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param id of film maker
     * @return boolean result if film maker was deleted
     * @throws ServiceException
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        if (ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param id of film maker
     * @return film maker
     * @throws ServiceException
     */
    @Override
    public FilmMaker get(int id) throws ServiceException {
        if (ServiceValidation.isNotPositive(id)){
            throw new ServiceValidationException(ID_MUST_BE_POSITIVE);
        }
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            return DAOHelper.execute(() -> dao.get(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Return DTO object that contains {@code List<FilmMaker>} displaying on page and count
     * of all film makers.
     * @param offset - is a start number of selection
     * @param count - is a count of required records
     * @return a {@code PagingListDTO<FilmMaker>}
     * @throws ServiceException
     *
     * @see by.epam.filmstore.domain.dto.PagingListDTO
     */
    @Override
    public PagingListDTO<FilmMaker> getAll(int offset, int count) throws ServiceException {
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            return DAOHelper.transactionExecute(() -> {
                List<FilmMaker> list = dao.getAll(offset, count);
                int countFM = dao.getCountFilmMakers();
                return new PagingListDTO<FilmMaker>(countFM, list);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @return a {@code List<FilmMaker>}
     * @throws ServiceException
     */
    @Override
    public List<FilmMaker> getAll() throws ServiceException {
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            return DAOHelper.execute(dao::getAll);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
