package by.epam.filmstore.service.exception;

/**
 * An exception that provides information of errors during user's authorization
 *
 * @author Olga Shahray
 */
public class ServiceAuthException extends ServiceException{

    public ServiceAuthException(String message) {
        super(message);
    }

    public ServiceAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAuthException(Throwable cause) {
        super(cause);
    }
}
