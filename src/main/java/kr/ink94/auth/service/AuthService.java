package kr.ink94.auth.service;

import kr.ink94.auth.dto.LoginResponse;
import kr.ink94.auth.dto.LoginUserResponse;
import kr.ink94.global.config.JwtProperties;
import kr.ink94.member.domain.Member;
import kr.ink94.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;

    @Transactional(readOnly = true)
    public LoginResponse login(
            String loginId,
            String password
    ) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginId,
                        password
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        Member member = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "인증된 사용자 정보를 찾을 수 없습니다."
                        )
                );

        String accessToken =
                jwtTokenService.createAccessToken(authentication);

        String refreshToken =
                jwtTokenService.createRefreshToken(authentication);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtProperties.accessTokenExpirationSeconds(),
                LoginUserResponse.from(member)
        );
    }

    @Transactional(readOnly = true)
    public LoginUserResponse getCurrentUser(String loginId) {
        Member member = memberRepository.findByMemberId(loginId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "사용자를 찾을 수 없습니다."
                        )
                );

        return LoginUserResponse.from(member);
    }
}