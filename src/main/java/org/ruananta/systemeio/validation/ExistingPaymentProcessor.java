package org.ruananta.systemeio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingPaymentProcessorValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingPaymentProcessor {
    String message() default "The payment processor does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}