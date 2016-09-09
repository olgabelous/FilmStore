package by.epam.filmstore.service.exception;

/**
 * Created by Olga Shahray on 02.09.2016.
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
