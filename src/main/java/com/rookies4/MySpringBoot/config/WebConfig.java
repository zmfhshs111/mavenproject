package com.rookies4.MySpringBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // API 경로에만 CORS 적용
                .allowedOrigins("*")   // 모든 출처에서의 요청을 허용 (개발용)
                // .allowedOrigins("http://127.0.0.1:5500") // 특정 출처만 허용 (배포 시 권장)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
                .allowedHeaders("*")   // 모든 헤더 허용
                .allowCredentials(false) // 쿠키/인증 정보는 허용 안 함
                .maxAge(3600); // pre-flight 요청의 캐시 시간(초)
    }
}