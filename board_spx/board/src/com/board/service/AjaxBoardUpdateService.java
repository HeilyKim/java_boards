package com.board.service;

import com.board.ds.BoardDs;
import com.common.*;
import com.dto.BoardDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxBoardUpdateService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!LoginUtil.isLogin(request)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }
        //데이터 검사
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String id = request.getParameter("aid");

        if (title == null || title.equals("") || title.length() > 50
                || contents == null || contents.equals("")
                || !Validator.isValidated(id, "^[0-9]*$", true)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }
        //저장
        BoardDTO dto = BoardDTO.builder()
                .title(title)
                .contents(contents)
                .registerId(LoginUtil.getMemberId(request))
                .id(Integer.parseInt(id))
                .build();

        BoardDs ds = new BoardDs();
        boolean isSuccess = ds.updateBoard(dto);
        if (!isSuccess) {
            return AjaxUtill.buildAjaxResult(request, false, "글 수정에 실패하였습니다");
        }

        return AjaxUtill.buildAjaxResult(request, true, "글 수정 하였습니다");

    }
}
