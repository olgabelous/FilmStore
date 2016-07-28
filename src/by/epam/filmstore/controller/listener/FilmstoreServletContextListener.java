package by.epam.filmstore.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Olga Shahray on 28.07.2016.
 */
public class FilmstoreServletContextListener implements ServletContextListener {


    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("ServletContextListener started");
    }

}

