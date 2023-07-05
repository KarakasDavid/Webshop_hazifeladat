package com.example.tutorial_security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/yay").setViewName("yay");
        registry.addViewController("/product_adding").setViewName("product_adding");
        registry.addViewController("/register_page").setViewName("register_page");
        registry.addViewController("/list").setViewName("product-list");

    }

}