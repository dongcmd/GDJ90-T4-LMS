<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<c:forEach var="classInfo" items="${classesList}">
			<div class="d-flex mb-4" style="gap:10px">
				<table class="table table-bordered col-sm-4 mb-0">
		       		<tr>
		       			<th style="background-color: #eee;">파일</th>
		       			<td>${classInfo.file}_강의계획서.pdf</td>
		       		</tr>
	       		</table>
				<a class="btn btn-dark d-flex align-items-center" href="../files/${classInfo.file}_강의계획서.pdf" download>다운로드</a>
			</div>
			<textarea class="form-control" style="resize: none;" rows="20" readonly>${classInfo.c_plan}</textarea>
		</c:forEach>
	</body>
</html>