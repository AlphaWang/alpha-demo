package com.alphawang.spring.scope;

import com.alphawang.spring.jar.SessionHolder;
import com.alphawang.spring.jar.SessionInterceptor;
import com.alphawang.spring.scope.bean.SubSessionHolder;
import com.alphawang.spring.scope.interceptor.ExtendedSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ScopeConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加拦截器
        registry.addInterceptor(extendedSessionInterceptor()).addPathPatterns("/**");
        
    }
    
    @Bean
    public ExtendedSessionInterceptor extendedSessionInterceptor() {
        return new ExtendedSessionInterceptor();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    SubSessionHolder subSessionHolder() {
        return new SubSessionHolder();
    }
}
