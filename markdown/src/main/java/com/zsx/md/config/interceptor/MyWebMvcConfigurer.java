package com.zsx.md.config.interceptor;

import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private static String[] urls = new String[]{
            "/js/**", "/css/**", "/editormd/**", "/zTree/**", "/images/**",
            "/login",
            "/login/in"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInterceptor())
        registry.addInterceptor(new SSOSpringInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(urls);
    }
}
