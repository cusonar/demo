package com.example.demo.member;

import com.example.demo.common.Response;
import com.example.demo.member.service.JoinDto;
import com.example.demo.member.service.LoginDto;
import com.example.demo.member.service.MemberService;
import com.example.demo.token.service.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/join")
    public Response<JoinDto.Response> join(@RequestBody JoinDto.Request request) {
        log.info("join request: {}", request);
        Long joinedId = memberService.join(request);
        log.info("join completed: {}", joinedId);
        JoinDto.Response response = new JoinDto.Response(joinedId);
        return new Response<>(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto.Response> login(@RequestBody LoginDto.Request request) throws Exception {
        log.info("login request: {}", request);
        authenticate(request.getUsername(), request.getPassword());
        TokenDto.All tokens = memberService.login(request);
        ResponseCookie responseCookie = memberService.getCookieForRefreshToken(tokens.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new TokenDto.Response(tokens.getAccessToken()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<TokenDto.Response> renewalToken(HttpServletRequest request) {
        String refreshToken = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }
        TokenDto.All tokens = memberService.renewalToken(refreshToken);
        ResponseCookie responseCookie = memberService.getCookieForRefreshToken(tokens.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new TokenDto.Response(tokens.getAccessToken()));
    }
}
