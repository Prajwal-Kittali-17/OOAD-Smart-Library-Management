package com.library.Smart_Library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Registers route-level authentication interception for MVC views.
 * GRASP: Low Coupling by isolating security routing policy in one config class.
 */
@Configuration
public class WebMvcSecurityConfig implements WebMvcConfigurer {

  @Autowired
  private AuthInterceptor authInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor)
        .addPathPatterns("/dashboard", "/books", "/add-book", "/issue-book", "/return-book",
            "/transactions", "/fines")
        .excludePathPatterns("/login", "/", "/error", "/css/**", "/js/**", "/images/**");
  }
}
