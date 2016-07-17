package by.epam.filmstore.util;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.service.exception.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: gkislin
 * Date: 05.03.14
 */
public interface SqlTransaction<T> {
    T execute() throws SQLException, DAOException;
}
