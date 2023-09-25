package com.board.service;

import com.board.ds.BoardDs;
import com.common.*;
import com.dto.BoardDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxBoardDeleteService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!LoginUtil.isLogin(request)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }


        //데이터 검사
        String id = request.getParameter("id");
        if (!Validator.isValidated(id, "^[0-9]*$", true)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }

        BoardDs ds = new BoardDs();
        BoardDTO dto = ds.selectBoardDetailById(Integer.parseInt(id));
        if (dto == null) {
            return AjaxUtill.buildAjaxResult(request, false, "글을 삭제하는데 실패했따");
        }
        if (LoginUtil.getMemberId(request) != dto.getRegisterId()) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }
        boolean isSuccess = ds.deleteBoardById(Integer.parseInt(id));
        if (!isSuccess) {
            return AjaxUtill.buildAjaxResult(request, false, "글을 삭제하는데 실패했따");
        }
        return AjaxUtill.buildAjaxResult(request, true, "글 삭제 하였습니다");

    }
}
