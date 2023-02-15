package com.example.web01.entity;

import com.example.web01.dto.MemberFormDto;
import com.example.web01.repository.CartRepository;
import com.example.web01.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // 영속성 컨텍스트 적용
    @PersistenceContext
    EntityManager em;

    // 회원가입 과정
    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("양산");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원엔티티 매핑 조회 ")
    public void findCartAndMemberTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        // JPA는 영속성 컨텍스트에 데이터를 저장 후
        // 트랜잭션이 끝날 때 flush()호출하여 db에 반영
        // 영속성 컨텍스트에 엔티티가 없으면
        // DB를 조회
        em.flush();
        em.clear();
                        //  select * from cart where id=1
        Cart saveCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 장바구니엔티티에 있는 회원id값과 , 회원엔티티에 있는 id값 일치 여부 확인
        // 일치하면 조인이 성공적으로 되었음을 의미
        assertEquals(saveCart.getMember().getId(), member.getId());

    }



}