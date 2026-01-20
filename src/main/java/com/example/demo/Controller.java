package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/con")
@RestController
public class Controller {


    @GetMapping("/get")
    String getData()
    {
        return "hello6";
    }
}
