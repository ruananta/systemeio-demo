package org.ruananta.systemeio.payment;

import org.ruananta.systemeio.exeption.PaymentProcessingException;

import java.math.BigDecimal;

public interface PaymentAdaptor {
    public void makePayment(BigDecimal amount) throws PaymentProcessingException;
}
