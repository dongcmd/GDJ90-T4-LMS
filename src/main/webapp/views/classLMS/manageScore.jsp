<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학점 관리</title>
</head>
<body>
<%--
	<tr>
		<th>수강학기</th><td>${class1.year}-${class1.term}</td>
		<th>강의코드</th><td>${class1.class_no}-${ban}</td>
		<th>강의명</th><td>${class1.class_name}</td>
		<th>학년</th><td>${class1.class_grade}</td>
	</tr>
	<tr>
		<th>교수</th><td>${class1.prof}</td>
		<th>이수학점</th><td>${class1.credit}</td>
		<th>강의시간</th><td>요일 및 교시(시간)수정필요</td>
		<th>강의실</th><td>${class1.classroom}</td>
	</tr>
	 --%>
<h2>학점 관리</h2>
<table class="table table-bordered"><thead class="thead-light">
	<tr><th>번호</th><th>학번</th><th>학년</th>
			<th>이름</th><th>과제총점(20)</th><th>중간고사(30)</th><th>기말고사(40)</th>
			<th>출석(10)</th><th>총점(100)</th><th>학점</th>
	</tr></thead><%--
	<c:set var="i" value="1" />
	<c:forEach var="ru" items="${reg_users}">
	<tr><td>${i}</td><c:set var="i" value="${i+1}" /><td>${ru.user_no}</td><td>${ru.user_grade}</td>
			<td>${ru.user_name}</td><td>${ru.as_tot_score}</td><td>${ru.exam1_score}</td><td>${ru.exam2_score}</td>
			<td>${ru.att_score}</td><td>${ru.tot_score}</td><td>${ru.mark}</td>
	</tr>
	</c:forEach> --%>
</table>
<div style="display : flex; justify-content : flex-end; gap : 10px;">
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