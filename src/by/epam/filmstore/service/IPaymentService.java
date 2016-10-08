package by.epam.filmstore.service;

import by.epam.filmstore.domain.PaymentData;
import by.epam.filmstore.domain.PaymentStatus;

/**
 * @author Olga Shahray
 */
public interface IPaymentService {

  PaymentStatus processPayment(PaymentData pd);
}
