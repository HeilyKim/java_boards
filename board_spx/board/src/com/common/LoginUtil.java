package com.common;

import com.dto.MemberDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//login 여부 확인
public class LoginUtil {
    public static boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("mi");
        if (dto != null) {
            return true;
        } else {
            return false;
        }
    }

    public static int getMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("mi");
        return dto.getId();
    }

    public static void setRequestURI(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        session.setAttribute("goto", requestURI);
    }

    public static String getRequestURI(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String requestURI = (String) session.getAttribute("goto");
        if (requestURI == null){
            requestURI = "/";
        }
        session.removeAttribute("goto");
        return requestURI;
    }
}
