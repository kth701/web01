package com.example.web01.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    // 하나의 상품은 여러개의 주문상품으로 들어갈 수 있다.(N:1)
    //@ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    // 한 번의 주문에 여러개의 상품을 주문할 수 있는 경우(M:1)
    //@ManyToOne
    // 지연로딩으로 설정하면 실제엔티티 대신에 프록시객체를 넣어둠.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;
    private int count;

    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;
}

/*

- 연관 관계의 주인은 외래기키가 있는 곳으로 설정
- 연관 관계의 주인이 외래키를 관린(등록, 수정, 삭제)
- 주인이 아닌 쪽은 연관 관계 매핑 시 mappedBy속성의 값으로 연관 관계의 주인을 설정
- 주인이 아닌 쪽은 읽기만 가능


영속성 전이 (cascade)
 : 엔티티의 상태를 변경할 때 헨티티와 연관된 엔티티의 상태 변화를 전파시키는 옵션

 부모엔티티(Order), 자식엔티티(OrderItem) 관계에서
 Order엔티티가 삭제 -> OrderItem엔티티가 함께 삭제

 단일 엔티티에 완전히 종속정이고 부모 엔티티와 자식 엔티티의 라이프 사이클이 유사할 때
 cascad옵션을 활용

 persist    : 부모엔티티가 영속화될 때 자식 엔티티도 영속화
 merge      : 병합
 remove     : 삭제
 refresh    : refresh
 detach     : 분리
 all        : 부모 엔티티 영속성 상태 변화를 자식엔티티에 모두 전이
 */