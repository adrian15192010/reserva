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

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/habilitate")
    public String habilitate(){
        return "habilitate";
    }

    @GetMapping("/clave")
    public String gif(){
        return "recuperarClave";
    }

}
