<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 오예록 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 제출</title>
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
		
		<h3 class="my-4">과제 제출</h3>
		
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th>No</th>
					<th>과제 제목</th>
					<th>기한</th>
					<th>제출 여부</th>
					<th>업로드</th>
					<th>다운로드</th>
				</tr>
			</thead>
			
		<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>
		<c:forEach var="i" begin="1" end="5">
			<tr>
				<td style="text-align: center">1</td>
				<td>형변환 문제</td>
				<td style="text-align: center">~2025/3/10 23:59:59</td>
				<td style="text-align: center">제출</td>
				<td>
					<button type="button" class="btn btn-light btn-outline-secondary">업로드</button>
				</td>
				<td>
					<button type="button" class="btn btn-dark">다운로드</button>
				</td>
			</tr>
		</c:forEach>
	</table>
	</body>
</html>