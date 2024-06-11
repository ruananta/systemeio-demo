package org.ruananta.systemeio.controller;

import jakarta.validation.Valid;
import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.request.CalculatePriceRequest;
import org.ruananta.systemeio.service.ProductService;
import org.ruananta.systemeio.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class PaymentController {

    private TaxService taxService;
    private ProductService productService;

    @Autowired
    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<String> calculatePrice(@Valid @RequestBody CalculatePriceRequest request) {
        Optional<Product> optionalProduct = this.productService.findById(request.getProduct());
        //Можно проверить не пустой ли optionalProduct, но я его проверяю в CalculatePriceRequest
        BigDecimal finalPrice = this.taxService.calculateFinalPrice(
                optionalProduct.get().getPrice(),
                request.getTaxNumber(),
                request.getCouponCode()
        );
        return ResponseEntity.ok(finalPrice.toString());
    }

    @PostMapping("/purchase")
    public String purchase(@RequestBody Product product) {
        return "";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
