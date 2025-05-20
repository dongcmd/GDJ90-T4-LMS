<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 원동인 --%>
<%-- 이동원
기존 파일명
assignmentList
 --%>
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
		<h3 class="my-4">과제 제출</h3>
	
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">no</th>
					<th style="text-align: center">과제목록</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">제출 여부</th>
					<th style="text-align: center">제출한 과제 다운로드</th>
				</tr>
			</thead>
			
			<c:forEach var="aslist" items="${asList}">
			<tbody>
				<tr>
					<td style="text-align: center">${aslist.as_no}</td>
					<td><a href="submitassignment?as_no=${aslist.as_no}" style="text-align: center">${aslist.as_name}</a></td>
					<td> ~ <fmt:formatDate value="${aslist.as_e_date}" pattern="yyyy/MM/dd HH:mm" /> </td>
					<td style="text-align: center">
						제출/미제출
					</td>
					<td>
						<div class="d-flex justify-content-center">
							<a href="../files/${as.file}" class="btn btn-dark" role="button" style="text-align: center" download>다운로드</a>
						</div>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
	</body>
</html>