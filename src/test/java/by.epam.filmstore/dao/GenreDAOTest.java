package test.java.by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Genre;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static by.epam.filmstore.TestData.*;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class GenreDAOTest {
    private IGenreDAO genreDao;

    public GenreDAOTest() throws DAOException {
        this.genreDao = DAOFactory.getMySqlDAOFactory().getIGenreDAO();
    }

    @Before//how to reset db by file .sql?
    public void cleanDB() throws DAOException {
        ProcessBuilder pb = new ProcessBuilder("mysql -u root -proot filmstoretest < data.sql");
        /*genreDao.delete(TEST_GENRE1.getId());
        genreDao.delete(TEST_GENRE2.getId());
        genreDao.save(TEST_GENRE1);
        genreDao.save(TEST_GENRE2);*/
    }
    @Test
    public void save() throws DAOException{
        genreDao.save(TEST_GENRE1);
        Genre savedGenre = genreDao.get(TEST_GENRE1.getId());
        Assert.assertEquals(TEST_GENRE1, savedGenre);
    }

    @Test
    public void delete() throws DAOException{
        genreDao.delete(TEST_GENRE1.getId());
        List<Genre> allGenres = genreDao.getAll();
        Assert.assertEquals(allGenres, Arrays.asList(TEST_GENRE2));
    }

    @Test
    public void get() throws DAOException{
        Genre genre = genreDao.get(TEST_GENRE1.getId());
        Assert.assertEquals(TEST_GENRE1, genre);
    }

    @Test
    public void getAll() throws DAOException{
        List<Genre> allGenres = genreDao.getAll();
        Assert.assertEquals(allGenres, Arrays.asList(TEST_GENRE1, TEST_GENRE2));
    }


}
