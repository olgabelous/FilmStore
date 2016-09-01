package by.epam.filmstore.domain;

import java.util.Objects;

/**
 * Created by Olga Shahray on 18.07.2016.
 */
public class Country {

    private int id;
    private  String countryName;

    public Country() {
    }

    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        Country country = (Country) o;
        return id == country.id &&
                Objects.equals(countryName, country.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName);
    }
}

