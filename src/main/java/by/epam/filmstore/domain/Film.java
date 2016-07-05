package by.epam.filmstore.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 17.06.2016.
 */
public class Film {
    private int id;
    private String title;
    private int year;
    private String country;
    private String description;
    private int duration;
    private int ageRestriction;
    private double price;
    private String link;
    private double rating;
    private List<Genre> genreList = new ArrayList<>();
    private List<FilmMaker> filmMakerList = new ArrayList<>();

    public Film() {
    }

    public Film(int id, String title, int year, String country, String description, int duration, int ageRestriction,
                double price, String link, double rating, List<Genre> genreList, List<FilmMaker> filmMakerList) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        Film film = (Film) o;

        if (id != film.id) return false;
        if (year != film.year) return false;
        if (duration != film.duration) return false;
        if (ageRestriction != film.ageRestriction) return false;
        if (Double.compare(film.price, price) != 0) return false;
        if (Double.compare(film.rating, rating) != 0) return false;
        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        if (country != null ? !country.equals(film.country) : film.country != null) return false;
        if (description != null ? !description.equals(film.description) : film.description != null) return false;
        if (link != null ? !link.equals(film.link) : film.link != null) return false;
        if (genreList != null ? !genreList.equals(film.genreList) : film.genreList != null) return false;
        return !(filmMakerList != null ? !filmMakerList.equals(film.filmMakerList) : film.filmMakerList != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + ageRestriction;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (link != null ? link.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (genreList != null ? genreList.hashCode() : 0);
        result = 31 * result + (filmMakerList != null ? filmMakerList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", ageRestriction=" + ageRestriction +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", rating=" + rating +
                ", genreList=" + genreList +
                ", filmMakerList=" + filmMakerList +
                '}';
    }
}
