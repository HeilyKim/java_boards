package com.example.service;

import com.common.AppService;
import com.common.ServiceForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class MainService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "main.jsp")
                .build();
    }
}
