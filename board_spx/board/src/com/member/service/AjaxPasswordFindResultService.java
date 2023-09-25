package com.member.service;

import com.common.AjaxUtill;
import com.common.AppService;
import com.common.LoginUtil;
import com.common.ServiceForward;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.common.Validator.isStringEmpty;
import static com.common.Validator.isValidated;

public class AjaxPasswordFindResultService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //로그인 여부 확인
        if (LoginUtil.isLogin(request)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }
        //폼 데이터 로드
        String loginId = request.getParameter("loginId");
        String name = request.getParameter("name");
        String mobileNo = request.getParameter("mobileNo");
        String email = request.getParameter("email");

        //데이터 확인
        if (!isValidated(name, "^[가-힣]{2,10}$", true)
                || !isValidated(mobileNo, "^[0-9]{10,12}$", true)
                || !isValidated(email, "^.{1,50}$", true)
                || isStringEmpty(loginId)) {
            return AjaxUtill.buildAjaxResult(request, false, "올바른 형식으로 입력하세요");
        }

        //DTO data setting
        MemberDTO memberDTO = MemberDTO.builder()
                .loginId(loginId)
                .name(name)
                .mobileNo(mobileNo)
                .email(email)
                .build();
        //db 검색
        MemberDs ds = new MemberDs();
        int id = ds.selectMemberIdForFindPassword(memberDTO);
        if (id == 0) {  //얜 오류난게 아님 = true
            return AjaxUtill.buildAjaxResult(request, true, "회원정보를 찾을수 없따");
        } else {
            //세션에 id 저장
            HttpSession session = request.getSession();
            session.setAttribute("MemberId",id);

            return AjaxUtill.buildAjaxResult(request, true, null);
        }
    }
}
