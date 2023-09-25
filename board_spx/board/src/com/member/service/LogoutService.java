package com.member.service;

import com.common.AppService;
import com.common.ServiceForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.common.Constants.BASIC_VIEW_PATH;

public class LogoutService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("mi");
        session.invalidate();
        return ServiceForward.builder()
                .redirect(true)
                .path("/")
                .build();
    }
}
