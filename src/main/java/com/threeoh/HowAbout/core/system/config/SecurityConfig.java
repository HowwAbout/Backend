package com.threeoh.HowAbout.core.system.config;

import com.threeoh.HowAbout.core.auth.filter.JwtLoginFilter;
import com.threeoh.HowAbout.core.auth.filter.JwtUtil;
import com.threeoh.HowAbout.user.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성 (인증/인가 및 로그아웃을 설정)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능을 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증을 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 기능을 비활성화
                .authorizeHttpRequests(auth -> auth // 인가 작업
                        //.requestMatchers("/auth/register", "/auth/login", "/").permitAll() // 추후 User 기능 추가 시 주석 해제
                        //.anyRequest().authenticated() // 추후 User 기능 추가 시 주석 해제
                        .anyRequest().permitAll() // 추후 User 기능 추가 시 삭제해야할 코드. 모든 요청에 대해 접근 허용
                )
                .logout((logout) -> logout // 로그아웃
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer // 세션 관리 정책 설정 (Stateless)
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtLoginFilter(jwtUtil, customUserDetailsService), UsernamePasswordAuthenticationFilter.class); // jwt 토큰을 검증하는 필터 등록
        return http.build();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
