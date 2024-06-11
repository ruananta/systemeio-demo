package org.ruananta.systemeio.payment;

public class PaypalPaymentProcessor implements PaymentProcessor<Integer> {

    @Override
    public boolean makePayment(Integer price) throws IllegalArgumentException {
        Integer amount = price.intValue();
        if (amount > 100000) {
            throw new IllegalArgumentException("The amount exceeds the limit for Paypal");
        }
        return true;
    }
}
