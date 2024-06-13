package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {
    private TaxService taxService;

    @Autowired
    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
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
            this.taxService.getCountryFromTaxNumber(taxNumber);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
