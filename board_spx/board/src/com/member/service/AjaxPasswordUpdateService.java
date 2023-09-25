package com.member.service;

import com.common.*;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static com.common.Constants.BASIC_VIEW_PATH;
import static com.common.Validator.isValidated;

public class AjaxPasswordUpdateService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //login 여부 확인
        if (LoginUtil.isLogin(request)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }
        //id 가져오기
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("MemberId");

        if (id == null) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }

        //폼 데이터 로드
        //id 비번 비번확인 이름 휴대번호 생년월일 이메일 주소3
        String password = request.getParameter("password");
        String passwordCheck = request.getParameter("passwordCheck");

        //데이터 확인
        if (!isValidated(password, "^[a-z0-9]{4,15}$", true)
                || !isValidated(password, "^[a-z0-9!@#$]{4,15}$", true)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }

        if (!password.equals(passwordCheck)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근");
        }

        //비밀번호 암호화(복호화가 불가능)
        password = BCrypt.hashpw(password, BCrypt.gensalt(12));

        //DTO data setting
        MemberDTO memberDTO = MemberDTO.builder()
                .id(id)
                .password(password)
                .build();
        //db 저장
        MemberDs ds = new MemberDs();
        boolean isSuccess = ds.updateMemberPassword(memberDTO);
        if (!isSuccess) {

            return AjaxUtill.buildAjaxResult(request, false, "비밀번호 변경에 실패했따");
        }
        //저장된 키값 날리기
        session.removeAttribute("MemberId");
        //뷰 이동 데이터 세팅 및 리턴
        request.setAttribute("name", memberDTO.getName());
        return AjaxUtill.buildAjaxResult(request, true,
                "비밀번호 변경에 성공했따.\\n 변경된 비번으로 로그인해라");
    }
}
