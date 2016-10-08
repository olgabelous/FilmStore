package by.epam.filmstore.dao.poolconnection;

/**
 * An exception that provides information on error in pool connection.
 *
 * @author Olga Shahray
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}