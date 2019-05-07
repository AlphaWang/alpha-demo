package com.alphawang.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {

    private static final List<String> WHITE_LIST = Collections.singletonList("/registration");
    private static final String JWT_HEADER_NAME = "Authorization";
    
    @Autowired
    private JwtService jwtService;
    
    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String jwt = httpServletRequest.getHeader(JWT_HEADER_NAME);
        if (WHITE_LIST.contains(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
        } else if (isTokenValid(jwt)) {
            updateToken(httpServletResponse, jwt);
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


    private void updateToken(HttpServletResponse httpServletResponse, String jwt) {
        String payload = jwtService.parseToken(jwt);
        String newToken = jwtService.generateToken(payload);
        httpServletResponse.setHeader(JWT_HEADER_NAME, newToken);
    }

    private boolean isTokenValid(String token) {
        return jwtService.isTokenValid(token);
    }
}
