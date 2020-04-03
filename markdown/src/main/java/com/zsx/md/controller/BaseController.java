package com.zsx.md.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public String getHeader(HttpServletRequest request, String header) {

        System.out.println(request.getSession().getId());
        System.out.println(request.getRequestedSessionId());

        return request.getHeader(header);
    }

    public String getHeaderUser(HttpServletRequest request) {
        String user = getHeader(request, "user");
        return user;
    }
}
