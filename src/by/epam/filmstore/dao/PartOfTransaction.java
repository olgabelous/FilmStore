package by.epam.filmstore.dao;

/**
 * Interface that indicates that method is a part of transaction and is called from method
 * {@code transactionExecute(SqlExecutor<T> executor)} in class DAOHelper
 *
 * @see by.epam.filmstore.util.DAOHelper
 * @author Olga Shahray
 */
public @interface PartOfTransaction {
}
