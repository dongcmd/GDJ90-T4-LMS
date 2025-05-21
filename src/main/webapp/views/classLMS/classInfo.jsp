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
		<jsp:include page="classTopTable.jsp" />
		
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