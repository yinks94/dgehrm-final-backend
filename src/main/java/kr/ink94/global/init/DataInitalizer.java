package kr.ink94.global.init;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.ink94.member.entity.Member;
import kr.ink94.member.entity.Role;
import kr.ink94.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitalizer {

  private final String DEFAULT_PASSWORD = "1234";

  @Bean
  CommandLineRunner initializeMembers(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {

    return args -> {
      if (!memberRepository.existsByMemberId("metro_master")) {
        memberRepository.save(
            new Member("metro_master",
                "시관리자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_METRO_MASTER));
      }

      if (!memberRepository.existsByMemberId("region_master")) {
        memberRepository.save(
            new Member("region_master",
                "지역관리자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_REGION_MASTER));
      }

      if (!memberRepository.existsByMemberId("metro_app")) {
        memberRepository.save(
            new Member("metro_app",
                "시배정자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_METRO_APP));
      }

      if (!memberRepository.existsByMemberId("region_app")) {
        memberRepository.save(
            new Member("region_app",
                "지역배정자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_REGION_APP));
      }
    };
  }
}
