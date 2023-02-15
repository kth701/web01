package com.example.web01.entity;


import com.example.web01.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Audition 테스트")
    @WithMockUser(username = "gildong", roles= "USER")
    public void auditiongTest(){
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.save(memberRepository.findById(member.getId())
                .orElseThrow(EntityNotFoundException::new));

        System.out.println("-----------------");
        System.out.println("register time: "+member1.getRegTime());
        System.out.println("update time: "+member1.getUpdateTime());
        System.out.println("create time: "+member1.getCreateBy());
        System.out.println("modify time: "+member1.getModifiedBy());

    }
}
