package com.board.service;

import com.board.ds.BoardDs;
import com.common.*;
import com.dto.BoardDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.common.Constants.BASIC_VIEW_PATH;

public class AjaxBoardWriteProcessService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!LoginUtil.isLogin(request)) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }

        //데이터 검사
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        if (title == null || title.equals("") || title.length() > 50
                || contents == null || contents.equals("")) {
            return AjaxUtill.buildAjaxResult(request, false, "잘못된 접근입니다");
        }

        //특수기호 문자열로 변환
        title = Validator.changeToString(title);
        contents = Validator.changeToString(contents);

        //저장
        BoardDTO dto = BoardDTO.builder()
                .title(title)
                .contents(contents)
                .registerId(LoginUtil.getMemberId(request))
                .build();

        BoardDs ds = new BoardDs();
        boolean isSuccess = ds.insertBoard(dto);
        if (!isSuccess) {
            return AjaxUtill.buildAjaxResult(request, false, "글 등록에 실패하였습니다");
        }

        return AjaxUtill.buildAjaxResult(request, true, "글 등록 하였습니다");

    }
}
