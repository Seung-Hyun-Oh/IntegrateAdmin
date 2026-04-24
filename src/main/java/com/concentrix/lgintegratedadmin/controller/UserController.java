package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.UserApi;
import com.concentrix.lgintegratedadmin.dto.UserDto;
import com.concentrix.lgintegratedadmin.mapper.UserMapper;
import com.concentrix.lgintegratedadmin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor // 생성자 주입
@Slf4j
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * 특정 사용자 ID로 DB 정보를 조회합니다.
     */
    @Override
    public ResponseEntity<UserDto> getUserInfoNew(String userId) {
        log.info("사용자 정보 조회 요청 - ID: {}", userId);
        UserDto userInfo = userService.getUserById(userId);

        return Optional.ofNullable(userInfo)
            .map(user -> {
                log.info("사용자 조회 성공: {}", user); // 조회된 데이터 로그 출력
                log.info("user.toString(): {}", user.getUsrId());
                return ResponseEntity.ok(user);
            })
            .orElseGet(() -> {
                log.warn("사용자 조회 실패 - 존재하지 않는 ID: {}", userId); // 실패 로그
                return ResponseEntity.notFound().build();
            });
    }

    /**
     * 신규 사용자를 DB에 등록합니다.
     */
    @Override
    public ResponseEntity<String> registerUser(UserDto userDto) {
        userMapper.saveUser(userDto);
        return ResponseEntity.ok("사용자 등록 성공: " + userDto.getUsrId());
    }
}
