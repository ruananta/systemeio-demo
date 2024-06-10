package org.ruananta.systemeio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingProductIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingProductId {
    String message() default "The product ID does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}