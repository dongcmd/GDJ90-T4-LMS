<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- signUpClass 수강신청 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--
 강의 이름 변경 text input 값 이름 변경
 값들 수정
 강의 시간 쪽에서 요일을 여러개 출력, 시간도 
 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수강 신청</title>
</head>
<body>
		<h2>수강 신청</h2>
		<form action="signUpClass" method="get" style="display: flex; gap: 8px; align-items: center;">
				<select name="column" class="form-control" style="width: 150px">
						<option value="강의이름,userName">강의명+교수명</option>
						<option value="강의이름">강의명</option>
						<option value="userName">교수명</option>
				</select>
				<input type="text" name="fine" placeholder="Search" class="form-control" style="width: 230px;">
				<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
		</form>
		<table class="table table-bordered">
				<thead class="thead-light" style="text-align: center">
						<tr>
								<th style="width: 5%;">No</th>
								<th style="width: 10%;">강의코드</th>
								<th style="width: 15%;">강의명</th>
								<th style="width: 10%;">교수명</th>
								<th style="width: 5%;">학년</th>
								<th style="width: 8%;">이수학점</th>
								<th style="width: 15%;">강의시간</th>
								<th style="width: 10%;">강의실</th>
								<th style="width: 8%;">수강인원</th>
								<th style="width: 10%;">신청</th>
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
								<td style="text-align: center">11/20</td>
								<td style="text-align: center"><button type="submit" class="btn btn-dark">신청</button></td>
						</tr>
				</c:forEach>
		</table>
</body>
</html>