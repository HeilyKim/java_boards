<%@ page import="com.dto.BoardDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BoardDTO dto = (BoardDTO) request.getAttribute("dto");
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
        function update() {
            var title = $('#title');
            var contents = $('#contents');
            if (title.val() == '') {
                alert('제목을 입력해줘라');
                title.focus();
                return;
            }
            if (contents.val() == '') {
                alert('내용을 입력해줘라');
                contents.focus();
                return;
            }
            if (title.val().length > 50) {
                alert('제목은 50자 이내로 입력해줘라');
                title.focus();
                return;
            }
            $.ajax({
                url: "/board/update/process.do"
                , type: "post"
                , async: true
                , dataType: "json"
                , data: {
                    title: title.val()
                    , contents: contents.val()
                    , aid: <%=dto.getId()%>
                }
                , error: function () {
                    alert('서버와 통신하는데 실패하였습니다.');
                }
                , success: function (data) {
                    alert(data.message);
                    if (data.isSuccess == 'true') {
                        gotoDetail();
                    }
                }
            });

            function gotoDetail() {
                location.replace('/board/detail.do?pn=' + getValueOfParameter("pn") +
                    '&searchCategory=' + getValueOfParameter("searchCategory") +
                    '&searchKeyword=' + getValueOfParameter("searchKeyword") +
                    '&sort=' + getValueOfParameter("sort") +
                    '&aid=<%=dto.getId()%>');
            }
        }
    </script>
</head>
<body>
<div>
    제목
    <input type="text" id="title" maxlength="50" value="<%=dto.getTitle()%>">
</div>
<div>
    내용
    <textarea id="contents" cols="30" rows="10"><%=dto.getContents()%></textarea>
</div>
<button onclick="update()">수정</button>
<button onclick="gotoDetail()">취소</button>
</body>
</html>