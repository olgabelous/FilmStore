package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.poolconnection.ConnectionPool;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.service.IConnectionPoolManager;
import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;

/**
 * Class that manages initialization and destroying of pool connection
 *
 * @see by.epam.filmstore.dao.poolconnection.ConnectionPool
 * @author Olga Shahray
 */
public class ConnectionPoolManagerImpl implements IConnectionPoolManager {

    /**
     * Method that initializes  pool connection
     * @throws ServiceConnectionPoolManagerException
     */
    @Override
    public void init() throws ServiceConnectionPoolManagerException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new ServiceConnectionPoolManagerException(e);
        }
    }

    /**
     * Method that destroys  pool connection
     * @throws ServiceConnectionPoolManagerException
     */
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
