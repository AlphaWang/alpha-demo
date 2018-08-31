package com.alphawang.spring.scope;

import com.alphawang.spring.scope.bean.SessionHolder;
import com.alphawang.spring.scope.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ScopeConfig implements WebMvcConfigurer {
    
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加拦截器
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        
    }

//    @Bean
//    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) 
//    SessionHolder sessionHolder() {
//        return new SessionHolder();
//    }
}
