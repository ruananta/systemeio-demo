package org.ruananta.systemeio.payment;

public class PaypalPaymentProcessor {


    public void makePayment(Integer price) throws Exception {
        if (price > 100000) {
            throw new Exception("The amount exceeds the limit for Paypal");
        }
    }
}
