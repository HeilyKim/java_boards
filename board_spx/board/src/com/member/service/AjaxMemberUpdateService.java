package com.member.service;

import com.common.*;
import com.dto.AjaxResultDTO;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static com.common.Constants.BASIC_VIEW_PATH;
import static com.common.Validator.isValidated;

public class AjaxMemberUpdateService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //폼 데이터 로드
        //이름 휴대번호 생년월일 이메일 주소3

        String name = request.getParameter("name");
        String mobileNo = request.getParameter("mobileNo");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String zipcode = request.getParameter("zipcode");
        String address = request.getParameter("address");
        String addressDetail = request.getParameter("addressDetail");

        //데이터 확인
        if (!isValidated(name, "^[가-힣]{2,10}$", true)
                || !isValidated(mobileNo, "^[0-9]{10,12}$", true)
                || !isValidated(birthday, "^[0-9]{8,8}$", false)
                || !isValidated(email, "^.{1,50}$", true)
                || !isValidated(zipcode, "^[0-9]{5,5}$", true)
                || !isValidated(address, "^.{1,100}$", true)
                || !isValidated(addressDetail, "^.{1,100}$", true)) {
            return AjaxUtill.buildAjaxResult(request,false,"회원정보 수정에 실패했따");
        }

        //DTO data setting
        MemberDTO memberDTO = MemberDTO.builder()
                .id(LoginUtil.getMemberId(request))
                .name(name)
                .mobileNo(mobileNo)
                .email(email)
                .zipcode(zipcode)
                .address(address)
                .addressDetail(addressDetail)
                .build();
        //생일은 선택값이라서 데이터 존재시만 날짜로 변환
        if (birthday != null || !birthday.equals("")) {
            birthday = birthday.substring(0, 4)
                    + "-" + birthday.substring(4, 6)
                    + "-" + birthday.substring(6);
            memberDTO.setBirthday(LocalDate.parse(birthday));
        }
        //db 저장
        MemberDs ds = new MemberDs();
        boolean isSuccess = ds.updateMemberInfo(memberDTO);
        if (!isSuccess) {
            return AjaxUtill.buildAjaxResult(request,false,"회원정보 수정에 실패했따");
        }
        //뷰 이동 데이터 세팅 및 리턴
        return AjaxUtill.buildAjaxResult(request,true,"회원정보 수정했따");
    }
}
