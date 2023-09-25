package com.example.service;

import com.common.AppService;
import com.common.ServiceForward;
import com.example.ds.ExampleDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Pattern;

import static com.common.Constants.BASIC_VIEW_PATH;

public class SearchService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//      String input 선언->입력한 myNameId를 로드 및 저장
        String input = request.getParameter("myNameId");

//      입력한 id가 숫자인지?  정규표현식 검사: String 만 매치 가능함 => ("^시작[인정받는 숫자]{문자열 길이} $종료",비교대상)
//                                                                   단어= a-z,ㄱ-ㅎ  전부 다 = [0-9a-zA-Z가-힣!@#$%^&*]
        if (input == null || input.equals("") || !Pattern.matches("^[0-9]{1,9}$", input)) {    //SCE개념:불필요한 연산자 생략
            return null;
        }
        //정수형 id 선언하고 input을 숫자로 변환
        int id = Integer.parseInt(input);

//      String형 name을 선언하고 db에서 id로 이름 검색 및 저장
        ExampleDs ds = new ExampleDs();
        String name = ds.selectNameById(id);
        if (name == null) {
            name = "검색 결과 없다";
        }
//      request객체의 attribute에 검색한 이름을 저장
        request.setAttribute("result", name);

//      view return
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "search.jsp")
                .build();
    }
}