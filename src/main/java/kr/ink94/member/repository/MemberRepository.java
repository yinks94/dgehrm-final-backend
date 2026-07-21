package kr.ink94.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ink94.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByMemberId(String memberId);

  boolean existsByMemberId(String memberId);
}
