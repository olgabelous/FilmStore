package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Film;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static by.epam.filmstore.TestData.TEST_FILM1;
import static by.epam.filmstore.TestData.TEST_FILM2;
/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class FilmDAOTest {
    private IFilmDAO dao;

    public FilmDAOTest() throws DAOException {
        this.dao = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
    }

    @Before//how to reset db by file .sql?
    public void cleanDB() throws DAOException {
        //ProcessBuilder pb = new ProcessBuilder("mysql -u root -proot filmstoretest < data.sql");
        dao.deleteByTitle(TEST_FILM1.getTitle());
        dao.deleteByTitle(TEST_FILM2.getTitle());
        dao.save(TEST_FILM1);
        dao.save(TEST_FILM2);
    }

    @Test
    public void testSave() throws DAOException{
        Film film = new Film(0, "TestFilm", 2016, "USA", "the best", 111, 16, 10.0, "movie.hd", 9.0, null, null);
        dao.save(film);
        Film savedFilm = dao.get(film.getId());
        Assert.assertEquals(film, savedFilm);

    }
    @Test
    public void testGetById() throws DAOException {
        Film film = dao.get(TEST_FILM1.getId());
        Assert.assertEquals(TEST_FILM1, film);
    }

    @Test
    public void testDelete() throws DAOException {
        dao.delete(TEST_FILM1.getId());
        List<Film> allFilms = dao.getAll();
        Assert.assertEquals(allFilms, Collections.singletonList(TEST_FILM2));
    }

    @Test
    public void testGellAll() throws DAOException {
        List<Film> allUsers = dao.getAll();
        Assert.assertEquals(allUsers, Arrays.asList(TEST_FILM1, TEST_FILM2));
    }
}
