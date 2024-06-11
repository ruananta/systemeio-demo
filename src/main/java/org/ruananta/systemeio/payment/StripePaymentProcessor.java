package org.ruananta.systemeio.payment;

public class StripePaymentProcessor {

    public boolean makePayment(Float price) {
        return price >= 100;
    }
}
