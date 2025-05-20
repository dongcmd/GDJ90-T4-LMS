<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학점 관리</title>
</head>
<body>
	<h2>학점 관리</h2>
	<table class="table table-bordered">
		<thead class="thead-light">
			<tr>
				<th>번호</th>
				<th>학번</th>
				<th>학년</th>
				<th>이름</th>
				<th>과제총점(20)</th>
				<th>중간고사(30)</th>
				<th>기말고사(40)</th>
				<th>출석(10)</th>
				<th>총점(100)</th>
				<th>학점</th>
			</tr>
		</thead>

		<c:set var="i" value="1" />
		<c:forEach var="ru" items="${reg_users}" varStatus="stat">
			<tr>
				<td>${stat.index + 1}</td>
				<td>${ru.user_no}</td>
				<td>${ru.USER_GRADE}</td>
				<td>${ru.user_name}</td>
				<td>${ru.as_tot_score}</td>
				<td>${ru.exam1_score}</td>
				<td>${ru.exam2_score}</td>
				<td>${ru.att_score}</td>
				<c:set var="tot_score" value="${ru.as_tot_score + ru.exam1_score + ru.exam2_score + ru.att_score}" />
				<td>${tot_score}</td>
 				<td><c:choose>
					<c:when test="${tot_score >= 90}">A<</c:when>
					<c:when test="${tot_score >= 80}">B</c:when>
					<c:when test="${tot_score >= 70}">C</c:when>
					<c:when test="${tot_score >= 60}">D</c:when>
					<c:otherwise>F</c:otherwise>
				</c:choose></td>
			</tr>
		</c:forEach>
	</table>
	<div style="display: flex; justify-content: flex-end; gap: 10px;">
		<form action="downloadXlsx" method="get">
			<button class="btn btn-dark">양식 다운로드</button>
		</form>
		<form action="uploadCsv" method="post" enctype="multipart/form-data">
			<input type="file" name="file" accept=".csv">
			<button class="btn btn-light btn-outline-secondary">csv 업로드</button>
		</form>
	</div>
</body>
</html>