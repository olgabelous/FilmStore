package by.epam.filmstore.service.impl;

import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;
import by.epam.filmstore.service.IPaymentService;

import java.time.LocalDateTime;

/**
 * Class encapsulates the business logic for payment of orders.
 *
 * @author Olga Shahray
 */
public class PaymentServiceImpl implements IPaymentService{

    /**
     * Method implements process payment of order. It gives payment data and returns payment status
     * @param pd - payment data
     * @return PaymentStatus
     */
    @Override
    public PaymentStatus processPayment(PaymentData pd) {
        return new PaymentStatus(1L,true, "", LocalDateTime.now(), pd.getSum());
    }
}
