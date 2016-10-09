package by.epam.filmstore.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Olga Shahray
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

    public User() {}

    public User(int id, String name, String email, String pass, String phone, String photo,
                LocalDateTime dateRegistration, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.photo = photo;
        this.dateRegistration = dateRegistration;
        this.role = role;
    }

    public User(String name, String email, String pass, String phone, LocalDateTime dateRegistration, Role role) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.dateRegistration = dateRegistration;
        this.role = role;
    }

    public User(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }
    public User(int id, String name) {
        this.id = id;
        this.name = name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(pass, user.pass) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(photo, user.photo) &&
                Objects.equals(dateRegistration, user.dateRegistration) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, pass, phone, photo, dateRegistration, role);
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
                '}';
    }
}
