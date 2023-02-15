package com.example.web01.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="item_img")
@Getter@Setter
public class ItemImg extends BaseEntity {
    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; // 상품 이미지 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 검토
    private String repimgYn; // 대표이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;


    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}

/*

상품등록 및 수정에 사용할 데이터 전달용 DTO클래스


상품정보(item)
 : item_id(PK), item_nm, price, stock_number, item_detail, item_sell_status
상품이미지(item_img)
 :item_img_id(PK), item_id(FK), img_name, ori_img_name, img_url, rep_img_yn


DTO(객체 자료 저장소) :  form정보
 */