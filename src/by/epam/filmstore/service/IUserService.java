package by.epam.filmstore.service;

import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public interface IUserService {

    User authorize(String login, String password) throws ServiceException;

    User saveUser(String name, String email, String pass, String phone) throws ServiceException;

    User get(int id) throws ServiceException;

    void update(int id, String... param) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    List<User> getAll(int limit) throws ServiceException;

}
