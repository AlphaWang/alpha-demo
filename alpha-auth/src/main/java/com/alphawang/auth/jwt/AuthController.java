package com.alphawang.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final static String JWT_HEADER_NAME = "Authorization";

    @Autowired
    private JwtService jwtService;

    @GetMapping("/registration")
    public String register(@RequestParam String username, HttpServletResponse response) {
        String jwt = jwtService.generateToken(username);
        response.setHeader(JWT_HEADER_NAME, jwt);

        return String.format("JWT for %s :\n%s", username, jwt);
    }

    
}
