package org.zerock.ex2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public String main(@RequestParam("name") String name){
        String str = "Hi~ " + name;
        return "<h1>" + str + "<h1/>";
    }
}
