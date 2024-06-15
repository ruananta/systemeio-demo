package org.ruananta.systemeio.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.ruananta.systemeio.config.TaxConfiguration;
import org.ruananta.systemeio.entity.Coupon;
import org.ruananta.systemeio.entity.DiscountType;
import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.request.CalculatePriceRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class TaxServiceTest {
    private TaxService taxService;
    private ProductService productService;
    private CouponService couponService;
    private TaxConfiguration taxConfiguration;

    @Before
    public void setUp() {
        productService = mock(ProductService.class);
        couponService = mock(CouponService.class);
        taxConfiguration = mock(TaxConfiguration.class);
        taxService = new TaxService(couponService, taxConfiguration, productService);
    }

    @Test
    public void calculateFinalPrice_WithValidCoupon_ShouldApplyDiscount() {
        Long productId = 753L;
        String taxNumber = "GR123456789";
        String couponCode = "P6";
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal discount = new BigDecimal("6.00");
        String country = "GREECE";
        BigDecimal taxRate = new BigDecimal("0.24");
        BigDecimal finalPrice = new BigDecimal("116.56");

        CalculatePriceRequest request = new CalculatePriceRequest(productId, taxNumber, couponCode);
        Product product = new Product(productId, basePrice);
        Coupon coupon = new Coupon(couponCode, DiscountType.PERCENTAGE, discount);

        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(couponService.getCoupon(couponCode)).thenReturn(Optional.of(coupon));
        when(taxConfiguration.getCountryFromTaxNumber(taxNumber)).thenReturn(country);
        when(taxConfiguration.getTaxRateFromCountry(country)).thenReturn(taxRate);

        BigDecimal calculateFinalPrice = taxService.calculateFinalPrice(request);

        assertEquals("Final price should be discounted", finalPrice, calculateFinalPrice);
    }

    @Test
    public void calculateFinalPrice_WithValidCoupon_ShouldApplyFixedDiscount() {
        Long productId = 753L;
        String taxNumber = "GR123456789";
        String couponCode = "F10";
        BigDecimal basePrice = new BigDecimal("110.00");
        BigDecimal discount = new BigDecimal("10.00");
        String country = "GREECE";
        BigDecimal taxRate = new BigDecimal("0.24");
        BigDecimal finalPrice = new BigDecimal("124.00");

        CalculatePriceRequest request = new CalculatePriceRequest(productId, taxNumber, couponCode);
        Product product = new Product(productId, basePrice);
        Coupon coupon = new Coupon(couponCode, DiscountType.FIXED, discount);

        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(couponService.getCoupon(couponCode)).thenReturn(Optional.of(coupon));
        when(taxConfiguration.getCountryFromTaxNumber(taxNumber)).thenReturn(country);
        when(taxConfiguration.getTaxRateFromCountry(country)).thenReturn(taxRate);

        BigDecimal calculateFinalPrice = taxService.calculateFinalPrice(request);

        assertEquals("Final price should be discounted", finalPrice, calculateFinalPrice);
    }

    @Test
    public void calculateFinalPrice_WithInvalidCoupon_ShouldNotApplyDiscount() {
        Long productId = 753L;
        String taxNumber = "GR123456789";
        BigDecimal basePrice = new BigDecimal("100.00");
        String country = "GREECE";
        BigDecimal taxRate = new BigDecimal("0.24");
        BigDecimal finalPrice = new BigDecimal("124.00");

        CalculatePriceRequest request = new CalculatePriceRequest(productId, taxNumber, null);
        Product product = new Product(productId, basePrice);

        when(productService.findById(productId)).thenReturn(Optional.of(product));
        when(taxConfiguration.getCountryFromTaxNumber(taxNumber)).thenReturn(country);
        when(taxConfiguration.getTaxRateFromCountry(country)).thenReturn(taxRate);

        BigDecimal calculateFinalPrice = taxService.calculateFinalPrice(request);

        assertEquals("Final price should not be discounted", finalPrice, calculateFinalPrice);
    }
}
