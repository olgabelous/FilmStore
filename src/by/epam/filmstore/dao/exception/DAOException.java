package by.epam.filmstore.dao.exception;

/**
 * An exception that provides information on errors in DAO layer
 *
 * @author Olga Shahray
 */
public class DAOException extends Exception {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
