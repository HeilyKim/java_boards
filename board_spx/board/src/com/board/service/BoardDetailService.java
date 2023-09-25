package com.board.service;

import com.board.ds.BoardDs;
import com.common.AppService;
import com.common.BCrypt;
import com.common.LoginUtil;
import com.common.ServiceForward;
import com.dto.BoardDTO;
import com.dto.MemberDTO;
import com.member.ds.MemberDs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static com.common.Constants.BASIC_VIEW_PATH;
import static com.common.Validator.isStringEmpty;
import static com.common.Validator.isValidated;

public class BoardDetailService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("aid");
        //데이터 확인
        if (!isValidated(id, "^[0-9]*$", true)) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }

        //db에서 id로 글 검색
        BoardDs ds = new BoardDs();
        BoardDTO dto = ds.selectBoardDetailById(Integer.parseInt(id));
        if (dto == null) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('존재하지 않는 글 입니다'); history.back();")
                    .build();
        }
        // 조회수 올리기: update board set hit = hit+1 where id = ?

        //뷰 이동 데이터 세팅 및 리턴
        request.setAttribute("dto",dto);

        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "board/detail.jsp")
                .build();
    }
}
