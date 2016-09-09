package by.epam.filmstore.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class Order {

    private int id;
    private Film film;
    private User user;
    private LocalDateTime dateSale;
    private double sum;
    private OrderStatus status;

    public Order() {
    }

    public Order(int id, Film film, User user, LocalDateTime dateSale, double sum, OrderStatus status) {
        this.id = id;
        this.film = film;
        this.user = user;
        this.dateSale = dateSale;
        this.sum = sum;
        this.status = status;
    }

    public Order(Film film, User user, LocalDateTime dateSale, double sum, OrderStatus status) {
        this.film = film;
        this.user = user;
        this.dateSale = dateSale;
        this.sum = sum;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateSale() {
        return dateSale;
    }

    public void setDateSale(LocalDateTime dateSale) {
        this.dateSale = dateSale;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.sum, sum) == 0 &&
                Objects.equals(film, order.film) &&
                Objects.equals(user, order.user) &&
                Objects.equals(dateSale, order.dateSale) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, film, user, dateSale, sum, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", film=" + film +
                ", user=" + user +
                ", dateSale=" + dateSale +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                '}';
    }
}
