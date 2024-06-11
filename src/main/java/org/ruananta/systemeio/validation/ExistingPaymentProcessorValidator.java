package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.config.PaymentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingPaymentProcessorValidator implements ConstraintValidator<ExistingPaymentProcessor, String> {

    @Autowired
    private PaymentConfiguration paymentConfiguration;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        return this.paymentConfiguration.existsPaymentProcessorByName(name);
    }
}