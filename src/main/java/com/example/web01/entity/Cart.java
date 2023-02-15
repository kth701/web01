package com.example.web01.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 현재 장바구니 <-> 회원 (1:1)
    // 엔티티조회시 한번에 조회하는 기능 : "즉시 로딩": 생략시 기본으로 설정됨
    //@OneToOne(fetch = FetchType.EAGER)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 외래키(필드명)
    private Member member;


}
