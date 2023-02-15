package com.example.web01.service;

import com.example.web01.dto.ItemFormDto;
import com.example.web01.dto.ItemImgDto;
import com.example.web01.dto.ItemSearchDto;
import com.example.web01.entity.Item;
import com.example.web01.entity.ItemImg;
import com.example.web01.repository.ItemImgRepository;
import com.example.web01.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(
            ItemFormDto itemFormDto,// 상품정보, 상품이미지 리스트
            List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            // 첫번째이미지을 대표이미지로 설정
            if (i==0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            // 이미지 파일 처리하는 부분
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        // 특정 상품에 대한 상품이미지 모두 추출(조회)
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        // 엔티티 -> DTO로 데이터 복사
        for (ItemImg itemImg: itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        // 특정 상품 기본 정보 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        // 엔티티 -> DTO로 데이터 복사
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    public Long updateItem(
            ItemFormDto itemFormDto,
            List<MultipartFile> itemImgFileList
            ) throws Exception{

        // 상품수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // dto -> entity에 복사(entity값이 변경->자동으로 update동작)
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 상품 이미지 수정
        for(int i=0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(
                    itemImgIds.get(i),// 상품이미지 아이디
                    itemImgFileList.get(i)); // 상품이미지 파일명

        }

        return item.getId();
    }


    // ItemRepositoryCustom인터페이스 작성한 메서드 호출: 상품 목록 조회하는 메서드
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

// 상품 서비스 : 상품기본정보, 상품이미지,... 상품 조회
//
//

}


