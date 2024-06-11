package org.ruananta.systemeio.service;

import org.ruananta.systemeio.config.PaymentConfiguration;
import org.ruananta.systemeio.payment.PaymentAdaptor;
import org.ruananta.systemeio.payment.PaymentProcessingException;
import org.ruananta.systemeio.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {
    private TaxService taxService;
    private PaymentConfiguration paymentConfiguration;
    @Autowired
    public void setPaymentConfiguration(PaymentConfiguration paymentConfiguration) {
        this.paymentConfiguration = paymentConfiguration;
    }

    @Autowired
    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
    }

    public void makePayment(PaymentRequest paymentRequest) {
        BigDecimal finalPrice = this.taxService.calculateFinalPrice(paymentRequest);
        Optional<PaymentAdaptor> paymentAdaptor = this.paymentConfiguration.getPaymentAdaptorByName(paymentRequest.getPaymentProcessor());
        if(paymentAdaptor.isEmpty()) {
            throw new PaymentProcessingException("Not found payment processor '" + paymentRequest.getPaymentProcessor());
        }
        paymentAdaptor.get().makePayment(finalPrice);
    }
}
