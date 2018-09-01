package com.alphawang.spring.scope.bean;

import com.alphawang.spring.jar.SessionHolder;

public class SubSessionHolder extends SessionHolder {
    public void setCartSessionId(String cartSessionId) {
        super.setCartSessionId(cartSessionId);
    }
}
