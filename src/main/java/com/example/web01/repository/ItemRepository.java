package com.example.web01.repository;

import com.example.web01.entity.Item;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository<엔티티이름, 엔티티에서 기본키로 설정된 필드의 타입>
// JpaRepository는 기본적인 CRUD및 페이징 처리를 위한 메서드가 정의되어 있음

// 1. querydsl미사용시
//public interface ItemRepository extends JpaRepository<Item, Long> {

// 2. querydsl사용시:  QuerydslPredicateExecutor,
// 2.1 상품관리페이지 목록 불러오는 ItemRepositoryCustom 인터페이스(사용자 작성한 인터페이스)
public interface ItemRepository extends JpaRepository<Item, Long> ,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    // 사용자가 직접 메서드 정의 : findBy필드(테이블컬럼,Entity)명
    // select * from item where itemNm = #{itemNm}
    List<Item> findByItemNm(String itemNm);
    // 상품이름, 상품상세설명
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    // 상품가격 기준(<,<=,>,>=,...)으로 조회
    // select * from item where price < #{금액}
    List<Item> findByPriceLessThan(Integer price);
    // 정렬: select * from item where price< #{금액} oreder by price desc
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // 어노테이션 활용 , 어노테이션 매개변수 표현 => ":매개변수이름"
    @Query("select i from Item i where i.itemDetail like " +
           "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetial);

    // 기존의 쿼리문을 그대로 사용할 수 있음, 특정 db에 종속이된다.
    @Query(value="select * from Item i where i.item_detail like %:itemDetail% order by i.price desc " ,  nativeQuery=true )
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetial);


    //Page<Item> findAll(BooleanBuilder booleanBuilder, PageRequest pageable);
}

/*
메서드
jpaRepository객체.save(S entity) : 엔티티 저장 및 수정
void delete(~) : 엔티티 삭제
count() : 엔티티 총 개수 반환
iterable<T> findAll() : 모든 엔티티 조회


save() -> insert ~ -> 반영


 */
