package br.com.devdolls.sos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                    "Authorization", "X-Requested-With", "requestId", "Correlation-Id")
            .exposedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                    "Authorization", "X-Requested-With", "requestId", "Correlation-Id")
            .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE");
    }
}
