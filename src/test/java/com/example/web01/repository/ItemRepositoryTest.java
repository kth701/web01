package com.example.web01.repository;

import com.example.web01.constant.ItemSellStatus;
import com.example.web01.entity.Item;
import com.example.web01.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @DisplayName("상품입력테스트")
    @Test
    public void createItemTest(){
        for (int i=1; i<=10; i++) {
            Item item = new Item();

            item.setItemNm("테스트상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            //item.setULocalDateTime.now());

            //db반영
            Item savedItem = itemRepository.save(item);

            System.out.println(savedItem);
        }//  end for
    }


    // list Test
    @DisplayName("상품명 조회테스트")
    @Test
    public void findByItemNmTest(){
        this.createItemTest();// 상품정보 입력하는 메서드 호출
        List<Item> itemList = itemRepository.findByItemNm("테스트상품1");
        for(Item item : itemList){
            System.out.println("--- list ---");
            System.out.println(item.toString());
        }

    }

    // 상풍명, 상품상세설명 조회 테스트
    @Test
    @DisplayName("상품명,상품상세설명기준으로 조회")
    public void findByitemNmOritemDetail(){
        this.createItemTest();
        List<Item> itemList =
                itemRepository
                    .findByItemNmOrItemDetail("테스트상품1","테스트 상품 상세 설명10");

        itemList.stream().forEach(item -> {
            System.out.println("--- 상품명, 상품상세설명기준으로 조회 ---");
            System.out.println(item);
        });
    }

    @DisplayName("가격 LessThan 테스트")
    @Test
    public void findByPriceLessThanTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        System.out.println("--- 관계(비교)연산자 조회 ---");
        itemList.stream().forEach(System.out::println);
    }

    @DisplayName("가격기준으로 내림차순 조회 테스트")
    @Test
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemTest();

        List<Item> listItem = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        System.out.println("--- 가격기준으로 내림차순 조회 테스트 ---");
        listItem.stream().forEach(System.out::println);

    }

    // 어노테이션 적용1
    @Test@DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetail(){
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        itemList.stream().forEach( item -> {
            System.out.println(item.toString());
        });
    }


    // 어노테이션 적용2
    @Test@DisplayName("nativeQuery를 이용한 상품 조회 테스트")
    public void findByItemDetailByNative(){
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        itemList.stream().forEach( item -> {
            System.out.println(item.toString());
        });
    }

    //Querydsl 조회 테스트
    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest(){
        this.createItemTest();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명"+"%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        itemList.stream().forEach(System.out::println);

    }

    public void createItemList2(){
        IntStream.rangeClosed(1,5).forEach(i -> {
                Item item = new Item();
                item.setItemNm("테스트 상품" + i);
                item.setPrice(10000 + i);
                item.setItemDetail("테스트 상품 상세 설명" + i);
                item.setItemSellStatus(ItemSellStatus.SELL);
                item.setStockNumber(100);
                item.setRegTime(LocalDateTime.now());
                //item.setUpdatetime(LocalDateTime.now());
                itemRepository.save(item);
        });
        IntStream.rangeClosed(6,10).forEach(i->{
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            //item.setUpdatetime(LocalDateTime.now());
            itemRepository.save(item);
        });
        /*
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdatetime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdatetime(LocalDateTime.now());
            itemRepository.save(item);
        }
        */
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트2")
    public void queryDslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem qItem = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(qItem.itemDetail.like("%"+itemDetail+"%"));
        booleanBuilder.and(qItem.price.gt(price));
        System.out.println(ItemSellStatus.SELL);

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        PageRequest pageable = PageRequest.of(0,5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("totoal elements : "+itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        resultItemList.stream().forEach(System.out::println);

    }





}

/*

영속성 컨텍스트
어플리케이션과 데이터베이스사이에 영속성 컨텍스트라는 중간계층

1. id조회
2.Persistance Context (1차캐시)
  2.1 있으면 가져오기
  2.2없으면
     1차캐시에 저장되어 있지 않으면 DB조회
     엔티티를 1차 캐시에 저장
 3. 1차 캐시에 있는 내용을 가져옴


트랜잭션 지원하는 쓰기 지연
영속성 컨텍스트에는 스기 지연 SQL저장소가 존재
entityManager.persist()를 호출하면 1차 캐시에 저장되는 것과
동시에 쓰지지연 SQL저장소에 SQL문이 저장
SQl을 모아두고 트랜잭션을 커밋하는 시점에 저장된 SQL문들이 flush되면 DB반영




쿼리 메소드(find()) -> select ~~~
select id, name from tablename where id=1
형식
find+(엔티티이름)+By+변수(필드,테이블컬럼)이름

Keyword Sample                          JPQL
And     findBy필드명1And필드명2           -> where x.필드명1 = ?1 and x.필드명2 = ?2

in      findByAgeIn(Collection<Age>...) -> where x.age in ?1




 */



