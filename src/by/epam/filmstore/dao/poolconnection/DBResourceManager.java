package by.epam.filmstore.dao.poolconnection;

import java.util.ResourceBundle;

/**
 * Created by Olga Shahray on 18.06.2016.
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
