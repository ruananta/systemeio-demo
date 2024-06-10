package org.ruananta.systemeio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaxNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaxNumber {
    String message() default "Invalid Tax Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
