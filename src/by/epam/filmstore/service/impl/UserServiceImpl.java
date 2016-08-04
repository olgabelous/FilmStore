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
        if (name==null || phone==null || name.isEmpty() || phone.isEmpty()){
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
}
