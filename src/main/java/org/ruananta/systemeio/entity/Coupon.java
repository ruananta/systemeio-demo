package org.ruananta.systemeio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table (name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String code;
    private BigDecimal discount;

    public Coupon() {}
    public Coupon(String code, BigDecimal discount) {
        this.code = code;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
