package by.epam.filmstore.service;

import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;

/**
 * Created by Olga Shahray on 04.08.2016.
 */
public interface IConnectionPoolManager {

    void init() throws ServiceConnectionPoolManagerException;

    void destroy() throws ServiceConnectionPoolManagerException;
}
