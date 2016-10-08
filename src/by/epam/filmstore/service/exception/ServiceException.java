package by.epam.filmstore.service.exception;

/**
 * An exception that provides information on errors in service layer
 *
 * @author Olga Shahray
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
