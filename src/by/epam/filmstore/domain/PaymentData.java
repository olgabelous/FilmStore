package by.epam.filmstore.domain;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public class PaymentData {
    double sum;

    public PaymentData(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
