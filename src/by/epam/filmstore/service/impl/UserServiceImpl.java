package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.util.ServiceHelper;
import by.epam.filmstore.util.DAOHelper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User authorize(String login, String password) throws ServiceException {
        if(!ServiceHelper.isLoginValid(login)){
            throw new ServiceAuthException("Wrong login!");
        }
        if(!ServiceHelper.isPasswordValid(password)){
            throw new ServiceAuthException("Wrong password!");
        }

        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();

        User user;
            try {
                user = DAOHelper.execute(() -> dao.authorize(login, password));
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
            if(user == null){
                throw new ServiceAuthException("Wrong login or password!");
            }
        return user;

    }

    @Override
    public User saveUser(String name, String email, String pass, String phone) throws ServiceException {
        if(!ServiceHelper.isLoginValid(email)){
            throw new ServiceAuthException("Wrong email!");
        }
        if(!ServiceHelper.isPasswordValid(pass)){
            throw new ServiceAuthException("Wrong password!");
        }
        if (ServiceHelper.isNullOrEmpty(name, phone)){
            throw new ServiceAuthException("Fields must not be empty!");
        }
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        User user = new User(name,email,pass,phone,LocalDateTime.now(), Role.USER);
        try {
            final User finalUser = user;
            user = DAOHelper.transactionExecute(() -> dao.save(finalUser));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User get(int id) throws ServiceException {

        if(id <= 0){
            throw new ServiceException("User id must be positive number!");
        }
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        try {
            return DAOHelper.execute(() -> dao.get(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(int id, String... params) throws ServiceException {
        // validation
        if(id <= 0){
            throw new ServiceException("User id must be positive number!");
        }
        if (params.length != 7){
            throw new ServiceException("incorrect params");
        }
        String name = params[0];
        String email = params[1];
        String pass = params[2];
        String phone = params[3];
        String photo = params[4];
        String date = params[5];
        String role = params[6];
        if(!ServiceHelper.isLoginValid(email)){
            throw new ServiceAuthException("Wrong email!");
        }
        if(!ServiceHelper.isPasswordValid(pass)){
            throw new ServiceAuthException("Wrong password!");
        }
        if (ServiceHelper.isNullOrEmpty(name, phone, date, role) ){
            throw new ServiceException("incorrect params");
        }
        LocalDateTime dateReg = LocalDateTime.parse(date);
        Role userRole;
        try{
            userRole = Role.valueOf(role.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new ServiceException("Such role does not exist");
        }
        //end validation

        User user = new User(id, name, email, pass, phone, photo, dateReg, userRole);
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        try {
            DAOHelper.transactionExecute(() -> {
                dao.update(user);
                return null;
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        if(id <= 0){
            throw new ServiceException("User id must be positive number!");
        }
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAll(int limit) throws ServiceException {
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();

        try {
            return DAOHelper.execute(() -> dao.getAll(limit));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
