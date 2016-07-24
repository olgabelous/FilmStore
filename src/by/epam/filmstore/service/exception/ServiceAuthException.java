package by.epam.filmstore.service.exception;

/**
 * Created by Olga Shahray on 23.07.2016.
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
