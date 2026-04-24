package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.PriceCalculationApi;
import com.concentrix.lgintegratedadmin.dto.PriceCalcRequest;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PriceCalculationController implements PriceCalculationApi {

    private final RestTemplateUtil restTemplateUtil;

    @Override
    public ApiResponse<Object> calculateFinalPrice(PriceCalcRequest request) {

        // 1. 외부 가격 산정 API URL (가상)
        String externalUrl = "internal-api.concentrix.com";

        try {
            // 2. RestTemplateUtil.post를 사용하여 외부 시스템 호출
            Object result = restTemplateUtil.post(externalUrl, request, Object.class);

            // 3. 공통 규격으로 감싸서 반환
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("가격 계산 시스템 연동 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getExchangePrice(Long amount, String currency) {

        String url = "api.external-ex.com" + amount + "&to=" + currency;

        // 커스텀 헤더 설정 (API Key 등)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LG-SYSTEM-ID", "LG-INT-ADMIN-01");
        headers.set("Authorization", "Bearer ADMIN_ACCESS_TOKEN");
        try {
            // RestTemplateUtil.exchangeWithHeaders 사용
            @SuppressWarnings("unchecked")
            Map<String, Object> result = restTemplateUtil.exchangeWithHeaders(url, HttpMethod.GET, headers, null, Map.class);

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("환율 변환 시스템 호출 실패: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> deletePricePolicy(String policyId) {

        String url = "internal-api.concentrix.com" + policyId;

        try {
            // RestTemplateUtil.delete 사용
            restTemplateUtil.delete(url);
            return ApiResponse.success("정책이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ApiResponse.error("정책 삭제 실패: " + e.getMessage());
        }
    }
}
