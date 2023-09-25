<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String inputName = (String) request.getAttribute("name");
    String inputAge = (String) request.getAttribute("age");
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<%=inputAge%>살 <%=inputName%>님, 어서오세오.
<button onclick="location.href='/'">홈으로</button>
</body>
</html>
