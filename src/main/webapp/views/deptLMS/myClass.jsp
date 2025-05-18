<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- myClass 강의 관리 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 관리</title>
</head>
<body>
		<h2>강의 관리</h2>
		<table class="table table-bordered">
				<thead class="thead-light">
						<tr>
								<th style="width: 5%;">No</th>
								<th style="width: 10%;">강의코드</th>
								<th style="width: 15%;">강의명</th>
								<th style="width: 10%;">교수명</th>
								<th style="width: 5%;">학년</th>
								<th style="width: 8%;">이수학점</th>
								<th style="width: 15%;">강의시간</th>
								<th style="width: 10%;">강의실</th>
								<th style="width: 8%;">수정/삭제</th>
						</tr>
				</thead>
				<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>
				<c:forEach var="i" begin="1" end="5">
						<tr>
								<td style="text-align: center">1</td>
								<td>101</td>
								<td>JSP 프로그래밍</td>
								<td style="text-align: center">홍길동</td>
								<td style="text-align: center">1</td>
								<td style="text-align: center">3</td>
								<td style="text-align: center">월<br>09:00~11:00
								</td>
								<td style="text-align: center">A관 101호</td>
								<td style="text-align: center">수정/삭제</td>
						</tr>
				</c:forEach>
		</table>
</body>
</html>