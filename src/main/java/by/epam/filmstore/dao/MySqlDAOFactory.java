package by.epam.filmstore.dao;

import by.epam.filmstore.dao.impl.*;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class MySqlDAOFactory extends DAOFactory {
    private static final IUserDAO userDAO = new UserDAOImpl();
    private static final IFilmDAO filmDAO = new FilmDAOImpl();
    private static final ICommentDAO commentDAO = new CommentDAOImpl();
    private static final IOrderDAO orderDAO = new OrderDAOImpl();
    private static final IGenreDAO genreDAO = new GenreDAOImpl();
    private static final IDiscountDAO discountDAO = new DiscountDAOImpl();

    @Override
    public IUserDAO getIUserDAO() {
        return userDAO;
    }

    @Override
    public IFilmDAO getIFilmDAO() {
        return filmDAO;
    }

    @Override
    public ICommentDAO getICommentDAO() {
        return commentDAO;
    }

    @Override
    public IOrderDAO getIOrderDAO() {
        return orderDAO;
    }

    @Override
    public IGenreDAO getIGenreDAO() {
        return genreDAO;
    }

    @Override
    public IDiscountDAO getIDiscountDAO() {
        return discountDAO;
    }

}
