package com.member.service;

import com.common.*;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LeaveService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!LoginUtil.isLogin(request)) { //회원탈퇴는 로그인이 되어었어야됨
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }

        int id = LoginUtil.getMemberId(request);

        //폼의 데이터 로드
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        //id를 사용하여 탈퇴 처리
        MemberDs ds = new MemberDs();
        boolean isSuccess = ds.updateLeave(id);
        if (!isSuccess) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('회원탈퇴에 실패했따'); history.back();")
                    .build();
        }

        //*로그아웃
        //세션에 회원정보 저장
        HttpSession session = request.getSession();
        //세션 정보 다 파기
        session.removeAttribute("mi");
        session.invalidate();

        return ServiceForward.builder()
                .path("/")
                .redirect(true)
                .build();
    }
}
