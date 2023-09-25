package com.member.service;

import com.common.*;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginProcessService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (LoginUtil.isLogin(request)) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }
        //폼의 데이터 로드
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        //데이터 빈값 검사
        if (Validator.isStringEmpty(loginId)
                || Validator.isStringEmpty(password)) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }
        //입력한 아뒤로 db에서 회원 정보 검색
        MemberDs ds = new MemberDs();
        MemberDTO memberDTO = ds.selectMemberByInfoByLoginId(loginId);
        if (memberDTO == null){
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('id 또는 비밀번호를 확인해 주세요'); history.back();")
                    .build();
        }
        //비번 비교(입력된 비번이 암호화된 db저장된 비번과 같은지)
        boolean isSame = BCrypt.checkpw(password,memberDTO.getPassword());
        if (!isSame){ //비번 틀렸을때
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('id 또는 비밀번호를 확인해 주세요'); history.back();")
                    .build();
        }
        //세션에 회원정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("mi",memberDTO);
        return ServiceForward.builder()
                .path(LoginUtil.getRequestURI(request))
                .redirect(true)
                .build();
    }
}
