package com.board.service;

import com.board.ds.BoardDs;
import com.common.AppService;
import com.common.MyPagination;
import com.common.ServiceForward;
import com.common.Validator;
import com.dto.BoardDTO;
import com.dto.SearchDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.common.Constants.BASIC_VIEW_PATH;

public class BoardListService implements AppService {
    @Override
    public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //page number=>pn
        String pn = request.getParameter("pn");
        if (Validator.isStringEmpty(pn)) {
            pn = "1";   //지정된 페이지번호가 없으면 강제로 1페이지를 띄움
        }
        if (!Validator.isValidated(pn, "^[0-9]*$", true)) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }

        int pageNumber = Integer.parseInt(pn); //int으로 바꾸기
        if (pageNumber == 0) {  //0페이지 없음
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); history.back();")
                    .build();
        }


        //데이터(페이지, 키워드, 카테고리, 정렬)
        String searchCategory = request.getParameter("searchCategory");
        String searchKeyword = request.getParameter("searchKeyword");
        String sort = request.getParameter("sort");
        String extraQuery = makeExtraQuery(searchCategory, searchKeyword, sort);
        if (extraQuery == null) {
            return ServiceForward.builder()
                    .fail(true)
                    .message("alert('잘못된 접근입니다'); location.href = '/board/list.do';")
                    .build();
        }

        BoardDs ds = new BoardDs();

        //검색 적용하여 db에서 글의 총 개수 검색
        int totalArticleCount = ds.selectTotalArticleCount(extraQuery);
        MyPagination mp = new MyPagination();
        mp.setPaginationInfo(pageNumber, totalArticleCount);
        List<BoardDTO> list = ds.selectBoardList(mp, extraQuery);

        request.setAttribute("mp",mp); //front에서 쓰려고

        //리스트 만들기
        request.setAttribute("list", list);
//        request.setAttribute("searchInfo", SearchDTO.builder()
//                .searchCategory(searchCategory)
//                .searchKeyword(searchKeyword)
//                .sort(sort)
//                .build());

        return ServiceForward.builder()
                .path(BASIC_VIEW_PATH + "board/list.jsp")
                .build();
    }

    private String makeExtraQuery(String searchCategory, String searchKeyword, String sort) {
        String extraQuery = "";
        if (searchKeyword != null && !searchKeyword.equals("")) {
            switch (searchCategory) {
                case "title":
                    extraQuery = " and a.title like '%" + searchKeyword + "%'";
                    break;
                case "contents":
                    extraQuery = " and a.contents like '%" + searchKeyword + "%'";
                    break;
                case "register":
                    extraQuery = " and b.login_id like '%" + searchKeyword + "%'";
                    break;
                default:
                    return null;
            }
        }
        if (sort == null) {
            sort = "recent";
        }
        switch (sort) {
            case "recent":
                extraQuery += " order by a.id desc";
                break;
            case "old":
                extraQuery += " order by a.id asc";
                break;
            case "high":
                extraQuery += " order by a.hits desc";
                break;
            case "low":
                extraQuery += " order by a.hits asc";
                break;
            default:
                return null;
        }

        return extraQuery;
    }
}