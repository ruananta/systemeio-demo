package org.ruananta.systemeio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ruananta.systemeio.repository.ProductRepository;
import org.ruananta.systemeio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingProductIdValidator implements ConstraintValidator<ExistingProductId, Long> {

    @Autowired
    private ProductService productService;

    @Override
    public boolean isValid(Long productId, ConstraintValidatorContext context) {
        if (productId == null) {
            return false;
        }
        return this.productService.existsById(productId);
    }
}