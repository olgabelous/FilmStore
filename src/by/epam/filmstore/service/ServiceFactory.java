package by.epam.filmstore.service;

import by.epam.filmstore.service.impl.*;

/**
 * Created by Olga Shahray on 28.05.2016.
 */
public class ServiceFactory {

    private static ServiceFactory factory = new ServiceFactory();

    private IConnectionPoolManager poolManager = new ConnectionPoolManagerImpl();
    private IFilmService filmService = new FilmServiceImpl();
    private IUserService userService = new UserServiceImpl();
    private IOrderService orderService = new OrderServiceImpl();
    private ICommentService commentService = new CommentServiceImpl();
    private IDiscountService discountService = new DiscountServiceImpl();
    private IFilmMakerService filmMakerService = new FilmMakerServiceImpl();
    private ICountryService countryService = new CountryServiceImpl();
    private IGenreService genreService = new GenreServiceImpl();

    public static ServiceFactory getInstance(){
        return factory;
    }

    public IConnectionPoolManager getPoolManager() {return  poolManager; }
    public IFilmService getFilmService(){
        return filmService;
    }
    public IUserService getUserService() { return userService; }
    public IOrderService getOrderService() { return orderService; }
    public ICommentService getCommentService() {return commentService; }
    public IDiscountService getDiscountService() { return discountService; }
    public IFilmMakerService getFilmMakerService() { return filmMakerService; }
    public ICountryService getCountryService() {return  countryService; }
    public IGenreService getGenreService() {
        return genreService;
    }
}
