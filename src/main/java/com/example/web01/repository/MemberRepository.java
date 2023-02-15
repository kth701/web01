package com.example.web01.repository;

import com.example.web01.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select * from member where email = #email
    // 이메일 기준으로 정보을 조회
    Member findByEmail(String email);
}
