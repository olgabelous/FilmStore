package by.epam.filmstore.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 17.06.2016.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String pass;
    private String phone;
    private String photo;
    private LocalDateTime dateRegistration;
    private Role role;
    private List<Genre> favoriteGenres = new ArrayList<>();

    public User() {}

    public User(int id, String name, String email, String pass, String phone, String photo,
                LocalDateTime dateRegistration, Role role, List<Genre> favoriteGenres) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.photo = photo;
        this.dateRegistration = dateRegistration;
        this.role = role;
        this.favoriteGenres = favoriteGenres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Genre> getFavoriteGenres() {
        return favoriteGenres;
    }

    public void setFavoriteGenres(List<Genre> favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }

    public void addFavoriteGenre(Genre genre){
        this.favoriteGenres.add(genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (dateRegistration != null ? !dateRegistration.equals(user.dateRegistration) : user.dateRegistration != null)
            return false;
        if (role != user.role) return false;
        return !(favoriteGenres != null ? !favoriteGenres.equals(user.favoriteGenres) : user.favoriteGenres != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (dateRegistration != null ? dateRegistration.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (favoriteGenres != null ? favoriteGenres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", dateRegistration=" + dateRegistration +
                ", role=" + role +
                ", favoriteGenres=" + favoriteGenres +
                '}';
    }
}
