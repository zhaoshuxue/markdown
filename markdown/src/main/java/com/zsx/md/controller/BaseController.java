package com.zsx.md.controller;

import com.zsx.md.config.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    public String getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(Constants.USERID);
        if (attribute != null) {
            return String.valueOf(attribute);
        }
        return null;
    }
}
