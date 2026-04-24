package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.domain.MemberGrade;
import com.concentrix.lgintegratedadmin.domain.PromotionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "Promotion", description = "이커머스 프로모션 계산 API")
@RequestMapping("/api/promotion")
public interface PromotionApi {

    @Operation(summary = "예상 할인 금액 계산", description = "상품 가격과 프로모션 정보를 기반으로 최종 할인액을 계산합니다.")
    @GetMapping("/calculate")
    ResponseEntity<Map<String, Object>> getDiscount(
            @RequestParam BigDecimal price,
            @RequestParam double discountValue,
            @RequestParam PromotionType type,
            @RequestParam(required = false) BigDecimal maxLimit);

    @Operation(summary = "최종 혜택가 계산", description = "쿠폰, 첫 구매, 등급 할인을 모두 적용한 최종가를 계산합니다.")
    @PostMapping("/calculate-benefit")
    ResponseEntity<Map<String, Object>> calculateAllBenefits(
            @RequestParam BigDecimal originalPrice,
            @RequestParam boolean isFirstPurchase,
            @RequestParam MemberGrade userGrade);
}
