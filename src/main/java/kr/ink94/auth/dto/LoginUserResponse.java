package kr.ink94.auth.dto;

import kr.ink94.member.domain.Member;

public record LoginUserResponse(Long Id,
  String memberId,
  String username,
  String role
) {

  public static LoginUserResponse from(Member member){
    return new LoginUserResponse(
      member.getId(), 
      member.getMemberId(), 
      member.getUsername(),
      member.getRole().name());
  }
}