package org.ruananta.systemeio.config;

import jakarta.annotation.Resource;
import org.ruananta.systemeio.payment.PaymentProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Map;

@Configuration
@ImportResource("classpath:payment-config.xml")
public class PaymentConfiguration {
    @Resource
    private Map<String, Class<? extends PaymentProcessor<?>>> paymentProcessors;

    public boolean existsPaymentProcessorByName(String name) {
        return this.paymentProcessors.containsKey(name);
    }
}
