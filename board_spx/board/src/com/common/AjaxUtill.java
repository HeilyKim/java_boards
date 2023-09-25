package com.common;

import com.dto.AjaxResultDTO;

import javax.servlet.http.HttpServletRequest;

import static com.common.Constants.BASIC_VIEW_PATH;

public class AjaxUtill {
    public static ServiceForward buildAjaxResult(HttpServletRequest request, boolean success, String message){
        request.setAttribute("result", AjaxResultDTO.builder()
                .success(success)
                .message(message)
                .build());
        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "ajax/result.jsp")
                .build();
    }
}
