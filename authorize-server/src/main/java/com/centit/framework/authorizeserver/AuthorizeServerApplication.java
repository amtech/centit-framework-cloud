package com.centit.framework.authorizeserver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.centit.framework.core.controller.MvcConfigUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableEurekaClient
@SpringBootApplication
@EnableResourceServer
@ComponentScan(basePackages="com.centit.framework")
public class AuthorizeServerApplication extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MvcConfigUtil.setApplicationContext(applicationContext, fastJsonHttpMessageConverter);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizeServerApplication.class, args);
    }
}
