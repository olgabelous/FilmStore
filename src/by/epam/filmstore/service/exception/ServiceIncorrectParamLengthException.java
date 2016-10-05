package by.epam.filmstore.service.exception;

/**
 * Created by Olga Shahray on 22.09.2016.
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