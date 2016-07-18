package by.epam.filmstore;

import by.epam.filmstore.domain.*;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class TestData {

    public static final User TEST_USER = new User(1, "User", "user@yandex.ru", "password", "2223322", null, LocalDateTime.of(2016, Month.JUNE, 15, 10, 0, 0), Role.USER, null);
    public static final User TEST_ADMIN = new User(2, "Admin", "admin@gmail.com", "admin", "+375297253059", "adm.jpg", LocalDateTime.of(2016, Month.MAY, 1, 12, 12, 0), Role.ADMIN, null);

    public static final Film TEST_FILM1 = new Film(1, "Film1", 2016, "Mexico", "good", 100, 16, 15.0, "movie.hd", 9.0, null, null);
    public static final Film TEST_FILM2 = new Film(2, "Film2", 2015, "Mexico", "the best", 87, 18, 10.0, "movie.hd", 10.00, null, null);

    public static final Comment TEST_COM1 = new Comment(TEST_USER, TEST_FILM1, 9, "Super!", LocalDateTime.of(2016, Month.JUNE, 18, 10, 0, 0), "new");
    public static final Comment TEST_COM2 = new Comment(TEST_USER, TEST_FILM2, 10, "Super!!!", LocalDateTime.of(2016, Month.JUNE, 19, 11, 11, 0), "new");

    public static final Order TEST_ORDER1 = new Order(1,TEST_FILM1, TEST_USER, LocalDateTime.of(2016, Month.JUNE, 8, 10, 0, 0), 15.0, "completed");
    public static final Order TEST_ORDER2 = new Order(2,TEST_FILM2, TEST_USER, LocalDateTime.of(2016, Month.JUNE, 12, 10, 0, 0), 15.0, "completed");

    public static final Genre TEST_GENRE1 = new Genre(1, "Comedy");
    public static final Genre TEST_GENRE2 = new Genre(1, "Horror");


}
