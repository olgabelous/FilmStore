package by.epam.filmstore.domain;

import java.time.LocalDateTime;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public class PaymentStatus {
    long paymentId;
    boolean successful;
    String message;
    LocalDateTime dateTimeTransaction;
    double sum;

    public PaymentStatus(long paymentId, boolean successful, String message, LocalDateTime dateTimeTransaction, double sum) {
        this.paymentId = paymentId;
        this.successful = successful;
        this.message = message;
        this.dateTimeTransaction = dateTimeTransaction;
        this.sum = sum;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTimeTransaction() {
        return dateTimeTransaction;
    }

    public void setDateTimeTransaction(LocalDateTime dateTimeTransaction) {
        this.dateTimeTransaction = dateTimeTransaction;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
