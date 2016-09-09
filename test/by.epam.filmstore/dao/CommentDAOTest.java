package by.epam.filmstore.dao;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Comment;
import by.epam.filmstore.domain.CommentStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static by.epam.filmstore.TestData.*;
/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class CommentDAOTest {
    private ICommentDAO commentDao;
    private IUserDAO userDAO;
    private IFilmDAO filmDAO;

    public CommentDAOTest() throws DAOException {
        this.commentDao = DAOFactory.getMySqlDAOFactory().getICommentDAO();
        this.userDAO = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        this.filmDAO = DAOFactory.getMySqlDAOFactory().getIFilmDAO();
    }

    @Before
    public void cleanDB() throws DAOException {

        commentDao.delete(TEST_USER.getId(), TEST_FILM1.getId());
        commentDao.delete(TEST_USER.getId(), TEST_FILM2.getId());
        userDAO.deleteByEmail(TEST_USER.getEmail());
        filmDAO.deleteByTitle(TEST_FILM1.getTitle());
        filmDAO.deleteByTitle(TEST_FILM2.getTitle());
        userDAO.save(TEST_USER);
        filmDAO.save(TEST_FILM1);
        filmDAO.save(TEST_FILM2);
        commentDao.save(TEST_USER.getId(), TEST_FILM2.getId(), TEST_COM2);
        commentDao.save(TEST_USER.getId(), TEST_FILM1.getId(), TEST_COM1);
    }
    @Test
    public void save() throws DAOException{
        Comment comment = new Comment(TEST_USER, TEST_FILM2, 10, "The best", null, CommentStatus.NEW);
        commentDao.save(TEST_USER.getId(), TEST_FILM1.getId(), comment);
        Comment savedComment = commentDao.get(TEST_USER.getId(), TEST_FILM1.getId());
        comment.setDateComment(savedComment.getDateComment());
        Assert.assertEquals(comment, savedComment);
    }

    @Test
    public void delete() throws DAOException{
        commentDao.delete(TEST_USER.getId(), TEST_FILM1.getId());
        List<Comment> allComments = commentDao.getAllOfUser(TEST_USER.getId());
        Assert.assertEquals(allComments, Collections.singletonList(TEST_COM2));
    }

    @Test
    public void get() throws DAOException{
        Comment comment = commentDao.get(TEST_USER.getId(), TEST_FILM1.getId());
        Assert.assertEquals(TEST_COM1, comment);
    }

    @Test
    public void getAllOfUser() throws DAOException{
        List<Comment> allComments = commentDao.getAllOfUser(TEST_USER.getId());
        Assert.assertEquals(allComments, Arrays.asList(TEST_COM1, TEST_COM2));
    }

    @Test
    public void getAllOfFilm() throws DAOException{
        List<Comment> allComments = commentDao.getAllOfFilm(TEST_FILM1.getId());
        Assert.assertEquals(allComments, Arrays.asList(TEST_COM1));
    }
}
