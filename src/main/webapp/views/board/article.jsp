<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Article</title>
</head>
<body>
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
		<a href="updateForm?arti_no=${arti.arti_no}">[수정]</a>
		<a href="deleteForm?arti_no=${arti.arti_no}">[삭제]</a>
		<a href="board?board_id=${board_id}">[목록]</a></td></tr></table>
</body>

<%-- 댓글 등록,조회,삭제 --%>
  <span id="comment"></span>
  <form action="comment" method="post">
  <input type="hidden" name="arti_no" value="${arti.arti_no}"><%-- 게시물번호 --%>
  <div class="row">
    <div class="col-3 text-center">
     <p>${login.user_no}</p></div>
    <div class="col-7 text-center">
    <p>내용:<input type="text" name="content" class="form-control"></p></div>
    <div class="col-2 text-center">
     <p><button type="submit" class="btn btn-primary">댓글등록</button></p></div>
  </div>
  </form>
  <div class="container">
  <table class="table">
    <c:forEach var="c" items="${commList}">
    <tr><td>${c.seq}</td><td>${c.writer}</td><td>${c.content}</td>
    <td>
	    <fmt:formatDate value="${c.regdate}" pattern="yyyy-MM-dd" var="rdate"/>
	    <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="tdate"/>
	    <c:if test="${rdate == tdate}">
	    	<fmt:formatDate value="${c.regdate}" pattern="HH:mm:ss" />
	    </c:if>
	    <c:if test="${rdate != tdate}">
	    	${rdate}
	    </c:if>
    </td>
    <td align="right">
    <a class="btn btn-danger" href="commDel?num=${param.num}&seq=${c.seq}">삭제</a>
    </td></tr>
    </c:forEach>
  </table></div>
</html>