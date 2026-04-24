package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.DateApi;
import com.concentrix.lgintegratedadmin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class DateController implements DateApi {

    @Override
    public ResponseEntity<String> getCurrentTime() {
        String now = DateUtil.getCurrentDateTime();
        log.info("현재 시간 조회 요청: {}", now);
        return ResponseEntity.ok(now);
    }

    @Override
    public ResponseEntity<Long> getDiffDays(LocalDate start, LocalDate end) {
        long diff = DateUtil.getDaysBetween(start, end);
        log.info("날짜 차이 계산: {} ~ {} -> {}일", start, end, diff);
        return ResponseEntity.ok(diff);
    }

    @Override
    public ResponseEntity<String> addHours(String baseTime, long hours) {
        // 1. 문자열을 LocalDateTime으로 변환 (기본 포맷 사용)
        LocalDateTime ldt = LocalDateTime.parse(baseTime,
                java.time.format.DateTimeFormatter.ofPattern(DateUtil.DEFAULT_FORMAT));

        // 2. 시간 계산
        LocalDateTime resultTime = DateUtil.addHours(ldt, hours);

        // 3. 다시 문자열로 포맷팅하여 반환
        String result = DateUtil.format(resultTime, DateUtil.DEFAULT_FORMAT);
        log.info("시간 계산 완료: {} + {}시간 = {}", baseTime, hours, result);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Map<String, String>> convertFormat(String dateStr, String fromPattern, String toPattern) {
        String converted = DateUtil.convertFormat(dateStr, fromPattern, toPattern);

        Map<String, String> response = new HashMap<>();
        response.put("original", dateStr);
        response.put("converted", converted);

        return ResponseEntity.ok(response);
    }
}
