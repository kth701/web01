package com.example.web01.entity;

import com.example.web01.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter@Setter
public class Order extends  BaseEntity{

    @Id@GeneratedValue
    @Column(name="order_id")
    private Long id;

    // 외래키 필드가 주인(기준)
    // 한명의 회원은 여러 번 주문 할 수 있으므로 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderTime; // 주문일
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태(주문, 취소)

    // 하나의 주문에 여러 개의 주문 상품을 갖는 경우
    // 외래키가 아닌 엔티티를 주인(기준)으로 설정할 경우 사용되는 속성
    //@OneToMany(mappedBy = "order")
    // 영속성 전이 (ex:부모엔티티 저장되면, 자식엔티티 자동 저장)
    //@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // 고아객체저거: 부모엔티티와 연관관계가 끊어진 자식 엔티티를 고아객체
    // @OneToOne, @OneToMany어노테이션에서 옵션으로 사용
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,  // 부모엔티티 영속성 상태 변화를 자식 엔티티에 모두 전이
            orphanRemoval = true,   // 고아객체제거
            fetch = FetchType.LAZY
            )
    private List<OrderItem> orderItems = new ArrayList<>();

    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;
}
//  고객이 주문할 상품을 선택, 주문할 때 주문 엔티티를 저장하면서
//  주문상품엔티티도 함께 저장되는 경우

/*
주문엔티티
주문식별자, 주문자(회원id), 주문일자, 주문상태, (주문상품들을 나열)

주문상품엔티티
주문상품엔티티 식별자, 주문식식별자, 주문상품, 가격, 개수

 */
