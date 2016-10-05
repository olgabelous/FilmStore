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
 * Created by Olga Shahray on 17.07.2016.
 */
public class FilmMakerServiceImpl implements IFilmMakerService {
    private final static String ID_MUST_BE_POSITIVE = "Id must be positive number";
    private final static String MUST_NOT_BE_EMPTY = "Invalid parameters: Fields must not be empty";

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
            DAOHelper.transactionExecute(() -> {
                dao.save(fm);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

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
            DAOHelper.transactionExecute(() -> {
                dao.update(fm);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public PagingListDTO<FilmMaker> getAll(int offset, int count) throws ServiceException {
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        IFilmMakerDAO dao = DAOFactory.getMySqlDAOFactory().getIFilmMakerDAO();
        try {
            return DAOHelper.execute(() -> {
                List<FilmMaker> list = dao.getAll(offset, count);
                int countFM = dao.getCountFilmMakers();
                return new PagingListDTO<FilmMaker>(countFM, list);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
