package com.example.web01.dto;

import com.example.web01.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import com.example.web01.entity.Item;

@Getter@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = " 상품명은 필수 입력 값입니다.")
    private String itemNm;
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;
    @NotBlank(message = "상품 상세 설명은 필수 입력 값입니다.")
    private String itemDetail;
    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    // 수정할 때 상품이미지 정보를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    // 상품이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();

    // modelMapper객체 : 엔티티 객체와 DTO객 간의 데이터를 복사하여 복사한 객체를 반환
    private static ModelMapper modelMapper = new ModelMapper();
    public Item createItem(){
        // ItemFormDto -> item 엔티티
        return modelMapper.map(this, Item.class) ;
    }
    public static ItemFormDto of (Item item){
        // Item엔티티 -> ItemFormDto
        return modelMapper.map(item, ItemFormDto.class) ;
    }


}
