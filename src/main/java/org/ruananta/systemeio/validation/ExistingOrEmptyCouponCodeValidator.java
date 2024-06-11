package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingOrEmptyCouponCodeValidator implements ConstraintValidator<ExistingOrEmptyCouponCode, String> {

    @Autowired
    private CouponService couponService;

    @Override
    public boolean isValid(String couponCode, ConstraintValidatorContext context) {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            return true;
        }
        return this.couponService.existsByCode(couponCode);
    }
}