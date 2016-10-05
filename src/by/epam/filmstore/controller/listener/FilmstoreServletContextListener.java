package by.epam.filmstore.controller.listener;

import by.epam.filmstore.service.IConnectionPoolManager;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceConnectionPoolManagerException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Olga Shahray on 28.07.2016.
 */
public class FilmstoreServletContextListener implements ServletContextListener {
    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        try {
            poolManager.init();
        } catch (ServiceConnectionPoolManagerException e) {
            e.printStackTrace();
            throw new RuntimeException("Connection pool is not created");
        }
        System.out.println("Connection pool is created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        IConnectionPoolManager poolManager = ServiceFactory.getInstance().getPoolManager();
        try {
            poolManager.destroy();
        } catch (ServiceConnectionPoolManagerException e) {
            e.printStackTrace();
        }
        System.out.println("Connection pool is destroyed");
    }
}

