package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.util.ServiceHelper;
import by.epam.filmstore.util.DAOHelper;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User authorize(String login, String password) throws ServiceException {
        if(!ServiceHelper.isLoginValid(login)){
            System.out.println("Wrong login!");
            //throw new ServiceAuthException("Wrong login!");
        }
        if(!ServiceHelper.isPasswordValid(password)){
            System.out.println("Wrong pass!");
            //throw new ServiceAuthException("Wrong password!");
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
    public void saveUser(String name, String email, String pass, String phone) throws ServiceException {

    }
}
