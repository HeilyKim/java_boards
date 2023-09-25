<%@ page import="com.dto.MemberDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberDTO dto = (MemberDTO) session.getAttribute("mi");
%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <%--<script>--%>
    <%--function searchSubmit() {--%>
    <%--var input = $('#keywordInput').val();--%>
    <%--if (input == ''){--%>
    <%--alert('검색 키워드 입력 하세오');--%>
    <%--return;--%>
    <%--}--%>
    <%--$('#searchForm').submit();--%>
    <%--}--%>
    <%--</script>--%>
</head>
<body>

<%--<form action="/result.do" method="post">--%>
<%--이름--%>
<%--<input type="text" name="myName"> &lt;%&ndash; post: 주소창에 data표시 x &ndash;%&gt;--%>
<%--나이--%>
<%--<input type="text" name="myAge"> &lt;%&ndash;name은 고유 키 값임&ndash;%&gt;--%>
<%--<input type="submit" value="확인">--%>
<%--</form>--%>
<%--&lt;%&ndash;form tag 안에 button 넣을때 type 꼭 지정하기&ndash;%&gt;--%>
<%--<form action="/search.do" method="post" id="searchForm">--%>
<%--검색--%>
<%--<input type="text" name="myNameId" id="keywordInput">--%>
<%--<button type="button" onclick="searchSubmit()">확인</button>--%>
<%--</form>--%>
<%if (dto == null) {%>
<a href="/member/join/form.do">회원가입</a>
<a href="/login.do">로그인</a>
<%} else {%>
<%=dto.getName()%>님 안녕?
<a href="/member/info.do">마이페이지</a>
<a href="/logout.do">로그아웃</a>
<%}%>
<a href="/board/list.do">게시판</a>

</body>
</html>