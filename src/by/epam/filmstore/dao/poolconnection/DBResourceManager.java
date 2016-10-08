package by.epam.filmstore.dao.poolconnection;

import java.util.ResourceBundle;

/**
 * <p>Class manages database resources. It allows to get parameter of database
 * by key</p>
 *
 * @author Olga Shahray
 */
public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
