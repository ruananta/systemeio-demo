package org.ruananta.systemeio.service;

import org.ruananta.systemeio.payment.TaxNumberCountry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TaxService {

    private BigDecimal getTaxRate(TaxNumberCountry country) {
        return switch (country) {
            case GERMANY -> new BigDecimal("0.19");
            case ITALY -> new BigDecimal("0.22");
            case FRANCE -> new BigDecimal("0.20");
            case GREECE -> new BigDecimal("0.24");
            default -> throw new IllegalArgumentException("Country not supported");
        };
    }

    public BigDecimal calculateTax(BigDecimal price, TaxNumberCountry country) {
        BigDecimal taxRate = getTaxRate(country);
        return price.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal applyCoupon(BigDecimal price, BigDecimal discountRate) {
        BigDecimal discount = price.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
        return price.subtract(discount);
    }

    public BigDecimal calculateFinalPrice(BigDecimal basePrice, String taxNumber, String couponCode) {
        BigDecimal priceWithTax = calculatePriceWithTax(basePrice, taxNumber);
        return applyCouponIfPresent(priceWithTax, couponCode);
    }

    private BigDecimal calculatePriceWithTax(BigDecimal basePrice, String taxNumber) {
        TaxNumberCountry country = TaxNumberCountry.getCountryFromTaxNumber(taxNumber);
        return basePrice.add(calculateTax(basePrice, country));
    }

    private BigDecimal applyCouponIfPresent(BigDecimal price, String couponCode) {
        return isCouponCodeValid(couponCode) ? applyCoupon(price, getCouponDiscountRate(couponCode)) : price;
    }

    private boolean isCouponCodeValid(String couponCode) {
        return couponCode != null && !couponCode.isEmpty();
    }

    private BigDecimal getCouponDiscountRate(String couponCode) {
        //todo Здесь должна быть логика для определения скидки по купону
        return new BigDecimal("0.00");
    }
}
