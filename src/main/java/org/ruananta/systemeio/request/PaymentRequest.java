package org.ruananta.systemeio.request;

import jakarta.validation.constraints.NotNull;
import org.ruananta.systemeio.validation.ExistingPaymentProcessor;

public class PaymentRequest extends CalculatePriceRequest {

    @NotNull(message = "Payment processor must not be null")
    @ExistingPaymentProcessor
    private String paymentProcessor;

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
}
