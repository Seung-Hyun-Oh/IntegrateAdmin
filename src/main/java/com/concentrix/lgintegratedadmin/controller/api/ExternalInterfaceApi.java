package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Tag(name = "외부 연동 관리 API", description = "PG/카드사 등 대외 시스템 인터페이스 제어")
@RequestMapping("/api/v1/external")
public interface ExternalInterfaceApi {

    @Operation(
            summary = "외부 결제 상태 조회",
            description = "ApiUtil을 사용하여 외부 연동 서버로부터 결제 상태 정보를 동기식으로 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "504", description = "외부 시스템 타임아웃")
    })
    @GetMapping("/payment-status/{transactionId}")
    ApiResponse<Object> getExternalPaymentStatus(
            @Parameter(description = "거래 번호", example = "TX_20260102_001")
            @PathVariable String transactionId);

    @Operation(
            summary = "외부 결제 승인 요청",
            description = "결제 데이터를 외부 시스템에 동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approve")
    ApiResponse<Map<String, Object>> requestApprove(
            @RequestBody Map<String, Object> paymentData);

    @Operation(
            summary = "외부 결제 승인 요청 (비동기)",
            description = "결제 데이터를 외부 시스템에 비동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approveAsync")
    Mono<ApiResponse<Map<String, Object>>> requestApproveAsync(@RequestBody Map<String, Object> paymentData);

    @Operation(
            summary = "외부 결제 승인 요청 (비동기-블로킹)",
            description = "결제 데이터를 외부 시스템에 동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approveAsync1")
    ApiResponse<Map<String, Object>> requestApproveAsync1(
            @RequestBody Map<String, Object> paymentData);
}
