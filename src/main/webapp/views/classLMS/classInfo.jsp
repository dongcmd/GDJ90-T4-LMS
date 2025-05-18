<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 원동인 --%>
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
		
		<h3 class="my-4">강의 계획서</h3>
		<div class="d-flex mb-4" style="gap:10px">
			<table class="table table-bordered col-sm-4 mb-0">
	       		<tr>
	       			<th style="background-color: #eee;">파일</th>
	       			<td>중간고사.pdf</td>
	       		</tr>
       		</table>
			<button type="button" class="btn btn-dark">다운로드</button>
		</div>
		<textarea class="form-control" style=" resize: none; " rows="20">형 변환 문제를 통한 자료형의 이해, 출력 알고림즘 문제를 이용하여 공부합니다.</textarea>
	</body>
</html>