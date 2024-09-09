package com.threeoh.HowAbout.core.auth.filter;

import com.threeoh.HowAbout.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtLoginFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtLoginFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Authorization 헤더에서 JWT 토큰 추출
        String authorization = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);

            // 액세스 토큰이 유효한 경우
            if (!jwtUtil.isTokenExpired(token)) {
                String username = jwtUtil.getUsername(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Spring Security 컨텍스트에 인증 정보 설정
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } else if (refreshToken != null && !jwtUtil.isTokenExpired(refreshToken)) {
                // 리프레시 토큰이 유효한 경우, 새로운 액세스 토큰 발급
                String username = jwtUtil.getUsername(refreshToken);
                String role = jwtUtil.getRole(refreshToken); // 리프레시 토큰에서 역할 정보 가져옴
                String newAccessToken = jwtUtil.generateAccessToken(username, role);

                // 새 액세스 토큰을 응답 헤더에 추가
                response.setHeader("Authorization", "Bearer " + newAccessToken);

                // 사용자 인증 처리
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Spring Security 컨텍스트에 인증 정보 설정
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }



        // 다음 필터로 요청과 응답 전달
        filterChain.doFilter(request, response);
    }
}
