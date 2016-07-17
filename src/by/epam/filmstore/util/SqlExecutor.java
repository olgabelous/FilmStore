package by.epam.filmstore.util;

import by.epam.filmstore.dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Olga Shahray on 17.07.2016.
 *
 * Функциональный интерфейс.
 * Для реализации функционального интерфейса можно использовать лямбда-выражения для замены анонимного внутреннего класса
 * см. слой service и класс DAOHelper
 */

@FunctionalInterface
public interface SqlExecutor<T> {
    T execute() throws SQLException, DAOException;
}
