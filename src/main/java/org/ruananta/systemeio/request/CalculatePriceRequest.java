package org.ruananta.systemeio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CalculatePriceRequest {
    @NotNull(message = "Product ID must not be null")
    private Long product;
    @NotBlank(message = "Tax number must not be blank")
    private String taxNumber;
    @NotNull(message = "Coupon code must not be null")
    private String couponCode;

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
