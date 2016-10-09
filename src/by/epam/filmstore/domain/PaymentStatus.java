package by.epam.filmstore.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Olga Shahray
 */
public class PaymentStatus {
    private long paymentId;
    private boolean successful;
    private String message;
    private LocalDateTime dateTimeTransaction;
    private double sum;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return paymentId == that.paymentId &&
                successful == that.successful &&
                Double.compare(that.sum, sum) == 0 &&
                Objects.equals(message, that.message) &&
                Objects.equals(dateTimeTransaction, that.dateTimeTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, successful, message, dateTimeTransaction, sum);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "paymentId=" + paymentId +
                ", successful=" + successful +
                ", message='" + message + '\'' +
                ", dateTimeTransaction=" + dateTimeTransaction +
                ", sum=" + sum +
                '}';
    }
}
