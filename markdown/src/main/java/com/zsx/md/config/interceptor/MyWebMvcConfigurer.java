package com.zsx.md.config.interceptor;

import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurer.class);

    @Value("${spring.profiles.active}")
    private String profile;

    private static String[] urls = new String[]{
            "/js/**", "/css/**", "/editormd/**", "/zTree/**", "/images/**"

    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("spring.profiles.active = {}", profile);
        if ("pro".equals(profile)) {
            logger.info("加载拦截器SSOSpringInterceptor");
            registry.addInterceptor(new SSOSpringInterceptor()).order(1)
                    .addPathPatterns("/**")
                    .excludePathPatterns(urls);
            logger.info("加载拦截器LoginInterceptor");
            registry.addInterceptor(new LoginInterceptor()).order(2)
                    .addPathPatterns("/**")
                    .excludePathPatterns(urls);
        }
    }
}
