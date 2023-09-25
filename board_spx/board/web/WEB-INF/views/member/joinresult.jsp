<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String name = (String) request.getAttribute("name");
%>
<%=name%>님! 환영합니다!
<button onclick="location,href='/'">홈으로</button>
<button onclick="location,href='/login.do'">로그인하기</button>