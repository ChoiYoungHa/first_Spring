<%@page import="poly.util.CmmUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
//Controller로부터 전달받은 데이터

String res = CmmUtil.nvl((String)request.getAttribute("res"),"0");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
강서폴리텍 학식 홈페이지에서 <%=res %>개의 학식 정보가 수집되었습니다.
</body>
</html>