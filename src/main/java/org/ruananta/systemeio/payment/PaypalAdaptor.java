package org.ruananta.systemeio.payment;

import java.math.BigDecimal;


public class PaypalAdaptor implements PaymentAdaptor {
    private PaypalPaymentProcessor processor;

    public PaypalAdaptor() {
    }

    public PaypalAdaptor(PaypalPaymentProcessor processor) {
        this.processor = processor;
    }

    public void setProcessor(PaypalPaymentProcessor processor) {
        this.processor = processor;
    }


    @Override
    public void makePayment(BigDecimal amount) throws PaymentProcessingException {
        try {
            this.processor.makePayment(amount.intValue());
        } catch (IllegalArgumentException e) {
            throw new PaymentProcessingException(e.getMessage());
        }
    }


}
