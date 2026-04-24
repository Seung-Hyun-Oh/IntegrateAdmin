package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Tag(name = "시스템 공통 API", description = "메시지 조회 및 엑셀 다운로드 API")
@RequestMapping("/api/common")
public interface CommonApi {

    @Operation(summary = "다국어 인사말 조회", description = "설정된 Locale에 따른 환영 메시지를 반환합니다.")
    @GetMapping("/welcome")
    ApiResponse<String> getWelcome(
            @Parameter(description = "사용자 이름", example = "admin")
            @RequestParam(required = false) String name);

    @Operation(summary = "대용량 데이터 엑셀 다운로드", description = "SXSSF 방식을 사용하여 대용량 데이터를 엑셀로 추출합니다.")
    @GetMapping("/excel/download")
    void downloadData(HttpServletResponse response) throws IOException;

    @Operation(summary = "문자열 케이스 변환 테스트", description = "카멜케이스를 스네이크케이스로 변환하여 반환합니다.")
    @GetMapping("/convert/{text}")
    ApiResponse<Map<String, String>> convertText(@PathVariable String text);
}
