package by.epam.filmstore.service.exception;

/**
 * An exception that provides information on pool connection errors in service layer
 *
 * @author Olga Shahray
 */
public class ServiceConnectionPoolManagerException extends ServiceException {

    public ServiceConnectionPoolManagerException(String message) {
        super(message);
    }

    public ServiceConnectionPoolManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceConnectionPoolManagerException(Throwable cause) {
        super(cause);
    }
}
