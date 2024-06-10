package org.ruananta.systemeio.controller;

import jakarta.validation.Valid;
import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.request.CalculatePriceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @PostMapping("/calculate-price")
    public ResponseEntity<String> calculatePrice(@Valid @RequestBody CalculatePriceRequest calculatePriceRequest) {

        return ResponseEntity.ok("");
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
