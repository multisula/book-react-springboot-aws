package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.*;

@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {
//  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable()) // csrf는 현재 사용하지 않으므로 disable
//        .cors(withDefaults()) // WebMvcConfig 에서 이미 설정했으므로 기본 cors 설정
        .httpBasic(withDefaults()) // token을 사용하므로 basic 인증 disable
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session 기반이 아님을 선언
        .authorizeHttpRequests(
            (requests) ->
                requests.requestMatchers("/auth/**", "/").permitAll()
                    .anyRequest().authenticated())
        .addFilterAfter(
            // filter 등록
            // 매 요청마다
            // CorsFilter 실행한 후에
            // jwtAuthenticationFilter 실핸한다.
            new JwtAuthenticationFilter(),
            CorsFilter.class
        );

    return http.build();
  }
}
