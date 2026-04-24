package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.dto.PriceCalcRequest;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "가격 계산 관리 API", description = "RestTemplate을 이용한 외부 가격/할인 시스템 연동")
@RequestMapping("/api/v1/calc")
public interface PriceCalculationApi {

    @Operation(summary = "최종 결제 금액 계산", description = "상품 정보와 쿠폰 정보를 외부 가격 시스템에 전송하여 할인된 최종 금액을 산출합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "계산 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/calculate")
    ApiResponse<Object> calculateFinalPrice(@RequestBody PriceCalcRequest request);

    @Operation(summary = "국가별 환율 적용 가격 조회", description = "특수 헤더를 사용하여 외부 환율 시스템으로부터 변환된 가격을 가져옵니다.")
    @GetMapping("/exchange-rate")
    ApiResponse<Map<String, Object>> getExchangePrice(
            @Parameter(description = "금액", example = "50000") @RequestParam Long amount,
            @Parameter(description = "통화코드", example = "USD") @RequestParam String currency);

    @Operation(summary = "임시 가격 정책 삭제", description = "등록된 특정 프로모션 가격 정책을 삭제합니다.")
    @DeleteMapping("/policy/{policyId}")
    ApiResponse<String> deletePricePolicy(
            @Parameter(description = "정책 ID", example = "POL_2026_99") @PathVariable String policyId);
}
