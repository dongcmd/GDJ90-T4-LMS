<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- board (공통)게시판 이동원 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
</head>
<body>
<h2>${boardname} 게시판</h2>
	<c:if test="${boardcount == 0}">
		등록된 게시글이 없습니다.
	</c:if>
	<c:if test="${boardcount > 0}">
		<form action="board" method="post" name="sf">
			<input type="hidden" name="pageNum" value="1">
			<input type="hidden" name="boardid" value="${param.boardid}">
			<select name="column">
				<option value="title">제목</option>
				<option value="writer">작성자</option>
				<option value="content">내용</option>
			</select>
			<c:if test="${not empty param.column}">
				<script>document.sf.column.value='${param.column}'</script>
			</c:if>
			<input placeholder="검색어를 입력하세요." name="keyword" value="${param.keyword}">
			<button type="submit">검색</button>
		</form>
		<table class="table" border="1"><tr><th width="8%">번호</th>
			<th width="53%">제목</th><th width="8%">첨부파일</th>
			<th width="14%">작성자</th><th width="17%">등록일</th>
				<c:forEach var="arti" items="${artiList}">
					<tr><td>${artiIndex}</td><c:set var="artiIndex" value="${artiIndex-1}" />
							<td><a href="article?num=${arti.arti_no}">${arti.title}</a></td>
							<td><c:if test="${not empty arti.file}">O</c:if></td>
							<td>${arti.writer}</td>
							<td><fmt:formatDate value="${arti.arti_date}" pattern="yyyy-MM-dd" /></td>							
					</tr>
				</c:forEach>
		</tr></table>
	</c:if>
</body>
</html>