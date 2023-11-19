package com.b2.prj02.config.security;

import com.b2.prj02.service.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        jwtTokenProvider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request); //Request의 헤더에서 토큰을 불러와서 저장

        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰이 유효한지 검사

            Authentication authentication = jwtTokenProvider.getAuthentication(token); //토큰안에 있는 유저정보를 authentication객체로 저장

            SecurityContextHolder.getContext().setAuthentication(authentication); //authentication객체로 저장한 유저정보를 SecurityContext에 저장
        }


        filterChain.doFilter(request, response); //Custom한 필터 등록
    }
}
