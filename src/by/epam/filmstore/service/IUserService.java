package by.epam.filmstore.service;

import by.epam.filmstore.domain.User;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.exception.ServiceException;

/**
 * @author Olga Shahray
 */
public interface IUserService {

    User authorize(String login, String password) throws ServiceException;

    User saveUser(String name, String email, String pass, String confPass, String phone) throws ServiceException;

    User get(int id) throws ServiceException;

    User update(int id, String... param) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    PagingListDTO<User> getAll(int offset, int count) throws ServiceException;

    int checkEmail(String email) throws ServiceException;

    void updateUserPass(User loggedUser, String newPass, String confirmPass)throws ServiceException;
}
