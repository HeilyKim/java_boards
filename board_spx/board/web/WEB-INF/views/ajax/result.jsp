<%@ page import="com.dto.AjaxResultDTO"%><%@ page contentType="text/plain;charset=UTF-8" language="java" %>
<%-- plain: 별도의 데이터셋을 받는 페이지--%>
<%
   AjaxResultDTO dto = (AjaxResultDTO)request.getAttribute("result");
%>
{
    "isSuccess" : "<%=dto.isSuccess()%>"
    ,"message" : "<%=dto.getMessage()%>"
}
