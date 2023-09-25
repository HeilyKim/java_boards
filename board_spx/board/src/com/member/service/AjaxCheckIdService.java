package com.member.service;

import com.common.AjaxUtill;
import com.common.AppService;
import com.common.ServiceForward;
import com.dto.AjaxResultDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Pattern;

import static com.common.Constants.BASIC_VIEW_PATH;

public class AjaxCheckIdService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String 변수 loginId를 선언하고 loginId를 키 값으로 데이터를 로드하여 저장
        String loginId = request.getParameter("loginId");

        //loginId가 null, 빈값,정규표현식 통과 여부 확인
        if (loginId == null || loginId.equals("") || !Pattern.matches("^[a-z0-9]{4,15}$", loginId)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }

        //db에서 loginId의 데이터와 같은 loginId가 몇개인지 확인
        MemberDs ds = new MemberDs();
        int count = ds.selectAccountCountByLoginId(loginId);
        return AjaxUtill.buildAjaxResult(request
                , count == 0 ? true : false
                , count == 0 ? "사용할 수 있는 아이디" : "사용할 수 없는 아이디 입니다");
    }
}
