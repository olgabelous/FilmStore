package by.epam.filmstore.dao.poolconnection;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}