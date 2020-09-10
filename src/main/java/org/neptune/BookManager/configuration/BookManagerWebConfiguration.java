package org.neptune.BookManager.configuration;

import org.neptune.BookManager.interceptor.HostInfoInterceptor;
import org.neptune.BookManager.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class BookManagerWebConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private HostInfoInterceptor hostInfoInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            //添加拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //对所有操作
                registry.addInterceptor(hostInfoInterceptor).addPathPatterns("/**");
                //对书的操作
                registry.addInterceptor(loginInterceptor).addPathPatterns("/books/**");
            }
        } ;
    }
}
