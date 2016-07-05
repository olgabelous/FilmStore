package by.epam.filmstore.domain;

/**
 * Created by Olga Shahray on 27.06.2016.
 */
public class Discount {
    private int id;
    private double sumFrom;
    private double value;

    public Discount() {
    }

    public Discount(int id, double sumFrom, double discount) {
        this.id = id;
        this.sumFrom = sumFrom;
        this.value = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSumFrom() {
        return sumFrom;
    }

    public void setSumFrom(double sumFrom) {
        this.sumFrom = sumFrom;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double discount) {
        this.value = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        Discount discount1 = (Discount) o;

        if (id != discount1.id) return false;
        if (Double.compare(discount1.sumFrom, sumFrom) != 0) return false;
        return Double.compare(discount1.value, value) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(sumFrom);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", sumFrom=" + sumFrom +
                ", discount=" + value +
                '}';
    }
}
