package com.board.service;

import com.common.AppService;
import com.common.LoginUtil;
import com.common.ServiceForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class BoardWriteService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!LoginUtil.isLogin(request)){
            //요청한 uri를 session에 저장
            LoginUtil.setRequestURI(request);

            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('로그인이 필요한 서비스 입니다'); location.href = '/login.do';")
                    .build();
        }
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "board/writeform.jsp")
                .build();
    }
}
