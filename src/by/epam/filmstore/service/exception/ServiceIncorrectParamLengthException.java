package by.epam.filmstore.service.exception;

/**
 * Thrown to indicate that an method has incorrect length of parameters.
 *
 * @author Olga Shahray
 */
public class ServiceIncorrectParamLengthException extends ServiceValidationException{

    public ServiceIncorrectParamLengthException(String message) {
        super(message);
    }

    public ServiceIncorrectParamLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceIncorrectParamLengthException(Throwable cause) {
        super(cause);
    }
}