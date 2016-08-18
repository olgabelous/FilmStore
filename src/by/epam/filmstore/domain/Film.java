package by.epam.filmstore.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Olga Shahray on 17.06.2016.
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
    private String link;
    private double rating;
    private List<Genre> genreList = new ArrayList<>();
    private List<FilmMaker> filmMakerList = new ArrayList<>();
    private List<Comment> commentsList = new ArrayList<>();

    public Film() {
    }

    public Film(int id, String title, int year, Country country, String description, int duration, int ageRestriction,
                double price, String link, double rating, List<Genre> genreList, List<FilmMaker> filmMakerList, List<Comment> commentsList) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
        this.description = description;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.price = price;
        this.link = link;
        this.rating = rating;
        this.genreList = genreList;
        this.filmMakerList = filmMakerList;
        this.commentsList = commentsList;
    }

    public Film(String title, int year, String description, int duration, int ageRestriction, double price, String link) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.price = price;
        this.link = link;
    }

    public Film(int id, String title) {
        this.id = id;
        this.title = title;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public void addGenre(Genre genre){
        this.genreList.add(genre);
    }

    public void addFilmMaker(FilmMaker fMaker){
        this.filmMakerList.add(fMaker);
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
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
                Objects.equals(link, film.link) &&
                Objects.equals(genreList, film.genreList) &&
                Objects.equals(filmMakerList, film.filmMakerList) &&
                Objects.equals(commentsList, film.commentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, country, description, duration, ageRestriction, price, link, rating, genreList, filmMakerList, commentsList);
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
                ", link='" + link + '\'' +
                ", rating=" + rating +
                ", genreList=" + genreList +
                ", filmMakerList=" + filmMakerList +
                ", commentsList=" + commentsList +
                '}';
    }
}
