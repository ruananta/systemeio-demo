package org.ruananta.systemeio.payment;

public interface PaymentProcessor<T> {
    boolean makePayment(T price) throws Exception;
}
