package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.config.TaxConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {
    private TaxConfiguration taxConfiguration;

    @Autowired
    public void setTaxConfiguration(TaxConfiguration taxConfiguration) {
        this.taxConfiguration = taxConfiguration;
    }

    @Override
    public void initialize(ValidTaxNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(taxNumber == null)
            return false;
        try {
            this.taxConfiguration.getCountryFromTaxNumber(taxNumber);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
