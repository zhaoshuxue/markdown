package com.zsx.md.controller;

import com.baomidou.kisso.SSOHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class IndexController {



    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());



        return "index";
    }

}
