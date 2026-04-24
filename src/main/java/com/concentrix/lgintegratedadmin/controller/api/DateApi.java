package com.concentrix.lgintegratedadmin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Tag(name = "Date Utility", description = "날짜 및 시간 계산 유틸리티 API")
@RequestMapping("/api/util/date")
public interface DateApi {

    @Operation(summary = "현재 서버 시간 조회", description = "서버의 현재 시스템 날짜와 시간을 반환합니다.")
    @GetMapping("/now")
    ResponseEntity<String> getCurrentTime();

    @Operation(summary = "날짜 차이 계산", description = "두 날짜 사이의 총 일수(Days) 차이를 계산합니다.")
    @GetMapping("/diff-days")
    ResponseEntity<Long> getDiffDays(
            @Parameter(description = "시작 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

    @Operation(summary = "시간 더하기", description = "특정 시간에 입력한 시간(Hour)만큼 더한 결과를 반환합니다.")
    @GetMapping("/add-hours")
    ResponseEntity<String> addHours(
            @Parameter(description = "시간을 더할 기준 시간 (yyyy-MM-dd HH:mm:ss)")
            @RequestParam String baseTime,
            @Parameter(description = "더할 시간(Hour) 수")
            @RequestParam long hours);

    @Operation(summary = "날짜 포맷 변경", description = "날짜 문자열의 포맷을 변경합니다. (예: 2026-01-02 -> 20260102)")
    @PostMapping("/convert")
    ResponseEntity<Map<String, String>> convertFormat(
            @Parameter(description = "날짜 문자열") @RequestParam String dateStr,
            @Parameter(description = "원본 패턴") @RequestParam String fromPattern,
            @Parameter(description = "변경할 패턴") @RequestParam String toPattern);
}
