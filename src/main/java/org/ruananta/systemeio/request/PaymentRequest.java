package org.ruananta.systemeio.request;

import jakarta.validation.constraints.NotNull;
import org.ruananta.systemeio.validation.ExistingPaymentProcessor;

public class PaymentRequest extends CalculatePriceRequest {

    @NotNull(message = "Payment processor must not be null")
    @ExistingPaymentProcessor
    private String paymentProcessor;

    public PaymentRequest(Long productId, String taxNumber, String couponCode, String paymentProcessor) {
        super(productId, taxNumber, couponCode);
        this.paymentProcessor = paymentProcessor;
    }

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
}
