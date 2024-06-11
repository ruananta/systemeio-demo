package org.ruananta.systemeio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String code;
    private DiscountType discountType;
    private BigDecimal discount;

    public Coupon() {
    }

    public Coupon(String code, DiscountType discountType, BigDecimal discount) {
        this.code = code;
        this.discountType = discountType;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
