<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 원동인 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 관리</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<h3 class="my-4">컴퓨터 공학과 과제관리</h3>
		<table class="table table-bordered">
       		<tr>
       			<th style="background-color: #eee;">과목코드</th>
       			<td>123456</td>
       			<th style="background-color: #eee;">과목명</th>
       			<td>자바스크립트</td>
       			<th style="background-color: #eee;">강의 시간</th>
       			<td>월 09:00 ~ 12:00</td>
       			<th style="background-color: #eee;">담당교수</th>
       			<td>홍길동</td>
       		</tr>
       		
       		<tr>
       			<th style="background-color: #eee;">학과</th>
       			<td>컴퓨터공학과</td>
       			<th style="background-color: #eee;">학년</th>
       			<td>1</td>
       			<th style="background-color: #eee;">구분</th>
       			<td>전공</td>
       			<th style="background-color: #eee;">강의실</th>
       			<td>101</td>
       		</tr>
		</table>
	
		<h3 class="my-4">과제 목록</h3>
	
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">과제명</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">과제내용</th>
					<th style="text-align: center">제출현황</th>
					<th style="text-align: center">수정/삭제</th>
				</tr>
			</thead>
			
			<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>
			<tbody>
				<tr>
					<td style="text-align: center">형변환</td>
					<td>2025-03-01 ~ 2025-05-10</td>
					<td style="text-align: center">형변환 연습문제 1~10번까지 풀기</td>
					<td style="text-align: center">3 / 10</td>
					<td><a href="updateAssignmentForm" class="btn btn-dark" role="button" style="align-items: center">과제수정
						<a href="deleteAssignmentForm" class="btn btn-danger" role="button" style="align-items: center">과제삭제</a>
				</tr>
			</tbody>
		</table>
		
		<div class="m-5">
			<a href="addAssignmentForm" class="btn btn-dark" role="button">과제추가</a>
			<a href="#" class="btn btn-dark" role="button">과제 다운로드</a>
			<a href="#" class="btn btn-dark" role="button">파일2 다운로드 csv</a>
			<a href="#" class="btn btn-dark" role="button">파일업로드 csv</a>
		</div>

		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">학번</th>
					<th style="text-align: center">학년</th>
					<th style="text-align: center">학생명</th>
					<th style="text-align: center">제출현황</th>
				</tr>
			</thead>
			
			<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>
			<tbody>
				<tr>
					<td style="text-align: center">111</td>
					<td style="text-align: center">1</td>
					<td style="text-align: center">홍길동</td>
					<td style="text-align: center">x</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>