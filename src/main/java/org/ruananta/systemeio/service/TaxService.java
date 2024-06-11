package org.ruananta.systemeio.service;

import org.ruananta.systemeio.entity.Coupon;
import org.ruananta.systemeio.payment.TaxNumberCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
public class TaxService {
    private CouponService couponService;

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    enum TaxRate {
        GERMANY(0.19),
        ITALY(0.22),
        FRANCE(0.20),
        GREECE(0.24);

        private final BigDecimal rate;

        TaxRate(double rate) {
            this.rate = BigDecimal.valueOf(rate);
        }

        public BigDecimal getRate() {
            return rate;
        }
    }

    private Optional<BigDecimal> getTaxRate(TaxNumberCountry country) {
        try {
            return Optional.of(TaxRate.valueOf(country.name()).getRate());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public BigDecimal calculateTax(BigDecimal price, TaxNumberCountry country) {
        return getTaxRate(country)
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
        TaxNumberCountry country = TaxNumberCountry.getCountryFromTaxNumber(taxNumber);
        return basePrice.add(calculateTax(basePrice, country));
    }

    private boolean isCouponCodeValid(String couponCode) {
        return couponCode != null && !couponCode.isEmpty();
    }

}
