package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.config.PaymentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

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
        return this.paymentConfiguration.existAdaptor(name);
    }
}