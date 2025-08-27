package com.rookies4.MySpringBoot;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBoot3ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBoot3ProjectApplication.class, args);
    }

    // 지연 로딩된 엔티티를 API 응답에 포함시키기 위한 Bean 설정
    @Bean
    Hibernate6Module hibernate6Module() {
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        // 지연 로딩을 강제 실행하는 옵션
        // 이 옵션을 사용하면 API 요청 시 연관된 엔티티 데이터가 없어도 자동으로 조회하여 JSON에 포함시켜 줍니다.
        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate6Module;
    }
}