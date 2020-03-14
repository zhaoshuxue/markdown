package com.zsx.md.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class IndexController {


    @GetMapping
    public String index(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "index";
    }
}
