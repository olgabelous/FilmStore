package by.epam.filmstore.service;

import by.epam.filmstore.service.impl.ConnectionPoolManagerImpl;
import by.epam.filmstore.service.impl.FilmServiceImpl;
import by.epam.filmstore.service.impl.UserServiceImpl;

/**
 * Created by Olga Shahray on 28.05.2016.
 */
public class ServiceFactory {

    private static ServiceFactory factory = new ServiceFactory();

    private IConnectionPoolManager poolManager = new ConnectionPoolManagerImpl();
    private IFilmService filmService = new FilmServiceImpl();
    private IUserService userService = new UserServiceImpl();


    public static ServiceFactory getInstance(){
        return factory;
    }

    public IConnectionPoolManager getPoolManager() {return  poolManager; }
    public IFilmService getFilmService(){
        return filmService;
    }
    public IUserService getUserService() { return userService; }

}