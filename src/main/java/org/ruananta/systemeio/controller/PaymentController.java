package org.ruananta.systemeio.controller;

import jakarta.validation.Valid;
import org.ruananta.systemeio.exeption.PaymentProcessingException;
import org.ruananta.systemeio.request.CalculatePriceRequest;
import org.ruananta.systemeio.request.PaymentRequest;
import org.ruananta.systemeio.service.PaymentService;
import org.ruananta.systemeio.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    private TaxService taxService;
    private PaymentService paymentService;

    @Autowired
    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
    }
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<String> calculatePrice(@Valid @RequestBody CalculatePriceRequest request) {
        return ResponseEntity.ok(taxService.calculateFinalPrice(request).toString());
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@Valid @RequestBody PaymentRequest request) {
        try{
            this.paymentService.makePayment(request);
        }catch (PaymentProcessingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("The payment was successful");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
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
