package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.util.DAOHelper;

import java.sql.Connection;

/**
 * Abstract class has a method that allows to get connection from thread local in class DAOHelper.
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public abstract class AbstractDAO {

    /**
     *
     * @return connection or DAOException if connection does not exist
     * @throws ConnectionPoolException
     * @throws DAOException
     */
    protected Connection getConnectionFromThreadLocal() throws ConnectionPoolException, DAOException {
        if(DAOHelper.getCurrentConnection() == null){
            throw new DAOException("Connection doesn't exist");
        }
        else{
            return DAOHelper.getCurrentConnection();
        }
    }
}
