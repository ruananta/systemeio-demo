package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {
    @Override
    public void initialize(ValidTaxNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(taxNumber == null || taxNumber.isEmpty())
            return false;
        return taxNumber.matches("DE\\d{9}") || // DEXXXXXXXXX - Germany
                taxNumber.matches("IT\\d{11}") || // ITXXXXXXXXXXX - Italy
                taxNumber.matches("GR\\d{9}") ||  // GRXXXXXXXXX - Greece
                taxNumber.matches("FR[A-Z]{2}\\d{9}"); // FRYYXXXXXXXXX - France
    }
}
