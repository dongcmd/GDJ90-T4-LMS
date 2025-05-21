<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>님 학점조회</title>
</head>
<body>
	<h2>님 학점조회</h2>
	<%-- 
<c:if test="${regList == null}"><h3 align="center">아직 새내기군요!</h3></c:if>
<c:if test="${regList != null}">
 --%>
	<form action="board" method="post" name="searchForm">
		<select name="column">
			<option value="mark">학점</option>
			<option value="year-term">수강학기</option>
			<option value="class_no">강의코드</option>
			<option value="class_name">강의명</option>
			<option value="prof">교수</option>
		</select>
		<c:if test="${not empty param.column}">
			<script>
				document.searchForm.column.value = '${param.column}'
			</script>
		</c:if>
		<input placeholder="검색어를 입력하세요." name="keyword" value="${param.keyword}">
		<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
	</form>

	<table class="table table-bordered">
		<thead class="thead-light">
			<tr>
				<th>번호</th>
				<th>수강학기</th>
				<th>강의코드-반</th>
				<th>강의명</th>
				<th>교수</th>
				<th>이수학점</th>
				<th>학점</th>
			</tr>
			<c:set var="i" value="1" />
			<c:forEach var="regc" items="${regList}">
				<tr>
					<td>${i}</td>
					<c:set var="i" value="${i+1}" />
					<td>${regc.year}-${regc.term}</td>
					<td>${regc.class_no}-${regc.ban}</td>
					<td>${regc.class_name}</td>
					<td>${regc.prof}</td>
					<td></td>
					<td>${regc.mark}</td>
				</tr>
			</c:forEach>
		</thead>
	</table>
	<%-- 
</c:if>
 --%>
</body>
</html>