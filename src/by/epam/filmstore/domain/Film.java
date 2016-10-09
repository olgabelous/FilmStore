package by.epam.filmstore.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Olga Shahray
 */
public class Film {
    private int id;
    private String title;
    private int year;
    private Country country;
    private String description;
    private int duration;
    private int ageRestriction;
    private double price;
    private String poster;
    private double rating;
    private LocalDate dateAdd;
    private String video;
    private List<Genre> genreList = new ArrayList<>();
    private List<FilmMaker> filmMakerList = new ArrayList<>();
    private List<Comment> commentsList = new ArrayList<>();

    public Film() {
    }

    public Film(String title, int year, String description, int duration, int ageRestriction, double price, String poster, String video) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.price = price;
        this.poster = poster;
        this.video = video;
    }

    public Film(int id, String title, String poster, double price, String video) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.video = video;
        this.poster = poster;
    }

    public Film(int id, String title) {
        this.id = id;
        this.title = title;
    }
    public Film(int id, String title, String poster) {
        this.id = id;
        this.title = title;
        this.poster = poster;
    }

    public Film(String title, int year, Country country, String description, int duration, int ageRestriction, double price, String poster, String video, LocalDate dateAdd, List<Genre> genreList, List<FilmMaker> filmMakerList) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.description = description;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.price = price;
        this.poster = poster;
        this.genreList = genreList;
        this.filmMakerList = filmMakerList;
        this.video = video;
        this.dateAdd = dateAdd;
    }

    public Film(int id, String title, int year, Country country, String description, int duration, int ageRestriction,
                double price, String poster, double rating, LocalDate dateAdd, String video, List<Genre> genreList,
                List<FilmMaker> filmMakerList, List<Comment> commentsList) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.description = description;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.price = price;
        this.poster = poster;
        this.rating = rating;
        this.dateAdd = dateAdd;
        this.video = video;
        this.genreList = genreList;
        this.filmMakerList = filmMakerList;
        this.commentsList = commentsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<FilmMaker> getFilmMakerList() {
        return filmMakerList;
    }

    public void setFilmMakerList(List<FilmMaker> filmMakerList) {
        this.filmMakerList = filmMakerList;
    }

    public void addGenre(Genre genre) {
        this.genreList.add(genre);
    }

    public void addFilmMaker(FilmMaker fMaker) {
        this.filmMakerList.add(fMaker);
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public LocalDate getDateAdd() {
        return dateAdd;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setDateAdd(LocalDate dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                year == film.year &&
                duration == film.duration &&
                ageRestriction == film.ageRestriction &&
                Double.compare(film.price, price) == 0 &&
                Double.compare(film.rating, rating) == 0 &&
                Objects.equals(title, film.title) &&
                Objects.equals(country, film.country) &&
                Objects.equals(description, film.description) &&
                Objects.equals(poster, film.poster) &&
                Objects.equals(dateAdd, film.dateAdd) &&
                Objects.equals(video, film.video) &&
                Objects.equals(genreList, film.genreList) &&
                Objects.equals(filmMakerList, film.filmMakerList) &&
                Objects.equals(commentsList, film.commentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, country, description, duration, ageRestriction, price, poster, rating, dateAdd, video, genreList, filmMakerList, commentsList);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", country=" + country +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", ageRestriction=" + ageRestriction +
                ", price=" + price +
                ", poster='" + poster + '\'' +
                ", rating=" + rating +
                ", dateAdd=" + dateAdd +
                ", video=" + video +
                ", genreList=" + genreList +
                ", filmMakerList=" + filmMakerList +
                ", commentsList=" + commentsList +
                '}';
    }
}
