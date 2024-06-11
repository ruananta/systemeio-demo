package org.ruananta.systemeio.request;

import jakarta.validation.constraints.NotNull;
import org.ruananta.systemeio.validation.ExistingOrEmptyCouponCode;
import org.ruananta.systemeio.validation.ExistingProductId;
import org.ruananta.systemeio.validation.ValidTaxNumber;

public class CalculatePriceRequest {
    @NotNull(message = "Product ID must not be null")
    @ExistingProductId 
    private Long product;
    @NotNull(message = "Tax number must not be null")
    @ValidTaxNumber
    private String taxNumber;

    @ExistingOrEmptyCouponCode
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
