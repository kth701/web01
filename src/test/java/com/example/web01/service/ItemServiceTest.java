package com.example.web01.service;

import com.example.web01.constant.ItemSellStatus;
import com.example.web01.dto.ItemFormDto;
import com.example.web01.entity.Item;
import com.example.web01.entity.ItemImg;
import com.example.web01.repository.ItemImgRepository;
import com.example.web01.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "D:/file_repo/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품등록테스트")
    @WithMockUser(username="admin", roles = "ADMIN")
    void saveItem() throws Exception{
        // 상품엔티티(상품기본정보), 상품이미지엔티티(상품이미지정보)
        ItemFormDto itemFormDto = new ItemFormDto();

        // 상품기본정보
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemDetail("테스트상품설명입니다.");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

        // 상품이미지 정보(이미지-> 파일처리 -> MultipartFile)
        List<MultipartFile> multipartFiles = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDto, multipartFiles);

        // 상품 등록후 확인하는 절차
        // 특정 상품이미지 조회
        List<ItemImg> itemImgList =
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        // 특정 상품기본정보 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());

        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFiles.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());

        for (int i=0; i<multipartFiles.size(); i++){
            System.out.println(
                    multipartFiles.get(i).getOriginalFilename()+", "+
                    itemImgList.get(i).getOriImgName()+", "+
                    itemImgList.get(i).getImgName()+", "+
                    itemImgList.get(i).getImgUrl());

        }





    }


//
//    @Test
//    @DisplayName("상품 등록 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    void saveItem() throws Exception {
//        ItemFormDto itemFormDto = new ItemFormDto();
//        itemFormDto.setItemNm("테스트상품");
//        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
//        itemFormDto.setItemDetail("테스트 상품 입니다.");
//        itemFormDto.setPrice(1000);
//        itemFormDto.setStockNumber(100);
//
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
//        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
//        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
//        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
//        assertEquals(itemFormDto.getPrice(), item.getPrice());
//        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
//        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
//
//        for (int i=0; i<multipartFileList.size(); i++){
//            System.out.println(
//                    multipartFileList.get(i).getOriginalFilename()+", "+
//                    itemImgList.get(i).getOriImgName()+", "+
//                    itemImgList.get(i).getImgName()+", "+
//                    itemImgList.get(i).getImgUrl());
//
//        }
//    }

}