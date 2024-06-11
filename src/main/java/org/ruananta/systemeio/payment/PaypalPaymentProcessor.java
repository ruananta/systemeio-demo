package org.ruananta.systemeio.payment;

public class PaypalPaymentProcessor {


    public void makePayment(Integer price) throws IllegalArgumentException {
        if (price > 100000) {
            throw new IllegalArgumentException("The amount exceeds the limit for Paypal");
        }
    }
}
