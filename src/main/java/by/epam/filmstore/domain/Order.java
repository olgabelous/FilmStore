package by.epam.filmstore.domain;

import java.time.LocalDateTime;

/**
 * Created by Olga Shahray on 19.06.2016.
 */
public class Order {

    private int id;
    private Film film;
    private User user;
    private LocalDateTime dateSale;
    private double sum;
    private String status;

    public Order() {
    }

    public Order(int id, Film film, User user, LocalDateTime dateSale, double sum, String status) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

        if (id != order.id) return false;
        if (Double.compare(order.sum, sum) != 0) return false;
        if (film != null ? !film.equals(order.film) : order.film != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (dateSale != null ? !dateSale.equals(order.dateSale) : order.dateSale != null) return false;
        return !(status != null ? !status.equals(order.status) : order.status != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (film != null ? film.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateSale != null ? dateSale.hashCode() : 0);
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
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
