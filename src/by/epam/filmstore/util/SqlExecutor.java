package by.epam.filmstore.util;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.service.exception.ServiceException;

import java.sql.SQLException;

/**
 * Functional interface.
 * Instances of functional interfaces can be created with lambda expressions.
 *
 * @see by.epam.filmstore.service.impl
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
@FunctionalInterface
public interface SqlExecutor<T> {
    T execute() throws SQLException, DAOException, ServiceException;
}
