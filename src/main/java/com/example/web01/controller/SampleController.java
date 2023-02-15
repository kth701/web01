package com.example.web01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
public class SampleController {

    @GetMapping("/hello")
    public void hello(Model model){
        log.info("/hello .....");

        model.addAttribute("msg","Hello World");
    }
    @GetMapping("/helloArray")
    public String helloArray(Model model){
        log.info("/helloArray .....");

        List<String> list = Arrays.asList("AAA","BBB","CCC");

        model.addAttribute("list",list);

        return "helloArray";// helloArray.html으로 포워딩
    }
}


/*

JPA(Java Persistence API)는 자바ORM 기술에 대한 API표준
ORM(Object Relation Mapping) : 객체와 관계형데이터베이스를 매핑

자바는 객체지향 페러다임, 관계형 데이터 베이스는 데이터 정규화
기존
 - 객체를 db에 넣기 과정 : sql문장을 통해 변환하여 저장, db에서 객체로 다시 가져오는 구조

 자바 객체 <-> ORM <-> 관계(DB)

 JPA는 ORM기술의 표준 명세로 자바에서 제공하는 API
  - Hibernate, EclipseLink, DataNucleus,.....
 JPA: 데이터베이스 설계중심에서 객체지향적으로 설계가 가능하게하는 구조
 - sql문을 직접 작성하지 않아도 된다. 유지보수가 용이
 - JPA는 Native SQL을 통해 기존의  SQL을 사용, 특정 데이터베이스에 종속된다 단점.


Entity(엔티티) : db의 테이블에 대응하는 클래스
@Entity 어노테이션을 적용

Entity Manager Factory(엔티티 매니저 팩토리) : 엔티티 메니저 인스턴스를 관리하는 주체
 - 하나의 app실행시 1개반 사용가능, 엔티티 매니저 팩토리로부터 엔티티매니저를 생성

 Entity Manager Factory -> Entity Manager -> Persistance Context:영속성 컨텍스트(Entity, Entity,...)







 */