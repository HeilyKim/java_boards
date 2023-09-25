package com.member.service;

import com.common.AppService;
import com.common.LoginUtil;
import com.common.ServiceForward;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class MemberInfoService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //로그인 여부 확인
        if (!LoginUtil.isLogin(request)){
            //요청한 uri를 session에 저장
            LoginUtil.setRequestURI(request);

            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('로그인이 필요한 서비스 입니다'); location.href = '/login.do';")
                    .build();
        }
        //login 정보의 id를 이용하여 데이터베이스에서 회원정보를  검색
        int id = LoginUtil.getMemberId(request);
        MemberDs ds = new MemberDs();
        MemberDTO dto = ds.selectMemberByInfoById(id);
        if (dto == null){
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('회원 정보를 찾을 수 없습니다'); history.back();")
                    .build();
        }
        //뷰 이동을 위한 데이터 세팅
        request.setAttribute("info",dto);
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "member/info.jsp")
                .build();
    }
}
