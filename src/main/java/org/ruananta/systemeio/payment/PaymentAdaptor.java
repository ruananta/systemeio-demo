package org.ruananta.systemeio.payment;

import java.math.BigDecimal;

public interface PaymentAdaptor {
    public void makePayment(BigDecimal amount) throws PaymentProcessingException;
}
