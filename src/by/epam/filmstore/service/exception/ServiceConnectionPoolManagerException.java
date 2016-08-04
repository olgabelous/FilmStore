package by.epam.filmstore.service.exception;

/**
 * Created by Olga Shahray on 04.08.2016.
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
