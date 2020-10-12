<%@page import="static poly.util.CmmUtil.nvl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="static poly.util.CmmUtil.nvl"%>
<%@page import="poly.dto.NoticeDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    session.setAttribute("SESSION_USER_ID", "USER01");
    List<NoticeDTO> rList = (List<NoticeDTO>)request.getAttribute("rList");
    
    if(rList==null){
    	rList = new ArrayList<NoticeDTO>();
    }
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>공지리스트</title>
<script type="text/javascript">

//상세보기 이동
function doDetail(seq){
	location.href="/Notice/Notice.do?nSeq="+seq;
}

</script>
</head>
<body>
<h2>공지사항</h2>
<br>
<table border="1" width="600px">
<thead>
<tr>
	<th width="100" align="center">글번호</th>
	<th width="100" align="center">제목</th>
	<th width="100" align="center">조회수</th>
	<th width="100" align="center">등록자</th>
	<th width="100" align="center">등록일</th>
</tr>
<tbody>
<%for(NoticeDTO rDTO : rList){ 

if(rDTO == null){
	rDTO = new NoticeDTO();
	}
 %>
<tr>
	<td align="center">
	<%
	//공지글 이라면, [공지]표시
	if(nvl(rDTO.getNotice_yn()).equals("1")){
		out.print("<b>[공지]</b>");
	//공지글이 아니라면 글번호 보여주기
	
	}else{
		out.print(nvl(rDTO.getNotice_seq()));
	}
	%></td>
	
<td align="center">
	<a href="javascript:doDetail('<%=nvl(rDTO.getNotice_seq())%>');">
	<%=nvl(rDTO.getTitle()) %></a>
</td>
<td align="center"><%=nvl(rDTO.getRead_cnt())%></td>
<td align="center"><%=nvl(rDTO.getUser_name())%></td>
<td align="center"><%=nvl(rDTO.getReg_dt())%></td>
</tr>
<%
}
%>
</tbody>
<thead>
</table>
<a href="Notice/NoticeReg.do">[글쓰기]</a>
</body>
</html>