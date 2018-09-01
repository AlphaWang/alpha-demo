package com.alphawang.spring.jar;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionHolder {
    private String cartSessionId;

    public void setCartSessionId(String cartSessionId) {
        this.cartSessionId = cartSessionId;
    }

    public String getCartSessionId() {
        return cartSessionId;
    }
}
