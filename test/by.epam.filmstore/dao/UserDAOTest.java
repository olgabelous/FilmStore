package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static by.epam.filmstore.TestData.TEST_ADMIN;
import static by.epam.filmstore.TestData.TEST_USER;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class UserDAOTest {
    private IUserDAO dao;

    public UserDAOTest() throws DAOException {
        this.dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
    }

    @Before//how to reset db by file .sql?
    public void cleanDB() throws DAOException {
        //ProcessBuilder pb = new ProcessBuilder("mysql -u root -proot filmstoretest < data.sql");
        dao.deleteByEmail(TEST_USER.getEmail());
        dao.deleteByEmail(TEST_ADMIN.getEmail());
        dao.save(TEST_USER);
        dao.save(TEST_ADMIN);
    }

    @Test
    public void testSave() throws DAOException{
        User user = new User();
        user.setName("TestUser");
        user.setEmail("test@test.com");
        user.setPass("123456");
        user.setPhone("+375441234567");
        user.setPhoto("111.jpg");
        user.setRole(Role.USER);
        dao.save(user);
        User savedUser = dao.get(user.getId());
        user.setDateRegistration(savedUser.getDateRegistration());
        Assert.assertEquals(user, savedUser);

    }
    @Test
    public void testGetById() throws DAOException {
        User user = dao.get(TEST_USER.getId());
        Assert.assertEquals(user, TEST_USER);
    }

    @Test
    public void testGetByEmail() throws DAOException {
        User user = dao.getByEmail(TEST_USER.getEmail());
        Assert.assertEquals(user, TEST_USER);
    }

    @Test
    public void testDelete() throws DAOException {
        dao.delete(TEST_USER.getId());
        List<User> allUsers = dao.getAll(1000);
        Assert.assertEquals(allUsers, Collections.singletonList(TEST_ADMIN));
    }

    @Test
    public void testGellAll() throws DAOException {
        List<User> allUsers = dao.getAll(1000);
        Assert.assertEquals(allUsers, Arrays.asList(TEST_USER, TEST_ADMIN));
    }



}
