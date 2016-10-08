package by.epam.filmstore.service;

import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;

/**
 * @author Olga Shahray
 */
public interface IConnectionPoolManager {

    void init() throws ServiceConnectionPoolManagerException;

    void destroy() throws ServiceConnectionPoolManagerException;
}
