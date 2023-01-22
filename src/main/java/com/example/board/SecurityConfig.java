package com.example.board;

import com.example.board.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
//        암호화 모듈 리턴
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return  http
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
//                token테스팅을 위한ui, 회원가입api, doLogin api는 token filter에 걸리지 않도록 하였다.
                .antMatchers("/authors/login" ,"/", "/authors/new")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/authors/login")
    //                스프링에서 지원하는 formLogin처리 요청url을 dologin으로 지정(post요청 url설정)
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                스프링에서 지원하는 logout기능을 doLogout호출을 통해 처리하겠다는 뜻
                .logoutUrl("/doLogout")
                .and().build();
    }

}
