package com.example.web01.repository;

import com.example.web01.dto.ItemSearchDto;
import com.example.web01.entity.Item;
import com.example.web01.service.ItemImgService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    // 페이징 처리의 최종 결과는 Page<T> 타입으로 반환하는 것
    // Querydsl에서 직접처리하지 않고 PageImpl클래스를 제공해서 Page<T>를 생성
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}


