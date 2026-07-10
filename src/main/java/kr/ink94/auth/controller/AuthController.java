package kr.ink94.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;
import kr.ink94.auth.dto.LoginResponse;
import kr.ink94.auth.dto.LoginUserResponse;
import kr.ink94.auth.service.AuthService;
import kr.ink94.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;



  @PostMapping(value="/login",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<LoginResponse>> login(
    @RequestParam
    @NotBlank(message="로그인 ID는 필수입니다.")
    String loginId,

    @RequestParam
    @NotBlank(message="비밀번호는 필수입니다.")
    String password){


      LoginResponse loginResponse = authService.login(loginId, password);
      return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "로그인에 성공했습니다", loginResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<LoginUserResponse>> me( Authentication authentication) {
        LoginUserResponse user =
                authService.getCurrentUser(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "사용자 정보를 조회했습니다.",
                        user
                )
        );
    }


  
}
