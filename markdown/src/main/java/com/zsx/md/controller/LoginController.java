package com.zsx.md.controller;

import com.zsx.md.utils.DESUtil;
import com.zsx.md.utils.SecureUtil;
import com.zsx.md.vo.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {


    @PostMapping("/in")
    @ResponseBody
    public ResultData login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "token") String token,
            HttpServletRequest request) {
        System.out.println(username);
        System.out.println(password);
        String encrypt = SecureUtil.encrypt(password);
        System.out.println(encrypt);
        System.out.println(token);


        request.getSession().setAttribute("username", username);

        return ResultData.build(true, "", null);
    }

}
