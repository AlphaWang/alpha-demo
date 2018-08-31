package com.alphawang.spring.scope;

import com.alphawang.spring.scope.bean.SessionIdHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopeHelloController {
    
    @Autowired
    private SessionIdHolder sessionIdHolder;
    
    @GetMapping("/scope/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/scope/id")
    public String id() {
        return sessionIdHolder.getSid();
    }
}
