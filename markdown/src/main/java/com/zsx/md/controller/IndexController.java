package com.zsx.md.controller;

import com.alibaba.fastjson.JSON;
import com.zsx.md.config.AllowURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private AllowURL allowURL;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        List<String> allowUrls = allowURL.getUrls();
        System.out.println(JSON.toJSONString(allowUrls));
        model.addAttribute("name", UUID.randomUUID().toString());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("name", UUID.randomUUID().toString());
        return "login";
    }

}
