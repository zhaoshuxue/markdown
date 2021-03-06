package com.zsx.md.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.google.common.collect.Lists;
import com.zsx.md.config.AllowURL;
import com.zsx.md.config.Constants;
import com.zsx.md.config.SpringUtil;
import com.zsx.md.config.exception.MyException;
import com.zsx.md.utils.HttpUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 自定义权限拦截器
 * 主要获取登录信息
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        HttpSession session = request.getSession();

        SSOToken ssoToken = SSOHelper.getSSOToken(request);
        System.out.println("打印ssoToken");
        System.out.println(JSON.toJSONString(ssoToken));
        String userId = ssoToken.getId();

        // 如果大于1个小时,强制退出
        if ((System.currentTimeMillis() - ssoToken.getTime()) > (60 * 60 * 1000)) {
            SSOHelper.clearRedirectLogin(request, response);
            return false;
        } else {
            if ((System.currentTimeMillis() - ssoToken.getTime()) > (30 * 60 * 1000)) {
                ssoToken.setTime(System.currentTimeMillis());
                SSOHelper.setCookie(request, response, ssoToken, false);
            }
        }

        Object userIdObject = session.getAttribute(Constants.USERID);
        if (userIdObject != null) {
            if (String.valueOf(userIdObject).equals(userId)) {

                printTime(start);
                return true;
            }
        }

        JSONObject userInfo = HttpUtil.getUserInfo(userId);

        System.out.println(JSON.toJSONString(userInfo));

        session.setAttribute(Constants.USERID, userId);

        printTime(start);
        return true;
    }

    private void printTime(long start) {
        long end = System.currentTimeMillis();
        System.out.println("LoginInterceptor耗时：" + (end - start) + "ms");

    }
}
