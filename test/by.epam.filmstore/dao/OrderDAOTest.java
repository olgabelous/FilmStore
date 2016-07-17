package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static by.epam.filmstore.TestData.*;
import static by.epam.filmstore.TestData.TEST_FILM1;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class OrderDAOTest {
    private IOrderDAO orderDao;
    private IUserDAO userDAO;
    private IFilmDAO filmDAO;

    public OrderDAOTest() throws DAOException {
        this.orderDao = DAOFactory.getMySqlDAOFactory().getIOrderDAO();
        this.userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        this.filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
    }

    @Before//how to reset db by file .sql?
    public void cleanDB() throws DAOException {
        //ProcessBuilder pb = new ProcessBuilder("mysql -u root -proot filmstoretest < data.sql");
        orderDao.delete(TEST_ORDER1.getId());
        orderDao.delete(TEST_ORDER2.getId());
        userDAO.deleteByEmail(TEST_USER.getEmail());
        filmDAO.deleteByTitle(TEST_FILM1.getTitle());
        filmDAO.deleteByTitle(TEST_FILM2.getTitle());
        userDAO.save(TEST_USER);
        filmDAO.save(TEST_FILM1);
        filmDAO.save(TEST_FILM2);
        orderDao.save(TEST_ORDER1);
        orderDao.save(TEST_ORDER2);
    }
    @Test
    public void save() throws DAOException{
        orderDao.save(TEST_ORDER1);
        Order savedOrder = orderDao.get(TEST_ORDER1.getId());
        Assert.assertEquals(TEST_ORDER1, savedOrder);
    }

    @Test
    public void delete() throws DAOException{
        orderDao.delete(TEST_ORDER1.getId());
        List<Order> allOrders = orderDao.getAll();
        Assert.assertEquals(allOrders, Arrays.asList(TEST_ORDER2));
    }

    @Test
    public void get() throws DAOException{
        Order order = orderDao.get(TEST_ORDER1.getId());
        Assert.assertEquals(TEST_ORDER1, order);
    }

    @Test
    public void getAllOfUser() throws DAOException{
        List<Order> allOrders = orderDao.getAllOfUser(TEST_USER.getId());
        Assert.assertEquals(allOrders, Arrays.asList(TEST_ORDER1, TEST_ORDER2));
    }

    @Test
    public void getAllOfFilm() throws DAOException{
        List<Order> allOrders = orderDao.getAllOfFilm(TEST_FILM1.getId());
        Assert.assertEquals(allOrders, Arrays.asList(TEST_ORDER1));
    }
}
