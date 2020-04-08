package com.zsx.md.controller;

import com.zsx.md.vo.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {


    @PostMapping("/in")
    @ResponseBody
    public ResultData login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "token") String token) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(token);
        return ResultData.build(true, "", null);
    }

}
