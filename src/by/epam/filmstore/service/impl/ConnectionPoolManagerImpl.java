package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.service.IConnectionPoolManager;
import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;

/**
 * Created by Olga Shahray on 04.08.2016.
 */
public class ConnectionPoolManagerImpl implements IConnectionPoolManager {

    @Override
    public void init() throws ServiceConnectionPoolManagerException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new ServiceConnectionPoolManagerException(e);
        }
    }

    @Override
    public void destroy() throws ServiceConnectionPoolManagerException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.disposeConnectionPool();
        } catch (ConnectionPoolException e) {
            throw new ServiceConnectionPoolManagerException(e);
        }
    }
}
