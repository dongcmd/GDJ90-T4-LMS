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
<%!private static String[] s_period = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
			"17:00" };
	private static String[] e_period = { "09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50",
			"17:50" };%>
<body>
		<h2>나의 강의</h2>
		<table class="table table-bordered">
				<thead class="thead-light" style="text-align: center;">
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
				<c:forEach var="cls" items="${classesList}" varStatus="stat">
						<tr>
								<td class="text-center">${stat.index + 1}</td>
								<td class="text-center">${cls.class_no}</td>
								<td>${cls.class_name}</td>
								<td>${login.user_name}</td>
								<td class="text-center">${cls.class_grade}</td>
								<td class="text-center">${cls.credit}</td>
								<td><c:forEach var="d" items="${cls.days}">
												<c:choose>
														<c:when test="${d == 0}">월 </c:when>
														<c:when test="${d == 1}">화 </c:when>
														<c:when test="${d == 2}">수 </c:when>
														<c:when test="${d == 3}">목 </c:when>
														<c:when test="${d == 4}">금 </c:when>
												</c:choose>
										</c:forEach> <br> <%=s_period[((models.classes.Class1) pageContext.getAttribute("cls")).getS_time() - 1]%> ~ <%=e_period[((models.classes.Class1) pageContext.getAttribute("cls")).getE_time() - 1]%>

								</td>
								<td class="text-center">${cls.classroom}</td>
								<td class="text-center"><a
										href="updateClass?no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-dark">수정</a>
										<a href="deleteClass?no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-dark"
										onclick="return confirm('정말 이 강의를 삭제하시겠습니까?');">삭제</a></td>
						</tr>
				</c:forEach>
		</table>
		<button type="button" class="btn btn-light btn-outline-secondary" onclick="location.href='addClass'"
				style="position: fixed; right: 24px; z-index: 1000;">강의 추가</button>
</body>
</html>