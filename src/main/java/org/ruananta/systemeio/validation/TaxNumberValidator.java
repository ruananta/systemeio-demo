package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.payment.TaxNumberCountry;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {
    @Override
    public void initialize(ValidTaxNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(taxNumber == null)
            return false;
        try {
            TaxNumberCountry.getCountryFromTaxNumber(taxNumber);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
