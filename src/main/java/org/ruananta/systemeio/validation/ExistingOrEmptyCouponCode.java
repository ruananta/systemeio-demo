package org.ruananta.systemeio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingOrEmptyCouponCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingOrEmptyCouponCode {
    String message() default "Invalid or non-existing coupon code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}