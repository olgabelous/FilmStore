package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public abstract class DAOFactory {
    private static final MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

    public static MySqlDAOFactory getMySqlDAOFactory() throws DAOException {
        return mySqlDAOFactory;
    }

    public abstract IUserDAO getIUserDAO();
    public abstract IFilmDAO getIFilmDAO();
    public abstract ICommentDAO getICommentDAO();
    public abstract IOrderDAO getIOrderDAO();
    public abstract IGenreDAO getIGenreDAO();
    public abstract IDiscountDAO getIDiscountDAO();
}