package org.ruananta.systemeio.service;

import org.ruananta.systemeio.entity.Coupon;

import java.util.Optional;

public interface CouponService {

    Optional<Coupon> getCoupon(String couponCode);

    boolean existsByCode(String couponCode);
}
