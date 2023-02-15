package com.example.web01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing      // JPA의 Audition기능을 활성화
public class AuditConfig {

    // 등록자와 수정자를 처리하는 AuditorAware빈을 등록
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

}
/*
Spring Data Jpa에서는 Auding기능
Item, Order, OrderItem등 엔티티세서 공통으로 사용되는 속성
등록시간, 수정시간, 등록자, 수정자 등 멤버변수가 공통으로 사용되는 경우


 */