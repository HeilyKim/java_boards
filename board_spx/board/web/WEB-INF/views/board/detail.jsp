<%@ page import="com.dto.BoardDTO" %>
<%@ page import="com.dto.MemberDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<%
    BoardDTO dto = (BoardDTO) request.getAttribute("dto");
    MemberDTO memberDTO = (MemberDTO) session.getAttribute("mi");
%>
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
        function updateArticle() {
            location.href = '/board/update.do?pn=' + getValueOfParameter("pn")
                + '&searchCategory=' + getValueOfParameter("searchCategory")
                + '&searchKeyword=' + getValueOfParameter("searchKeyword")
                + '&sort=' + getValueOfParameter("sort")
                + '&aid=' + getValueOfParameter("aid");
        }

        function deleteArticle() {
            if (!confirm("삭제된 글은 복구할 수 없습니다. \n정말 삭제하시겠습니까?")) {
                return;
            }
            $.ajax({
                url: "/board/delete.do"
                , type: "post"
                , async: true
                , dataType: "json"
                , data: {
                    id:<%=dto.getId()%>
                }
                , error: function () {
                    alert('서버와 통신하는데 실패하였습니다.');
                }
                , success: function (data) {
                    alert(data.message);
                    if (data.isSuccess == 'true') {
                        location.replace('/board/list.do?pn=' + getValueOfParameter("pn")
                            + '&searchCategory=' + getValueOfParameter("searchCategory")
                            + '&searchKeyword=' + getValueOfParameter("searchKeyword")
                            + '&sort=' + getValueOfParameter("sort"));
                    }
                }
            });
        }

        function gotoList() {
            location.href = '/board/list.do?pn=' + getValueOfParameter("pn")
                + '&searchCategory=' + getValueOfParameter("searchCategory")
                + '&searchKeyword=' + getValueOfParameter("searchKeyword")
                + '&sort=' + getValueOfParameter("sort");

        }
    </script>
    <style>
        .border_div {
            border: 1px cornflowerblue solid;
        }
    </style>
</head>
<body>
<div>
    제목
    <div class="border_div">
        <%=dto.getTitle()%>
    </div>
</div>
<div>
    내용
    <div class="border_div">
        <%=dto.getContents()%>
    </div>
</div>
<div>
    작성자
    <div class="border_div">
        <%=dto.getRegisterLoginId()%>
    </div>
</div>
<div>
    작성일시
    <div class="border_div">
        <%=dto.getRegisterDatetime()%>
    </div>
</div>
<div>
    조회수
    <div class="border_div">
        <%=dto.getHits()%>
    </div>
</div>
<div>
    <%if (memberDTO != null && memberDTO.getId() == dto.getRegisterId()) {%>
    <button onclick="updateArticle()">수정</button>
    <button onclick="deleteArticle()">삭제</button>
    <%}%>
    <button onclick="gotoList()">목록으로</button>
</div>

</body>
</html>