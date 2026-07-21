package kr.ink94.auth.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.ink94.auth.dto.LoginResponse;
import kr.ink94.auth.dto.LoginUserResponse;
import kr.ink94.auth.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(authService))
                .build();
    }

    @Test
    void loginReturnsTokenResponse() throws Exception {
        LoginUserResponse user = new LoginUserResponse(
                1L,
                "admin",
                "관리자",
                "ROLE_METRO_MASTER"
        );
        LoginResponse response = new LoginResponse(
                "access-token",
                "refresh-token",
                "Bearer",
                1800L,
                user
        );

        when(authService.login("admin", "password")).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("loginId", "admin")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("로그인에 성공했습니다"))
                .andExpect(jsonPath("$.data.accessToken").value("access-token"))
                .andExpect(jsonPath("$.data.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.expiresIn").value(1800))
                .andExpect(jsonPath("$.data.user.Id").value(1))
                .andExpect(jsonPath("$.data.user.memberId").value("admin"))
                .andExpect(jsonPath("$.data.user.username").value("관리자"))
                .andExpect(jsonPath("$.data.user.role").value("ROLE_METRO_MASTER"));

        verify(authService).login("admin", "password");
    }

    @Test
    void meReturnsCurrentUserResponse() throws Exception {
        LoginUserResponse user = new LoginUserResponse(
                1L,
                "admin",
                "관리자",
                "ROLE_METRO_MASTER"
        );
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                "admin",
                null,
                List.of(new SimpleGrantedAuthority(user.role()))
        );

        when(authService.getCurrentUser("admin")).thenReturn(user);

        mockMvc.perform(get("/api/v1/auth/me")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("사용자 정보를 조회했습니다."))
                .andExpect(jsonPath("$.data.Id").value(1))
                .andExpect(jsonPath("$.data.memberId").value("admin"))
                .andExpect(jsonPath("$.data.username").value("관리자"))
                .andExpect(jsonPath("$.data.role").value("ROLE_METRO_MASTER"));

        verify(authService).getCurrentUser("admin");
    }
}
