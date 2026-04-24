package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.CommonApi;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.ExcelUtil;
import com.concentrix.lgintegratedadmin.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor // 생성자 주입 자동화
public class CommonController implements CommonApi {

    private final MessageSource messageSource;

    @Override
    public ApiResponse<String> getWelcome(String name) {

        // 1. StringUtil을 활용한 기본값 처리
        String userName = StringUtil.defaultString(name, "Guest");

        // 2. MessageSource를 활용한 다국어 메시지 취득
        String message = messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale());
        String fullMessage = userName + "님, " + message;

        // 3. 표준 ApiResponse 성공 규격으로 반환
        return ApiResponse.success(fullMessage);
    }

    @Override
    public void downloadData(HttpServletResponse response) throws IOException {

        // 실무 예시 데이터 생성 (실제로는 Service에서 DB 조회)
        String[] headers = {"ID", "이름", "상태", "등록일"};
        List<Map<String, Object>> dataList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = LocalDateTime.now().format(formatter);
//        LocalDate now1 = java.time.LocalDate.now();

        for (int i = 1; i <= 1000; i++) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", i);
            row.put("name", "User_" + i);
            row.put("status", i % 2 == 0 ? "ACTIVE" : "INACTIVE");
            row.put("regDate", now);
            dataList.add(row);
        }

        // 유틸리티를 활용한 다운로드 실행
        // 주의: 파일 다운로드는 ApiResponse 래퍼를 씌우지 않고 직접 OutputStream에 씁니다.
        ExcelUtil.downloadLargeExcel(response, "User_List_2025", headers, dataList);
    }

    @Override
    public ApiResponse<Map<String, String>> convertText(String text) {

        if (StringUtil.isEmpty(text)) {
            return ApiResponse.error("변환할 텍스트가 비어있습니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("original", text);
        result.put("snakeCase", StringUtil.toSnakeCase(text));

        return ApiResponse.success(result);
    }
}
