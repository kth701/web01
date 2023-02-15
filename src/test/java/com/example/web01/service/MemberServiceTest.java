package com.example.web01.service;

import com.example.web01.dto.MemberFormDto;
import com.example.web01.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static  org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시");
        memberFormDto.setPassword("1234");

        // dto -> entity 전환 (비밀번호 암호화)
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member saveMember = memberService.saveMember(member);

        System.out.println(member.getEmail()+","+saveMember.getEmail());
        System.out.println(member.getName()+","+saveMember.getName());
        System.out.println(member.getAddress()+","+saveMember.getAddress());
        System.out.println(member.getPassword()+","+saveMember.getPassword());
        System.out.println(member.getRole()+","+saveMember.getRole());

        //System.out.println("----------");
        assertEquals(member.getEmail(),saveMember.getEmail());

    }

    @Test
    @DisplayName("중복회원 가입테스트:validateDuplicateMember()")
    public void saveDuplicateMember(){
        Member m1 = createMember();
        Member m2 = createMember();
        memberService.saveMember(m1);

        Throwable e = assertThrows(IllegalStateException.class, ()->{
            memberService.saveMember(m2);
        });

//        System.out.println("이미 가입된 회원입니다." + ", " +e.getMessage());
        assertEquals("이미 가입된 회원입니다.",e.getMessage());
    }
}
