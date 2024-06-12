package org.ruananta.systemeio.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ruananta.systemeio.config.PaymentConfiguration;
import org.ruananta.systemeio.config.TaxConfiguration;
import org.ruananta.systemeio.exeption.PaymentProcessingException;
import org.ruananta.systemeio.payment.PaypalAdaptor;
import org.ruananta.systemeio.request.CalculatePriceRequest;
import org.ruananta.systemeio.request.PaymentRequest;
import org.ruananta.systemeio.service.CouponService;
import org.ruananta.systemeio.service.PaymentService;
import org.ruananta.systemeio.service.ProductService;
import org.ruananta.systemeio.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTests {
    private final Long PRODUCT_ID = 1L;
    private final String TAX_NUMBER = "GR123456789";
    private final String COUPON = "P6";
    private final String COUNTRY = "GREECE";
    private final BigDecimal FINAL_PRICE = new BigDecimal("100.00");
    private final BigDecimal TAX_RATE = new BigDecimal("00.24");
    private final String PAYMENT_PROCESSOR = "paypal";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TaxService taxService;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private ProductService productService;
    @MockBean
    private TaxConfiguration taxConfiguration;
    @MockBean
    private CouponService couponService;
    @MockBean
    private PaymentConfiguration paymentConfiguration;
    @MockBean
    private PaypalAdaptor paypalAdaptor;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @BeforeEach
    public void configure() {
        when(taxService.calculateFinalPrice(any(CalculatePriceRequest.class))).thenReturn(FINAL_PRICE);
        when(productService.existsById(PRODUCT_ID)).thenReturn(true);
        when(taxConfiguration.getCountryFromTaxNumber(TAX_NUMBER)).thenReturn(COUNTRY);
        when(taxConfiguration.getRateFromTaxNumber(TAX_NUMBER)).thenReturn(TAX_RATE);
        when(taxConfiguration.getTaxRateFromCountry(COUNTRY)).thenReturn(TAX_RATE);
        when(couponService.existsByCode(COUPON)).thenReturn(true);
        when(paymentConfiguration.getAdaptor(PAYMENT_PROCESSOR)).thenReturn(paypalAdaptor);
        when(paymentConfiguration.existAdaptor(PAYMENT_PROCESSOR)).thenReturn(true);
    }

    @Test
    public void calculatePrice_ValidRequest_ReturnsOk() throws Exception {
        CalculatePriceRequest request = new CalculatePriceRequest(PRODUCT_ID, TAX_NUMBER, COUPON);

        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("100.00"));
    }

    @Test
    public void purchase_ValidRequest_ReturnsOk() throws Exception {
        PaymentRequest request = new PaymentRequest(PRODUCT_ID, TAX_NUMBER, COUPON, PAYMENT_PROCESSOR);

        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("The payment was successful"));
    }

    @Test
    public void purchase_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Подготовка данных для теста
        PaymentRequest request = new PaymentRequest(PRODUCT_ID, TAX_NUMBER, COUPON, PAYMENT_PROCESSOR);
        doThrow(new PaymentProcessingException("Payment failed")).when(paymentService).makePayment(any(PaymentRequest.class));
        // Выполнение запроса и проверка результата
        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment failed"));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
