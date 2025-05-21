<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 원동인 --%>
<%--이동원
이전 파일명
manageassignment
 --%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 관리</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<h3 class="my-4">컴퓨터 공학과 과제관리</h3>
        <input type="hidden" name="class_no" value="${class_no}">
        <input type="hidden" name="ban" value="${ban}">
        <input type="hidden" name="year" value="${year}">
        <input type="hidden" name="term" value="${term}">
        		
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
	
		<h3 class="my-4">과제 목록
			<a href="addAssignmentForm" class="btn btn-dark" role="button">과제추가</a>
			</h3>
	
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">과제명</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">과제내용</th>
					<th style="text-align: center">배점</th>
					<th style="text-align: center">제출현황</th>
					<th style="text-align: center">수정/삭제</th>
				</tr>
			</thead>
			

			<c:forEach var="as" items="${asList}">
			<tbody>
				<tr>
					<td style="text-align: center"><a href="manageAs?as_no=${as.as_no}">${as.as_name}</a></td>
					<td><fmt:formatDate value="${as.as_s_date}" pattern="yyyy/MM/dd HH:mm" /><br>
						~<fmt:formatDate value="${as.as_e_date}" pattern="yyyy/MM/dd HH:mm" /></td>
					<td style="text-align: center">${as.as_content}</td>
					<td style="text-align: center">${as.as_point}</td>
					<td style="text-align: center">${as.submittedCount} / ${class1.max_p}</td>
					<td class="d-flex justify-content-center">
						<a href="updateAssignmentForm?as_no=${as.as_no}" class="btn btn-dark" role="button" style="align-items: center">과제수정</a>
						<a href="deleteAssignmentForm?as_no=${as.as_no}" class="btn btn-danger" role="button" style="align-items: center">과제삭제</a>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
		
		<div class="m-5 d-flex justify-content-end" style="gap: 10px;">
			<a href="#" class="btn btn-dark" role="button">과제 다운로드</a>
		</div>


	<c:if test="${!empty selectedAs_no}">
		<a href="download_asXLSX?as_no=${selectedAs_no}" class="btn btn-dark" role="button">
		양식 다운로드 xlsx</a>
	  <form action="upload_asCSV" method="post" enctype="multipart/form-data">
	  	<input type="hidden" name="as_no" value="${selectedAs_no}">
	  	<input type="file" name="file" accept=".csv" />
	  	<button type="submit">${selectedAs_no} | csv 업로드</button>
		</form>
	</c:if>


		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">학번</th>
					<th style="text-align: center">학년</th>
					<th style="text-align: center">학생명</th>
					<th style="text-align: center">제출현황</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="subAs" items="${subAsList}">
					<tr>
						<td style="text-align: center">${subAs.user_no}</td>
						<td style="text-align: center">${subAs.user_grade}</td>
						<td style="text-align: center">${subAs.user_name}</td>
						<td style="text-align: center">
							<c:if test="${!empty subAs.file}">제출완료</c:if>
							<c:if test="${empty subAs.file or subAs.file.trim() == ''}">미제출</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>