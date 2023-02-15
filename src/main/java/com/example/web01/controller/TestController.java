package com.example.web01.controller;

import com.example.web01.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public UserDto test(){
        UserDto userDto = new UserDto();

        userDto.setName("20");
        userDto.setAge(10);


        return userDto;
    }

}
