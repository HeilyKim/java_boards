<%@ page import="com.dto.MemberDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dto.BoardDTO" %>
<%@ page import="com.common.MyPagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberDTO dto = (MemberDTO) session.getAttribute("mi");
    List<BoardDTO> list = (List<BoardDTO>) request.getAttribute("list");
    MyPagination mp = (MyPagination) request.getAttribute("mp");
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <script src="../../../js/common.js"></script>
    <title>Document</title>
    <script>
        var searchCategory = getValueOfParameter("searchCategory");
        if (searchCategory == "") {
            searchCategory = "title";
        }
        var searchKeyword = getValueOfParameter("searchKeyword");
        var selectedSort = getValueOfParameter("sort");
        if (selectedSort == "") {
            selectedSort = "recent";
        }

        function sortList() {
            var selectedValue = $('#sort option:selected').val();
            location.href = '/board/list.do?pn=<%=mp.getNowPage()%>'
                + '&searchCategory=' + searchCategory
                + '&searchKeyword=' + searchKeyword
                + '&sort=' + selectedValue;
        }

        $(document).ready(function () {
            $('#searchCategory').val(searchCategory).prop("selected", true);
            $('#searchKeyword').val(searchKeyword);
            $('#sort').val(selectedSort).prop("selected", true);
            $('.article_row').click(function () {
                var id = $(this).data('id'); //이벤트 발생시킨걸 가져옴 data('id') => data-id
                gotoDetailPage(id);
            })
        })

        function gotoPage(pn) {
            var sort = $('#sort option:selected').val();
            location.href = '/board/list.do?pn=' + pn +
                '&searchCategory=' + searchCategory +
                '&searchKeyword=' + searchKeyword +
                '&sort=' + sort;
        }

        function gotoDetailPage(id) {
            var sort = $('#sort option:selected').val();
            location.href = '/board/detail.do?pn=<%=mp.getNowPage()%>' +
                '&searchCategory=' + searchCategory +
                '&searchKeyword=' + searchKeyword +
                '&sort=' + sort + '&aid=' + id;
        }
    </script>
</head>
<body>
<form action="/board/list.do">
    <div>
        <select name="searchCategory" id="searchCategory">
            <option value="title" selected>제목</option>
            <option value="contents">내용</option>
            <option value="register">글쓴이</option>
        </select>
        <input type="text" name="searchKeyword" id="searchKeyword">
        <input type="submit" value="검색">
    </div>
</form>
<div>
    <select id="sort" onchange="sortList()">
        <option value="recent" selected>최신순</option>
        <option value="old">오래된순</option>
        <option value="high">조회순▲</option>
        <option value="low">조회순▼</option>
    </select>
</div>
<table>
    <tr>
        <td>제목</td>
        <td>조회수</td>
        <td>작성자</td>
        <td>직성일시</td>
    </tr>
    <%if (list.size() > 0) {%>
    <%for (int i = 0; i < list.size(); i++) {%>
    <tr class="article_row" data-id="<%=list.get(i).getId()%>">
        <td><%=list.get(i).getTitle()%>
        </td>
        <td><%=list.get(i).getHits()%>
        </td>
        <td><%=list.get(i).getRegisterLoginId()%>
        </td>
        <td><%=list.get(i).getRegisterDatetime()%>
        </td>
    </tr>
    <%}%>
    <%} else {%>
    <tr>
        <td colspan="4">게시글이 존해하지 않습니다.</td> <!--위에 td 합치기-->
    </tr>
    <%}%>
</table>
<div>
    <a href="javascript: gotoPage(1)">처음</a>
    <a href="javascript: gotoPage(<%=mp.getPrePage()%>)">이전</a>
    <%for (int i = mp.getStartPage(); i <= mp.getEndPage(); i++) {%>
    <a href="javascript: gotoPage(<%=i%>)"><%=i%>
    </a>
    <%}%>
    <a href="javascript: gotoPage(<%=mp.getNextPage()%>)">다음</a>
    <a href="javascript: gotoPage(<%=mp.getTotalPageCount()%>)">마지막</a>
</div>

<%if (dto != null) {%> <!--login 상태일때-->
<button onclick="location.href='/board/write.do'">글쓰기</button>
<%}%>
</body>
</html>
