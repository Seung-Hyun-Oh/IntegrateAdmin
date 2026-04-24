package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.MemberApi;
import com.concentrix.lgintegratedadmin.dto.MemberLoginRequest;
import com.concentrix.lgintegratedadmin.dto.MemberLoginResponse;
import com.concentrix.lgintegratedadmin.dto.MemberSignupRequest;
import com.concentrix.lgintegratedadmin.service.MemberService;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @Override
    public ApiResponse<String> signup(MemberSignupRequest request) {
        memberService.signup(request);
        return ApiResponse.success("회원가입이 완료되었습니다.");
    }

    @Override
    public ApiResponse<MemberLoginResponse> login(MemberLoginRequest request) {
        MemberLoginResponse response = memberService.login(request);
        return ApiResponse.success(response);
    }
}
