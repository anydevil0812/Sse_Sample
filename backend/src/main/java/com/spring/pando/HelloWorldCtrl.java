package com.spring.pando;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldCtrl {

    @GetMapping("/hello")
    public String helloWorld() {

        System.out.println("----------------------------------------------------------0");
        return "hello!";
    }

}
