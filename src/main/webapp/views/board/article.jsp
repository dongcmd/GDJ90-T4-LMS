<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${arti.arti_title}</title>
</head>
<body>
<h2>${board_name} 게시판</h2>
<table class="table">
	<tr><th width="20%">글쓴이</th>
			<td width="80%">${arti.user_name}</td></tr>
	<tr><th>제목</th><td>${arti.arti_title}</td></tr>
	<tr><th>내용</th><td><table style="width:100%; height:250px;">
		<tr><td style="boarder-width:0px; vertical-align:top; text-align:left;
		margin:0px; padding:0px">${arti.arti_content}</td></tr></table></td></tr>
	<tr><th>첨부파일</th>
			<td><c:if test="${empty arti.file}">&nbsp;</c:if>
			<c:if test="${!empty arti.file}">
			<a href="../../files/${arti.file}">${arti.file}</a>
			</c:if></td></tr>
	<tr><td colspan="2" align="center">
		<a href="updateForm?arti_no=${arti.arti_no}" class="btn btn-light btn-outline-secondary">수정</a>
		<a href="deleteForm?arti_no=${arti.arti_no}" target="_blank" class="btn btn-outline-danger">삭제</a>
		<a href="board?board_id=${board_id}" class="btn btn-light btn-outline-secondary">목록</a></td></tr></table>
		
<%-- 댓글 등록,조회,삭제 수정필요 강의게시판만 가능--%>
  <span id="comment"></span>
  <div class="container">
  <form action="writeComment" method="post" onsubmit="return chkComm();">
	  <input type="hidden" name="arti_no" value="${arti.arti_no}"><%-- 게시물번호 --%>
	  <table class="table" id="comments">
	    <tr><td>${login.user_name}</td><td><input name="comm_content" class="form-control"></td>
	     <td><button type="submit" class="btn-light btn-outline-secondary">댓글등록</button></td>
	  </table>
  </form></div>
  <div class="container">
  <table class="table">
    <c:forEach var="c" items="${commList}">
	    <tr><td>${c.user_name}</td><td>${c.comm_content}</td>
		    <td><fmt:formatDate value="${c.comm_date}" pattern="yyyy-MM-dd HH:mm" /></td>
		    <c:if test="${c.user_no == login.user_no}">
		    	<td><a class="btn btn-outline-danger" href="commDel?comm_no=${c.comm_no}">삭제</a></td>
	    	</c:if>
	    </tr>
    </c:forEach>
  </table></div>
  
  <script>
  function chkComm() {
  	if(document.querySelector("input[name='comm_content']").value.trim() == "") {
			alert("댓글을 입력하세요");
			return false;
  	}
  	return true;
  }
  </script>
  </body>
</html>