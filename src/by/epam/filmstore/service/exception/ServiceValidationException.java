package by.epam.filmstore.service.exception;

/**
 * Thrown to indicate that data is not valid.
 */
public class ServiceValidationException  extends ServiceException{

    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceValidationException(Throwable cause) {
        super(cause);
    }
}
