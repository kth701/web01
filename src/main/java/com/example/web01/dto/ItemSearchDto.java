package com.example.web01.dto;

import com.example.web01.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemSearchDto {

    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";
}

/*
1. 현재 시간과 상품등록일을 비교해서 상품 데이터를 조회
    searchDateType
        - all: 상품 등록일 전체
        - 1d: 최근하루 동안 등록된 상품 조회
        - 1w: 최근 일주일
        - 1m: 최근 한달
        - 6m: 최근 6개월
2. 상품의 판매상태를 기준 조회
    searchSellStatus
3. 상품을 조회할 때 어떤 유형으로 조회
    private String searchBy
      - itemNm : 상품명 기준
      - createBy: 상품 등록자 아이디
5. 검색 키워드 기준 조회
    searchQuery


 */
