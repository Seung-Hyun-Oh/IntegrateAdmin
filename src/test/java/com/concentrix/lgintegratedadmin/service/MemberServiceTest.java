package com.concentrix.lgintegratedadmin.service;

import com.concentrix.lgintegratedadmin.domain.Member;
import com.concentrix.lgintegratedadmin.dto.MemberLoginRequest;
import com.concentrix.lgintegratedadmin.dto.MemberLoginResponse;
import com.concentrix.lgintegratedadmin.dto.MemberSignupRequest;
import com.concentrix.lgintegratedadmin.dto.UserDto;
import com.concentrix.lgintegratedadmin.mapper.MemberMapper;
import com.concentrix.lgintegratedadmin.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Spy
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        // redisTemplate.opsForValue()가 valueOperations를 반환하도록 설정
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signupSuccess() {
        // given
        MemberSignupRequest request = MemberSignupRequest.builder()
                .name("테스터")
                .email("test@example.com")
                .password("password123")
                .build();

        when(memberMapper.findByEmail(request.getEmail())).thenReturn(null);

        // when
        memberService.signup(request);

        // then
        verify(memberMapper, times(1)).save(any(Member.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .name("테스터")
                .build();

        MemberLoginRequest request = new MemberLoginRequest();
        // Reflection 혹은 다른 방법으로 필드 주입이 필요하지만, 여기서는 직접 접근 가능한 구조라고 가정하거나 
        // 테스트용 생성자/Setter가 필요할 수 있음. 
        // 현재 MemberLoginRequest는 @Getter만 있고 NoArgsConstructor만 있음.
        // 테스트 편의를 위해 MemberLoginRequest를 수정하거나 수동 설정.
        
        // Mockito의 spy를 사용하거나 실제 객체의 필드를 Reflection으로 설정
        org.springframework.test.util.ReflectionTestUtils.setField(request, "email", email);
        org.springframework.test.util.ReflectionTestUtils.setField(request, "password", password);

        when(memberMapper.findByEmail(email)).thenReturn(member);
        when(jwtTokenProvider.createToken(any(UserDto.class))).thenReturn("mock-token");

        // when
        MemberLoginResponse response = memberService.login(request);

        // then
        assertThat(response.getToken()).isEqualTo("mock-token");
        assertThat(response.getEmail()).isEqualTo(email);
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(valueOperations, times(1)).set(eq("RT:" + email), eq("mock-token"), eq(1L), eq(TimeUnit.HOURS));
    }
}
