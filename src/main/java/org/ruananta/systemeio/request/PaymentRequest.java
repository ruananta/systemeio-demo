package org.ruananta.systemeio.request;

import jakarta.validation.constraints.NotNull;
import org.ruananta.systemeio.validation.ExistingPaymentProcessor;
import org.ruananta.systemeio.validation.ExistingProductId;

public class PaymentRequest extends CalculatePriceRequest {

    @NotNull(message = "Payment processor must not be null")
    @ExistingPaymentProcessor
    private String paymentProcessor;
}
