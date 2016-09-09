package by.epam.filmstore.service.impl;

import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;
import by.epam.filmstore.service.IPaymentService;

import java.time.LocalDateTime;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public class PaymentServiceImpl implements IPaymentService{

    @Override
    public PaymentStatus processPayment(PaymentData pd) {
        return new PaymentStatus(1L,true, "", LocalDateTime.now(), pd.getSum());
    }
}
