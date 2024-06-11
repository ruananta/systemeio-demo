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

    public BigDecimal calculateTax(BigDecimal price, String country) {
        return this.taxConfiguration.getTaxRateFromCountry(country)
                .map(taxRate -> price.multiply(taxRate).setScale(SCALE, RoundingMode.HALF_UP))
                .orElseThrow(() -> new IllegalArgumentException("Country not supported"));
    }

    public BigDecimal applyCoupon(BigDecimal totalAmount, Coupon coupon) {
        BigDecimal discountAmount = switch (coupon.getDiscountType()) {
            case FIXED -> coupon.getDiscount();
            case PERCENTAGE -> totalAmount.multiply(coupon.getDiscount()).divide(ONE_HUNDRED);
            default -> throw new IllegalArgumentException("Unknown discount type: " + coupon.getDiscountType());
        };
        return totalAmount.subtract(discountAmount).setScale(SCALE, RoundingMode.HALF_UP);
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

    public BigDecimal calculateFinalPrice(CalculatePriceRequest request) {
        Optional<Product> optionalProduct = this.productService.findById(request.getProduct());
        //Можно проверить не пустой ли optionalProduct, но я его проверяю в CalculatePriceRequest на получении
        return calculateFinalPrice(
                optionalProduct.get().getPrice(),
                request.getTaxNumber(),
                request.getCouponCode()
        );
    }
}
