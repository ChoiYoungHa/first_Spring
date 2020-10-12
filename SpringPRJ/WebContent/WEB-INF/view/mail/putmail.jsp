<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 전송페이지</title>
</head>
<body>
<form action="/mail/sendMail.do" method="post">
<table border="1">
<tr>
	<td>받는사람</td>
	<td><input type="text" name="toMail" style="width:500px;" required></td>
</tr>
<tr>
	<td>메일제목</td>
	<td><input type="text" name="title" style="width:500px;" required></td>
</tr>
<tr>
	<td>메일내용</td>
	<td><input type="text" name="contents" style="width:500px; height:300px;" required></td>
</tr>
</table>
<br>
<input type="submit" value="메일전송">
</form>
</body>
</html>