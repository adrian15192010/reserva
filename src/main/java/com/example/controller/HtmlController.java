package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/html")
public class HtmlController {

    @GetMapping("/index")
    public String index(){
         return "index";
     }

    @GetMapping("/habilitate")
    public String habilitate(){
        return "habilitate";
    }

}
