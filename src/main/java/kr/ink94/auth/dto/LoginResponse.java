package kr.ink94.auth.dto;

public record LoginResponse(
  String accessToken,
  String refreshToken,
  String tokenType,
  long expiresIn,
  LoginUserResponse user
) {
  
}
