package com.example.demo.member.service;

import com.example.demo.authority.service.AuthorityService;
import com.example.demo.common.exception.InvalidTokenException;
import com.example.demo.domain.Member;
import com.example.demo.member.MemberRepository;
import com.example.demo.token.service.RefreshTokenService;
import com.example.demo.token.service.TokenDto;
import com.example.demo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthorityService authorityService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refresh-token.expire-time}")
    private Long refreshTokenExpireTime;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("MemberService.loadUserByUsername: " + username));
    }

    @Transactional
    public Long join(JoinDto.Request request) {
        Member newMember = request.toEntity();
        Member saved = memberRepository.save(newMember);
        saved.assign(authorityService.get("ROLE_USER").orElseThrow(EntityExistsException::new));
        return saved.getId();
    }

    @Transactional
    public TokenDto.All login(LoginDto.Request request) {
        Member member = (Member) loadUserByUsername(request.getUsername());
        return generateNewToken(member);
    }

    private TokenDto.All generateNewToken(Member member) {
        String accessToken = jwtTokenUtil.generateAccessToken(member);
        String refreshToken = jwtTokenUtil.generateRefreshToken(member);
        Date refreshTokenExpirationDate = jwtTokenUtil.getExpirationDateFromToken(refreshToken);
        refreshTokenService.save(refreshToken, member, refreshTokenExpirationDate);
        return new TokenDto.All(accessToken, refreshToken);
    }

    @Transactional
    public TokenDto.All renewalToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken) &&
                refreshTokenService.exist(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            Member member = (Member) loadUserByUsername(username);
            refreshTokenService.deleteToken(refreshToken);
            return generateNewToken(member);
        }
        throw new InvalidTokenException();
    }

    public ResponseCookie getCookieForRefreshToken(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpireTime) // 14 days
                .build();
    }
}
