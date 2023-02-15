package com.example.web01.controller;

import com.example.web01.dto.ItemDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value="/ex01")
    public String thymeleafExample01(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품상세설명");
        itemDto.setItemNm("테스트상품1");
        itemDto.setPrice(10000);;
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);

        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String thymeleafExample02(@NotNull Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();

        IntStream.rangeClosed(1, 10).forEach( i-> {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품상세설명"+i);
            itemDto.setItemNm("테스트상품"+i);
            itemDto.setPrice(10000+i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        });

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx02";
    }

    // 조건 처리
    @GetMapping("/ex03")
    public String thymeleafExample03(Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();
        IntStream.rangeClosed(1,10).forEach( i-> {

            ItemDto itemDto = new ItemDto();

            itemDto.setItemDetail("상품상세설명"+i);
            itemDto.setItemNm("테스트상품"+i);
            itemDto.setPrice(10000+i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);

        });

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx03";
    }
    // 링크(메뉴링크)
    @GetMapping("/ex04")
    public String thymeleafExample04(){

        return "thymeleafEx/thymeleafEx04";
    }
    @GetMapping("/ex05")
    public String thymeleafExample05(
            String name, String age,
            //@Param("name") String name1, @Param("age") age,
            Model model
            ){

        model.addAttribute("name",name);
        model.addAttribute("age",age);

        return "thymeleafEx/thymeleafEx05";
    }

    // th:fragment 테스트
    @GetMapping("/ex06")
    public String thymeleafEx06(){
        return "thymeleafEx/thymeleafEx06";
    }

}
