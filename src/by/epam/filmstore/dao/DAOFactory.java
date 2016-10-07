package by.epam.filmstore.dao;

/**
 * @author Olga Shahray
 */
public abstract class DAOFactory {
    private static final MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

    public static MySqlDAOFactory getMySqlDAOFactory(){
        return mySqlDAOFactory;
    }

    public abstract IUserDAO getIUserDAO();
    public abstract IFilmDAO getIFilmDAO();
    public abstract IFilmMakerDAO getIFilmMakerDAO();
    public abstract ICommentDAO getICommentDAO();
    public abstract IOrderDAO getIOrderDAO();
    public abstract IGenreDAO getIGenreDAO();
    public abstract IDiscountDAO getIDiscountDAO();
    public abstract ICountryDAO getICountryDAO();
}