package com.example.web01.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// 로그인한 사용자의 정보를 등록자와 수정자로 지정
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if(authentication != null){
            userId = authentication.getName();
        }
        return Optional.of(userId);
    }

}

/*
Auditing을 이용한 엔티티 공통 속성 공통화
Item, order, orderItem 엔티티의 공통된 속성
 : 등록시간, 수정시간 멤버변수

Auditing기능 : 엔티티가 저장  또는 수정될 때 자동으로 입력해줌.



 */