package org.ruananta.systemeio.payment;

public class StripePaymentProcessor implements PaymentProcessor<Float> {
    @Override
    public boolean makePayment(Float price) throws Exception {
        Float amount = price.floatValue();
        return amount >= 100;
    }
}
