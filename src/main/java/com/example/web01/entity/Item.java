package com.example.web01.entity;

import com.example.web01.constant.ItemSellStatus;
import com.example.web01.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    // 상품 정보 : 상품코드, 가격, 상품명, 상품상세설명, 판매상태, 상품등록일, 상품수정일...
    @Id     //PRIMARY `KEY
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO_INCREMENT
    private Long id;//상품코드

    @Column(nullable = false, length=50)    // not null설정, 문자길이 50
    private String itemNm;//상품명

    @Column(name="price", nullable = false)
    private int price;//상품가격

    @Column(nullable = false)
    private int stockNumber;//상품 재고량

    @Lob    // BLOB,CLOB타입 매핑
    @Column(nullable = false)
    private String itemDetail;//상품상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;//상품판매상태(판매중, 재고없을 경우 노출회피,...)

    //private LocalDateTime regTime;//등록 시간
    //private LocalDateTime updatetime;// 수정 시간

    // 업데이트 관련 작업
    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }



//    public void updateItem(ItemFormDto itemFormDto){
//        this.itemNm = itemFormDto.getItemNm();
//        this.price = itemFormDto.getPrice();
//        this.stockNumber = itemFormDto.getStockNumber();
//        this.itemDetail = itemFormDto.getItemDetail();
//        this.itemSellStatus = itemFormDto.getItemSellStatus();
//    }







}
/*
@Column속성

name                필드와 매핑할 컬럼의 이름(생략시 객체의 필드이름 적용)
unique(DDL)         유니크 제약 조건 설정
insertable          insert가능 여부(true)
updatable           update가능 여부(true)
length              String 타입의 문자 길이 제약조건(255)
nuable(DDL)         null값 허용여부
columnDefinition    db컬럼정보기술: @Column(columnDefinition="varchar(5) defaul '10' not null)
percision, scale(DDL)
    BigDecimal타입
    percision소수점을 포함한 전체 자리수, scale소수점 자리수(Double, float적용되지 않음)


GenerationType.AUO(default)
GenerationType.IDENTITY(기본키 생성을 데이터베이스에 위임)
GenerationType.SEQUENCE(시퀸스오브젝트를 이용한 기본키 생성):@SequenceGeneratior를 적용
GenerationType.TABLE : 키 생성용 테이를 사용, @TableGenerator필요
 */

