package com.cos.jwtserver.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;  //다운캐스팅해서 받아준다.

        req.setCharacterEncoding("UTF-8");

        // 토큰이 넘어오면 인증
        // id.pw정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
        // 요청할 때 마다 header에 Authorization에 value 값으로 토큰을 가지고온다.
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검검하면 된다.(RSA, HS256)으로 검증
        if(req.getMethod().equals("POST")){
            System.out.println("post요청");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

            if(headerAuth.equals("cos")){
                chain.doFilter(req, res); // 필터를 끝마치고 종료되지말고 리턴
            }else{
                PrintWriter outPrintWriter = res.getWriter();
                System.out.println("인증안됨");
            }
        }


    }
}



