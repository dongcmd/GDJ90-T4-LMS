<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>강의 계획서</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
       <table class="table table-bordered">
       		<tr>
       			<th style="background-color: #eee;">과목코드</th>
       			<td>${class_no}</td>
       			<th style="background-color: #eee;">과목명</th>
       			<td>${class_name}</td>
       			<th style="background-color: #eee;">강의 시간</th>
       			<td>${s_time} ~ ${e_time}교시</td>
       			<th style="background-color: #eee;">담당교수</th>
       			<td></td>
       		</tr>
       		
       		<tr>
       			<th style="background-color: #eee;">학과</th>
       			<td>${major_no}</td>
       			<th style="background-color: #eee;">학년</th>
       			<td>${class_grade}</td>
       			<th style="background-color: #eee;">구분</th>
       			<td></td>
       			<th style="background-color: #eee;">강의실</th>
       			<td>${classroom}</td>
       		</tr>
		</table>
		
		<h3 class="mb-4">강의 계획서</h3>
		<table class="table table-bordered">
       		<tr>
       			<th style="background-color: #eee;">파일</th>
       			<td>${file}</td>
       		</tr>
       	</table>
       	
   		<input type="text" name="file" value="${file}">
		<a class="btn btn-dark" href="${pageContext.request.contextPath}/download?id=${file.id}">
			다운로드
		</a>
	</body>
</html>