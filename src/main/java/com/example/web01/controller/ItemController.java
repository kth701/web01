package com.example.web01.controller;


import com.example.web01.dto.ItemFormDto;
import com.example.web01.dto.ItemImgDto;
import com.example.web01.dto.ItemSearchDto;
import com.example.web01.entity.Item;
import com.example.web01.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model ){
        model.addAttribute("itemFormDto", new ItemFormDto());
        // 상품 등록하는 페이지로 포워딩
        return "/item/itemForm";
    }
    @PostMapping(value = "/admin/item/new")
    public String itemNew(
            @Valid ItemFormDto itemFormDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList
            ){

        if (bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        // 유효성 검사가 정상으로 통과 되면 처리하는 부분
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        // 상품 등록하는 페이지로 포워딩
        return "redirect:/";
    }


    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            //return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(
            @Valid ItemFormDto itemFormDto,
            BindingResult bindingResult,
            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
            Model model){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    // URL에 페이지번호가 없는 경우와 페이지 번호가 있는 경우 2가지 매핑
    @GetMapping(value= {"/admin/items", "/admin/items/{page}"})
    public String itemManage(
                    ItemSearchDto itemSearchDto,
                    @PathVariable("page")Optional<Integer> page,  // Optional<T> optional:'null일 수도 있는 객체'를 감싸는 일종의 Wrapper 클래스
                    Model model
                    ){

        // Optional 객체 접근: get(), orElse(), orelaseGet()
        // get(): isPresent()로 체크한 후에 이 get 메서드를 사용
        // Optional 내부에 담긴 객체를 반환, null객체이면 NoSuchElementException이 발생
        Pageable pageable =
                // PageRequest.of(시작 페이지 인덱스, 읽어올 row 개수)
                // url에 페이지 번호가 없으면 0, 있으면 해당 페이지 번호가 시작페이지
                PageRequest.of(page.isPresent()? page.get():0,3);

        Page<Item> items =
                itemService.getAdminItemPage(itemSearchDto, pageable);

        // View페이지에 전달할 객체 생성 및 설정
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage", 5); // 1블럭당 페이지 개수


        return "item/itemMng";
    }



}

/*
pageable이 가지고 있는 속성

    int getNumber();                    //현재 페이지
    int getSize();                      //페이지 크기
    int getTotalPages();                //전체 페이지 수
    int getNumberOfElements();   		//현재 페이지에 나올 데이터 수
    long getTotalElements();          	//전체 데이터 수
    boolean hasPreviousPage();     		//이전 페이지 여부
    boolean isFirstPage();              //현재 페이지가 첫 페이지 인지 여부
    boolean hasNextPage();            	//다음 페이지 여부
    boolean isLastPage();               //현재 페이지가 마지막 페이지 인지 여부
    Pageable nextPageable();          	//다음 페이지 객체, 다음 페이지가 없으면 null
    Pageable previousPageable();    	//다음 페이지 객체, 이전 페이지가 없으면 null
    List<T> getContent();               //조회된 데이터
    boolean hasContent();               //조회된 데이터 존재 여부
    Sort getSort();                     //정렬정보
 */
