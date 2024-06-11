package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.config.PaymentConfiguration;
import org.ruananta.systemeio.payment.PaymentAdaptor;
import org.ruananta.systemeio.payment.PaypalAdaptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ExistingPaymentProcessorValidator implements ConstraintValidator<ExistingPaymentProcessor, String> {


    private PaymentConfiguration paymentConfiguration;

    @Autowired
    public void setPaymentConfiguration(PaymentConfiguration paymentConfiguration) {
        this.paymentConfiguration = paymentConfiguration;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        return this.paymentConfiguration.getPaymentAdaptorByName(name).isPresent();
    }
}