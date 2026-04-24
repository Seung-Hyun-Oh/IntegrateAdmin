package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "사용자 데이터베이스 조회 API")
@RequestMapping("/api/user")
public interface UserApi {

    /**
     * 특정 사용자 ID로 DB 정보를 조회합니다.
     */
    @Operation(summary = "사용자 조회", description = "DB에서 특정 ID를 가진 사용자 정보를 가져옵니다.")
    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUserInfoNew(@PathVariable String userId);

    /**
     * 신규 사용자를 DB에 등록합니다.
     */
    @Operation(summary = "사용자 등록", description = "신규 사용자 정보를 DB에 저장합니다.")
    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody UserDto userDto);
}
