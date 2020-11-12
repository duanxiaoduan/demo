package com.example.demo.common.config;

import com.example.demo.common.filter.AppFilter;
import com.example.demo.common.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duanxiaoduan
 * @version 2019-05-10
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new JwtAuthenticationFilter());
        registration.addUrlPatterns("/*");
        registration.setName("JwtAuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //添加过滤器
        registration.setFilter(new AppFilter());
        //设置过滤路径，/*所有路径
        registration.addUrlPatterns("/*");
        //添加默认参数
        //registration.addInitParameter("name", "alue");
        registration.setName("appFilter");
        //设置优先级
        registration.setOrder(1);
        return registration;
    }
}
