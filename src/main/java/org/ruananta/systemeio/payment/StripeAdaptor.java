package org.ruananta.systemeio.payment;

import java.math.BigDecimal;


public class StripeAdaptor implements PaymentAdaptor {
    private StripePaymentProcessor processor;

    public StripeAdaptor() {}
    public StripeAdaptor(StripePaymentProcessor processor) {
        this.processor = processor;
    }
    public void setProcessor(StripePaymentProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void makePayment(BigDecimal amount) throws PaymentProcessingException {
        if (!this.processor.makePayment(amount.floatValue())) {
            throw new PaymentProcessingException("Payment failed");
        }
    }


}
