package com.example.web01.entity;

import com.example.web01.constant.ItemSellStatus;
import com.example.web01.repository.ItemRepository;
import com.example.web01.repository.MemberRepository;
import com.example.web01.repository.OrderItemRepository;
import com.example.web01.repository.OrderRepository;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public Item createItem(){
        Item item =  new Item();

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        //item.setUpdatetime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        // 주문서
        Order order = new Order();

        // 주문서에 있는 상품들
        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);

            // 주문 상품
            OrderItem orderItem = new OrderItem();

            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            // 주문서에 주문상품에 저장
            order.getOrderItems().add(orderItem);

        }
        // 영속성 컨ㅌ첵스트에 있는 객체들으 db에 반영
        orderRepository.saveAndFlush(order);
        em.clear();

        Order saveOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(3, saveOrder.getOrderItems().size());
    }

    // 주문엔티티(부모)에서 주문상품(자식엔티티)를 삭제했을 때
    // orderItem에티티 삭제하는 테스트코드
    public Order createOrder(){
        Order order = new Order();

        // 주문서에 있는 상품들
        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);

            // 주문 상품
            OrderItem orderItem = new OrderItem();

            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            // 주문서에 주문상품에 저장
            order.getOrderItems().add(orderItem);

        }

        Member member = new Member();
        memberRepository.save(member);

        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }

    // 주문상품 엔티티에 있는 상품엔티티 지연로딩 테스트
    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();

        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("order class : "+ orderItem.getOrder().getClass());


    }

}