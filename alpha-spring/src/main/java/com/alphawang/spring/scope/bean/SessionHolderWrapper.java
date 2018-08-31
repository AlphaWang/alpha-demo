package com.alphawang.spring.scope.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@Setter
@Getter
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionHolderWrapper {

    @Autowired
    private SessionHolder cartSessionHolder;

    private String sid;

    public String getSid(){
        
        log.warn("sessionHolder from wrapper: {}", cartSessionHolder.hashCode());
        
        if (StringUtils.isEmpty(this.sid)) {
            return cartSessionHolder.getCartSessionId();
        }
        return this.sid;
    }
}
