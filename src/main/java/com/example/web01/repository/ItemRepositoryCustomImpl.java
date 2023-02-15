package com.example.web01.repository;

import com.example.web01.constant.ItemSellStatus;
import com.example.web01.dto.ItemSearchDto;
import com.example.web01.entity.Item;
import com.example.web01.entity.QItem;
import com.example.web01.service.ItemImgService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    // 동적 쿼리 객체 선언
    private JPAQueryFactory queryFactory;


    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
        BooleanExpression은 null일 경우 그냥 null만 반환해도 되지만, BooleanBuilder는 항상 BooleanBuilder라는 객체를 생성하여 반환
        BooleanExpression은 ","을 이용해서 where조건에 여러 BooleanExpression의 조건들을 나열
     */

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        // 삼항(조건)연산자 => (조건식) ? 참처리 : 거짓처리
        //  Returns: this == right
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    // 현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회을 위한 조건식
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if (StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if (StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }
        // dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간이후로 상품조회하도록 조건값을 반환
        // Returns: this > right
        return QItem.item.regTime.after(dateTime);

    }
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if (StringUtils.equals("itemNum", searchBy)) {
            // Returns: this like str => where itemNm like '%홍길%'
            return QItem.item.itemNm.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)){
            // Returns: this like str => where createdBy like '%홍길%'
            return QItem.item.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }


    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory
                // 상품 데이터를 조회를 위한 QItem의 item지정: select ~ from Item
                .selectFrom(QItem.item)
                .where(
                        regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                        )
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스
                .limit(pageable.getPageSize())  // 한 번에 가지고 올 최대 개수를 지정
                .fetch(); // 상품 데이터 리스트 조회 및 데이터 전체 개수

                // select p from Post p	offset 3 limit 10
                // 3번째 row에서 부터 10개의 row를 가져온다는 의미

        long total = queryFactory
                .select(Wildcard.count) // select count(*) where id= 10
                .from(QItem.item)
                .where(
                        regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                )
                .fetchOne();

        //페이징 처리의 최종 결과는 Page<T> 타입으로 반환하는 것
        //Querydsl에서 직접처리하지 않고 PageImpl클래스를 제공해서 Page<T>를 생성
        // PageImpl<T>(List<T>, Pageable, long): 3개의 파라미터
        // List<T> : 실제목록 데이터, Pageble:페이지 관련 정보를 가진 객체, long:전체개수
        return new PageImpl<>(content, pageable, total);
    }
}

// fetchOne() : 조회대상이 1건이면 해당 타입 반환, 1건이상이면 에러발생
// fetchFirst(): 조회대상이 1건 또는 1건 이상이면 1건만 반환
// fetchCount(): 전체 개수 반환