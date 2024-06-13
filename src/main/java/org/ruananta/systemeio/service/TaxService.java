package org.ruananta.systemeio.service;

import org.ruananta.systemeio.config.TaxConfiguration;
import org.ruananta.systemeio.entity.Coupon;
import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.request.CalculatePriceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class TaxService {

    private CouponService couponService;
    private TaxConfiguration taxConfiguration;
    private ProductService productService;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final int SCALE = 2;

    public TaxService(CouponService couponService, TaxConfiguration taxConfiguration, ProductService productService) {
        this.couponService = couponService;
        this.taxConfiguration = taxConfiguration;
        this.productService = productService;
    }

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    @Autowired
    public void setTaxConfiguration(TaxConfiguration taxConfiguration) {
        this.taxConfiguration = taxConfiguration;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal calculateFinalPrice(CalculatePriceRequest request) {
        Optional<Product> optionalProduct = this.productService.findById(request.getProduct());
        return calculateFinalPrice(
                optionalProduct.get().getPrice(),
                request.getTaxNumber(),
                request.getCouponCode()
        );
    }

    public BigDecimal calculateFinalPrice(BigDecimal basePrice, String taxNumber, String couponCode) {
        BigDecimal priceAfterDiscount = isCouponCodeValid(couponCode)
                ? this.couponService.getCoupon(couponCode)
                .map(coupon -> applyCoupon(basePrice, coupon))
                .orElse(basePrice)
                : basePrice;
        return calculatePriceWithTax(priceAfterDiscount, taxNumber);
    }

    
    private BigDecimal calculateTax(BigDecimal price, String country) {
        return price.multiply(getTaxRateFromCountry(country)
                .setScale(SCALE, RoundingMode.HALF_UP));
    }

    private BigDecimal applyCoupon(BigDecimal totalAmount, Coupon coupon) {
        BigDecimal discountAmount = switch (coupon.getDiscountType()) {
            case FIXED -> coupon.getDiscount();
            case PERCENTAGE -> totalAmount.multiply(coupon.getDiscount()).divide(ONE_HUNDRED, RoundingMode.HALF_UP);
            default -> throw new IllegalArgumentException("Unknown discount type: " + coupon.getDiscountType());
        };
        return totalAmount.subtract(discountAmount).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePriceWithTax(BigDecimal basePrice, String taxNumber) {
        String country = getCountryFromTaxNumber(taxNumber);
        return basePrice.add(calculateTax(basePrice, country)).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private boolean isCouponCodeValid(String couponCode) {
        return couponCode != null && !couponCode.isEmpty();
    }

    public BigDecimal getTaxRateFromTaxNumber(String taxNumber) {
        return this.taxConfiguration.getTaxRateFromTaxNumber(taxNumber);
    }

    public BigDecimal getTaxRateFromCountry(String country) {
        return this.taxConfiguration.getTaxRateFromCountry(country);
    }

    public String getCountryFromTaxNumber(String taxNumber) {
        return this.taxConfiguration.getCountryFromTaxNumber(taxNumber);
    }


}
