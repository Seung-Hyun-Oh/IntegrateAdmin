package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.PromotionApi;
import com.concentrix.lgintegratedadmin.domain.MemberGrade;
import com.concentrix.lgintegratedadmin.domain.PromotionType;
import com.concentrix.lgintegratedadmin.util.PromotionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PromotionController implements PromotionApi {

    @Override
    public ResponseEntity<Map<String, Object>> getDiscount(
        BigDecimal price,
        double discountValue,
        PromotionType type,
        BigDecimal maxLimit)
    {
        BigDecimal discount = PromotionUtil.calculateDiscountAmount(price, discountValue, type, maxLimit);
        BigDecimal finalPrice = price.subtract(discount);

        Map<String, Object> result = new HashMap<>();
        result.put("originalPrice", price);
        result.put("discountAmount", discount);
        result.put("finalPrice", finalPrice);
        result.put("appliedType", type);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Map<String, Object>> calculateAllBenefits(
        BigDecimal originalPrice,
        boolean isFirstPurchase,
        MemberGrade userGrade)
    {
        BigDecimal currentPrice = originalPrice;

        // 1. 첫 구매 할인 적용 (10%)
        BigDecimal firstDiscount = PromotionUtil.calculateFirstPurchaseDiscount(originalPrice, isFirstPurchase);
        currentPrice = currentPrice.subtract(firstDiscount);

        // 2. 등급 할인 적용 (첫 구매 할인된 금액 기준 혹은 원가 기준 - 정책에 따라 선택)
        BigDecimal gradeDiscount = PromotionUtil.calculateGradeDiscount(currentPrice, userGrade);
        currentPrice = currentPrice.subtract(gradeDiscount);

        Map<String, Object> response = new HashMap<>();
        response.put("originalPrice", originalPrice);
        response.put("firstPurchaseBenefit", firstDiscount);
        response.put("gradeBenefit", gradeDiscount);
        response.put("totalDiscount", firstDiscount.add(gradeDiscount));
        response.put("finalPrice", currentPrice);

        return ResponseEntity.ok(response);
    }
}
