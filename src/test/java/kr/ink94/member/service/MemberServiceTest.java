package kr.ink94.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.ink94.member.domain.Member;
import kr.ink94.member.domain.Role;
import kr.ink94.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("memberId로 UserDetails를 조회한다")
    void loadUserByUsernameReturnsUserDetails() {
        Member member = new Member(
                "metro-master",
                "관리자",
                "encoded-password",
                Role.ROLE_METRO_MASTER
        );

        when(memberRepository.findByMemberId("metro-master"))
                .thenReturn(Optional.of(member));

        UserDetails userDetails = memberService.loadUserByUsername("metro-master");

        assertThat(userDetails.getUsername()).isEqualTo("metro-master");
        assertThat(userDetails.getPassword()).isEqualTo("encoded-password");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_METRO_MASTER");

        verify(memberRepository).findByMemberId("metro-master");
    }

    @Test
    @DisplayName("비활성 회원은 disabled UserDetails로 반환한다")
    void loadUserByUsernameReturnsDisabledUserDetailsWhenMemberIsDisabled() {
        Member member = new Member(
                "region-master",
                "지역관리자",
                "encoded-password",
                Role.ROLE_REGION_MASTER
        );
        member.setEnabled(false);

        when(memberRepository.findByMemberId("region-master"))
                .thenReturn(Optional.of(member));

        UserDetails userDetails = memberService.loadUserByUsername("region-master");

        assertThat(userDetails.getUsername()).isEqualTo("region-master");
        assertThat(userDetails.isEnabled()).isFalse();
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_REGION_MASTER");

        verify(memberRepository).findByMemberId("region-master");
    }

    @Test
    @DisplayName("memberId에 해당하는 회원이 없으면 예외를 던진다")
    void loadUserByUsernameThrowsExceptionWhenMemberDoesNotExist() {
        when(memberRepository.findByMemberId("missing-member"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.loadUserByUsername("missing-member"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("사용자를 찾을 수 없습니다.");

        verify(memberRepository).findByMemberId("missing-member");
    }
}
