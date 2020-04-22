package com.zsx.md.config.interceptor;

import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private static String[] urls = new String[]{
            "/js/**", "/css/**", "/editormd/**", "/zTree/**", "/images/**"

    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("加载拦截器SSOSpringInterceptor");
        registry.addInterceptor(new SSOSpringInterceptor()).order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(urls);
        System.out.println("加载拦截器LoginInterceptor");
        registry.addInterceptor(new LoginInterceptor()).order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(urls);
    }
}
