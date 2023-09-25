package com.example.service;

import com.common.AppService;
import com.common.ServiceForward;
import com.example.ds.ExampleDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class ResultService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String name 선언후 myName 키 값으로 입력한 데이터 저장
        String name = request.getParameter("myName");

        ExampleDs ds = new ExampleDs();
        ds.insertName(name);

        String age = request.getParameter("myAge");

        //request 객체에 뷰로 전송할 name 데이터 저장
        request.setAttribute("name", name);
        request.setAttribute("age", age);

        //serviceforward 객체 forward 선언하고 빌더 통해 경로 데이터 저장
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "result.jsp")
                .build();
    }
}
