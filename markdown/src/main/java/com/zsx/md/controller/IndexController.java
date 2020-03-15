package com.zsx.md.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class IndexController {

    private static String context = "";

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "demo";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(String text) {
        System.out.println(text);
        context = text;
        return "ok";
    }

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        return context;
    }
}
