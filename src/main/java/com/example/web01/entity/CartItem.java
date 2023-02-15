package com.example.web01.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name="cart_item")
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    // 관계맵핑시 외래키가 기준(주인)

    // 엔티티 참조(외래키설정), 다대일
    // 하나의 장바구니는 여러개의 상품을 담을 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    // 같은 상품은 여러 장바구니에 담을 수 있음.(다대일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int count;
}
