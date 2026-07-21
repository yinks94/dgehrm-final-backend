package kr.ink94.member.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.ink94.member.entity.Member;
import kr.ink94.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

    Member member = memberRepository.findByMemberId(memberId).orElseThrow(
        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    return org.springframework.security.core.userdetails.User
        .withUsername(member.getMemberId())
        .password(member.getPassword())
        .authorities(Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())))
        .disabled(!member.isEnabled())
        .build();
  }

}