package org.ruananta.systemeio.service;

import org.ruananta.systemeio.config.TaxConfiguration;
import org.ruananta.systemeio.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TaxService {
    private CouponService couponService;
    private TaxConfiguration taxConfiguration;

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }
    @Autowired
    public void setTaxConfiguration(TaxConfiguration taxConfiguration) {
        this.taxConfiguration = taxConfiguration;
    }

    public BigDecimal calculateTax(BigDecimal price, String country) {
        return this.taxConfiguration.getTaxRateFromCountry(country)
                .map(taxRate -> price.multiply(taxRate).setScale(2, RoundingMode.HALF_UP))
                .orElseThrow(() -> new IllegalArgumentException("Country not supported"));
    }

    public BigDecimal applyCoupon(BigDecimal price, Coupon coupon) {
        BigDecimal discount = price.multiply(coupon.getDiscount()).divide(BigDecimal.valueOf(100));
        return price.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateFinalPrice(BigDecimal basePrice, String taxNumber, String couponCode) {
        BigDecimal priceAfterDiscount = isCouponCodeValid(couponCode)
                ? this.couponService.getCoupon(couponCode)
                .map(coupon -> applyCoupon(basePrice, coupon))
                .orElse(basePrice)
                : basePrice;
        return calculatePriceWithTax(priceAfterDiscount, taxNumber);
    }

    private BigDecimal calculatePriceWithTax(BigDecimal basePrice, String taxNumber) {
        String country = this.taxConfiguration.getCountryFromTaxNumber(taxNumber);
        return basePrice.add(calculateTax(basePrice, country));
    }

    private boolean isCouponCodeValid(String couponCode) {
        return couponCode != null && !couponCode.isEmpty();
    }

}
