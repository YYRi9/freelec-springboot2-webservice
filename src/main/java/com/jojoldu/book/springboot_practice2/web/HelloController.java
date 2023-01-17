package com.jojoldu.book.springboot_practice2.web;

import com.jojoldu.book.springboot_practice2.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // /hello로 요청이 오면 문자열 "hello"를 반환한다.
    @GetMapping("/hello")   // 컨트롤러가 JSON을 반환하도록 만들어준다.
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
