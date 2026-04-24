package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.PaymentInterfaceApi;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.RestClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentInterfaceController implements PaymentInterfaceApi {

    private final RestClientUtil restClientUtil;

    @Override
    public ApiResponse<Object> getPaymentInfo(String orderId, String mallId) {

        String targetUrl = "api.external-pg.com";

        // Query 파라미터 구성
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("orderId", orderId);
        queryParams.put("mallId", mallId);

        // RestClientUtil 사용
        Object result = restClientUtil.get(targetUrl, queryParams, Object.class);

        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<Map<String, Object>> requestSecureApprove(Map<String, Object> paymentPayload) {

        String targetUrl = "api.external-pg.com";

        // 인증 헤더 구성
        Map<String, String> headers = new HashMap<>();
        headers.put("X-API-KEY", "SECRET_KEY_2026");
        headers.put("Authorization", "Bearer TOKEN_STRING");

        // RestClientUtil의 postWithHeaders 사용
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClientUtil.postWithHeaders(targetUrl, headers, paymentPayload, Map.class);

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Object> updatePaymentMethod(Map<String, Object> updateData) {

        String targetUrl = "api.external-pg.com";
        Object result = restClientUtil.put(targetUrl, updateData, Object.class);

        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<String> cancelPayment(String txId) {

        String targetUrl = "api.external-pg.com" + txId;

        // RestClientUtil의 delete 사용
        restClientUtil.delete(targetUrl);

        return ApiResponse.success("취소 요청이 접수되었습니다.");
    }
}
