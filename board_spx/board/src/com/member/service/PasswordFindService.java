package com.member.service;

import com.common.AppService;
import com.common.LoginUtil;
import com.common.ServiceForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class PasswordFindService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //로그인 여부
        if (LoginUtil.isLogin(request)) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); location.href='/';")
                    .build();
        }
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "member/passwordfindform.jsp")
                .build();
    }
}
