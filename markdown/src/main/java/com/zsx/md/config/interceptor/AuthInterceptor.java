package com.zsx.md.config.interceptor;

import com.google.common.collect.Lists;
import com.zsx.md.config.AllowURL;
import com.zsx.md.config.SpringUtil;
import com.zsx.md.config.exception.MyException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 自定义权限拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static List<String> excludeUrls = Lists.newArrayList(); // 不需要拦截的资源

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestURI.substring(contextPath.length() + 1);

        System.out.println(request.getServletPath());
        System.out.println(url);

        if (excludeUrls.size() == 0) {
            AllowURL allowURL = SpringUtil.getBean(AllowURL.class);
            excludeUrls = allowURL.getUrls();
        }

        if ("".equals(url) || validateUrls(url)) {
            return true;
        }

        if (session.getAttribute("userName") == null) {

            throw new MyException("404", "page not found");
//            throw new RuntimeException("未登录");

//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
//            response.sendRedirect(contextPath + "/login");
//            return false;
        }

        return true;
    }


    /**
     * 验证url是否在允许访问的url列表里面，即未登录既可以访问的url
     *
     * @param url
     * @return
     */
    private boolean validateUrls(String url) {
        url = "/" + url;

        if (excludeUrls.contains(url)) {
            return true;
        }
        for (String string : excludeUrls) {
            if (string.endsWith("*") &&
                    url.indexOf(string.substring(0, string.length() - 1)) != -1) {
                return true;
            }
        }
        return false;
    }


}
