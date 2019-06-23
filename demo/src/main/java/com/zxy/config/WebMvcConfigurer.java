package com.zxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auth()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public AuthenticationInterceptor auth(){
        return new AuthenticationInterceptor();
    }
}
