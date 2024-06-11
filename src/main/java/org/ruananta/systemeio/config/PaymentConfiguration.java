package org.ruananta.systemeio.config;

import jakarta.annotation.PostConstruct;
import org.ruananta.systemeio.payment.*;

import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class PaymentConfiguration {

    private Map<String, PaymentAdaptor> paymentAdaptors;
    public PaypalPaymentProcessor paypalPaymentProcessor() {
        return new PaypalPaymentProcessor();
    }
    public StripePaymentProcessor stripePaymentProcessor() {
        return new StripePaymentProcessor();
    }
    public PaypalAdaptor paypalAdaptor() {
        return new PaypalAdaptor(paypalPaymentProcessor());
    }

    public StripeAdaptor stripeAdaptor() {
        return new StripeAdaptor(stripePaymentProcessor());
    }

    @PostConstruct
    public void init() {
        this.paymentAdaptors = new HashMap<>();
        this.paymentAdaptors.put("paypal", paypalAdaptor());
        this.paymentAdaptors.put("stripe", stripeAdaptor());
    }

    public Optional<PaymentAdaptor> getPaymentAdaptorByName(String name) {
        return Optional.ofNullable(paymentAdaptors.get(name));
    }

}
