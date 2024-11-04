package com.example.blog.Config;
// Projenizin paket yapısına uygun olmalı

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'ler için geçerli olacak
                .allowedOrigins("http://localhost:3000") // Frontend uygulamanızın URL'si
                .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metotları
                .allowedHeaders("*") // Tüm header'lara izin ver
                .allowCredentials(true); // Credential bilgilerini de iletebilir
    }
}
