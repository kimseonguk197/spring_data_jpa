package com.example.board.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//    Authentication 객체 안에 로그인 후 인증객체가 담겨 있음
//    세션을 꺼내어 세션안에 인증정보를 담는다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
//         Princilpal을 UserDetails로 변환하여 사용
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        session.setAttribute("email", userDetails.getUsername());
        session.setAttribute("auth", userDetails.getAuthorities());
//        또는 아래와 같이 email정보만 필요하다면, authentication객체에서 바로 name을 꺼내면된다.
//        session.setAttribute("email", authentication.getName());
//        인사말이 필요한 곳에서 아래와 같이 greeting 변수에 담아 사용도 가능하다
        session.setAttribute("greeting", authentication.getName()+"님 반갑습니다.");
        response.sendRedirect("/");
    }
}
