package kr.ink94.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import kr.ink94.member.domain.Member;
import kr.ink94.member.domain.Role;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("memberId로 회원을 조회한다")
    void findByMemberIdReturnsMember() {
        Member member = new Member(
                "metro-master",
                "관리자",
                "encoded-password",
                Role.ROLE_METRO_MASTER
        );
        memberRepository.saveAndFlush(member);

        Optional<Member> foundMember = memberRepository.findByMemberId("metro-master");

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getId()).isNotNull();
        assertThat(foundMember.get().getMemberId()).isEqualTo("metro-master");
        assertThat(foundMember.get().getUsername()).isEqualTo("관리자");
        assertThat(foundMember.get().getRole()).isEqualTo(Role.ROLE_METRO_MASTER);
        assertThat(foundMember.get().isEnabled()).isTrue();
        assertThat(foundMember.get().getCreatedAt()).isNotNull();
        assertThat(foundMember.get().getModifiedAt()).isNotNull();
    }

    @Test
    @DisplayName("memberId가 없으면 빈 Optional을 반환한다")
    void findByMemberIdReturnsEmptyWhenMemberDoesNotExist() {
        Optional<Member> foundMember = memberRepository.findByMemberId("missing-member");

        assertThat(foundMember).isEmpty();
    }

    @Test
    @DisplayName("memberId 존재 여부를 확인한다")
    void existsByMemberIdReturnsWhetherMemberExists() {
        Member member = new Member(
                "region-master",
                "지역관리자",
                "encoded-password",
                Role.ROLE_REGION_MASTER
        );
        memberRepository.saveAndFlush(member);

        boolean exists = memberRepository.existsByMemberId("region-master");
        boolean missing = memberRepository.existsByMemberId("missing-member");

        assertThat(exists).isTrue();
        assertThat(missing).isFalse();
    }

    @Test
    @DisplayName("전체 회원 목록을 조회한다")
    void findAllReturnsMembers() {
        Member metroMaster = new Member(
                "metro-master",
                "광역관리자",
                "encoded-password",
                Role.ROLE_METRO_MASTER
        );
        Member regionMaster = new Member(
                "region-master",
                "지역관리자",
                "encoded-password",
                Role.ROLE_REGION_MASTER
        );
        memberRepository.saveAllAndFlush(List.of(metroMaster, regionMaster));

        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(2);
        assertThat(members)
                .extracting(Member::getMemberId)
                .containsExactlyInAnyOrder("metro-master", "region-master");
        assertThat(members)
                .extracting(Member::getRole)
                .containsExactlyInAnyOrder(Role.ROLE_METRO_MASTER, Role.ROLE_REGION_MASTER);
    }

    @Test
    @DisplayName("회원이 없으면 빈 목록을 조회한다")
    void findAllReturnsEmptyListWhenMembersDoNotExist() {
        List<Member> members = memberRepository.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("Example 조건으로 전체 회원 목록을 조회한다")
    void findAllByExampleReturnsMatchingMembers() {
        Member metroMaster = new Member(
                "metro-master",
                "광역관리자",
                "encoded-password",
                Role.ROLE_METRO_MASTER
        );
        Member anotherMetroMaster = new Member(
                "another-metro-master",
                "다른광역관리자",
                "encoded-password",
                Role.ROLE_METRO_MASTER
        );
        Member regionMaster = new Member(
                "region-master",
                "지역관리자",
                "encoded-password",
                Role.ROLE_REGION_MASTER
        );
        memberRepository.saveAllAndFlush(List.of(metroMaster, anotherMetroMaster, regionMaster));

        Member probe = new Member();
        probe.setRole(Role.ROLE_METRO_MASTER);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths(
                        "id",
                        "memberId",
                        "username",
                        "password",
                        "enabled",
                        "createdAt",
                        "modifiedAt"
                );
        Example<Member> example = Example.of(probe, matcher);

        List<Member> members = memberRepository.findAll(example);

        assertThat(members).hasSize(2);
        assertThat(members)
                .extracting(Member::getMemberId)
                .containsExactlyInAnyOrder("metro-master", "another-metro-master");
        assertThat(members)
                .extracting(Member::getRole)
                .containsOnly(Role.ROLE_METRO_MASTER);
    }
}
