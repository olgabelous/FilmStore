package by.epam.filmstore.service;

import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;

/**
 * Created by Olga Shahray on 04.09.2016.
 */
public interface IPaymentService {

  PaymentStatus processPayment(PaymentData pd);
}
