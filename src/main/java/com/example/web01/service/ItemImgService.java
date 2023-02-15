package com.example.web01.service;


import com.example.web01.entity.ItemImg;
import com.example.web01.repository.ItemImgRepository;
import com.example.web01.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    // 상품이미지 업로드 경로: 'D:/file_repo/item/'
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    // 상품이미지 처리
    private final ItemRepository itemRepository;
    // 파일 처리
    private final FileService fileService;
    private final ItemImgRepository itemImgRepository;

    // 상품 이미지 등록
    public void saveItemImg(
            ItemImg itemImg,
            MultipartFile itemImgFile ) throws Exception{

        // 업로드했던 상품 이미미지 파일의 원래이름
        String oriImgName = itemImgFile.getOriginalFilename();
        // 실제 로컬에 저장된 상품 이미지 파일이름
        String imgName = "";
        // 업로드 결과 로컬에 저장된 상품 이미지 파일 경로
        String imgUrl = "";

        // 파일업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(
                    itemImgLocation,
                    oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }

        // 상품 이미지 정보
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);

    }



    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;

            // 상품이미지 업데이터(엔티티값이 변경되면 update 자동실행)
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }




}
