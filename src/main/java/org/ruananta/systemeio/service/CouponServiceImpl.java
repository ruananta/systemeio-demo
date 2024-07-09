package org.ruananta.systemeio.service;

import jakarta.annotation.PostConstruct;
import org.ruananta.systemeio.entity.Coupon;
import org.ruananta.systemeio.entity.DiscountType;
import org.ruananta.systemeio.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {
    private CouponRepository couponRepository;

    @PostConstruct
    public void initDB() {
//        if (this.couponRepository.count() == 0) {
//            List<Coupon> coupons = Arrays.asList(
//                    new Coupon("P6", DiscountType.PERCENTAGE, new BigDecimal("6.00")),
//                    new Coupon("P10", DiscountType.PERCENTAGE, new BigDecimal("10.00")),
//                    new Coupon("P100", DiscountType.PERCENTAGE, new BigDecimal("100.00")),
//                    new Coupon("F10", DiscountType.FIXED, new BigDecimal("10.00"))
//            );
//            this.couponRepository.saveAll(coupons);
//        }
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Optional<Coupon> getCoupon(String couponCode) {
        return this.couponRepository.findByCode(couponCode);
    }

    @Override
    public boolean existsByCode(String couponCode) {
        return this.couponRepository.existsByCode(couponCode);
    }
}
