package com.alphawang.spring.scope.bean;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Setter
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionIdHolder {

    @Autowired
    private SessionHolder cartSessionHolder;

    private String sid;

    public String getSid(){
        if (StringUtils.isEmpty(this.sid)) {
            return cartSessionHolder.getCartSessionId();
        }
        return this.sid;
    }
}
