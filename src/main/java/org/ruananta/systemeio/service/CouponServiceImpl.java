package org.ruananta.systemeio.service;

import jakarta.annotation.PostConstruct;
import org.ruananta.systemeio.entity.Coupon;
import org.ruananta.systemeio.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class CouponServiceImpl implements CouponService {
    private CouponRepository couponRepository;
    @PostConstruct
    public void initDB() {
        if(this.couponRepository.count() == 0) {
            List<Coupon> coupons = Arrays.asList(
                    new Coupon("P10", new BigDecimal("10.00")),
                    new Coupon("P100", new BigDecimal("100.00"))
            );
            this.couponRepository.saveAll(coupons);
        }
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
