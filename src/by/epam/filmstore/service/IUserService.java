package by.epam.filmstore.service;

import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.exception.ServiceException;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public interface IUserService {

    User authorize(String login, String password) throws ServiceException;

    void saveUser(String name, String email, String pass, String phone) throws ServiceException;
}
