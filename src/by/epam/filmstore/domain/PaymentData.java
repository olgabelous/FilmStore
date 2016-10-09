package by.epam.filmstore.domain;

import java.util.Objects;

/**
 * @author Olga Shahray
 */
public class PaymentData {
    private double sum;

    public PaymentData(double sum) {
        this.sum = sum;
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
        PaymentData that = (PaymentData) o;
        return Double.compare(that.sum, sum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum);
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "sum=" + sum +
                '}';
    }
}
