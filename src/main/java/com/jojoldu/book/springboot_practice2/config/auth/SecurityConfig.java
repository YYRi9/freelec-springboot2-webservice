package com.jojoldu.book.springboot_practice2.config.auth;

import com.jojoldu.book.springboot_practice2.domain.users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2 콘솔 화면을 사용하기 위해 옵션들을 disable한다.
                .and()
                .authorizeRequests()    // URL별 권한 관리를 설정하는 옵션의 시작점.
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // 권한 관리 대상을 지정.  /api/v1/** 주소를 가진 API는 USER권한을 가진 사람만 열람 권한이 있음
                .anyRequest().authenticated()   // 설정된 값들 이외의 나머지 URL을 나타냄 나머지 URL들은 모두 인증된 사용자인 로그인한 사용자들에게만 허용한다.
                .and()
                .logout()
                .logoutSuccessUrl("/")  // 로그아웃 성공 시 / 주소로 이동한다.
                .and()
                .oauth2Login()
                .userInfoEndpoint()    // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                .userService(customOAuth2UserService);  // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.

    }
    
}
