package com.example.web01.dto;

import com.example.web01.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter@Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn; // 대표이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    // 상품이미지 엔티티 객체를 넘겨받음
    public static ItemImgDto of(ItemImg itemImg){
        // 상품이미지 엔티티 객체와 상품이미지DTO객체를 1:1 맵핑
        // itemImg객체의 자료형과 멤버변수의 이름이 같을 때 ItemImgDto로 값을 복사
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}
