<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- 원동인 --%>
<title>학사 일정 등록</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
	<body>
		<!-- 등록 Form -->
		<h3 class="mb-4 fw_b">학사 일정 등록</h3>
		<form action="event" method="post" name="f" onsubmit="return date_check(this)" >
		  	<table class="table">
			    <tr>
			      <th>제목</th>
			      <td><input type="text" name="event_name" required></td>
			    </tr>
			    <tr>
			      <th>시작일</th>
			      <td><input type="date" name="even_s_date" required></td>
			    </tr>
			    <tr>
			      <th>종료일</th>
			      <td><input type="date" name="even_e_date" required></td>
			    </tr>
		  	</table>
		
		  	<button class="btn btn-dark" type="submit">등록</button>
		</form>
		
		<!-- 이벤트 목록 -->
		<h3 class="mb-4 fw_b">학사 일정 목록</h3>
		<table class="table">
		    <tr>
		        <th>번호</th><th>제목</th><th>시작일</th><th>종료일</th><th>수정</th><th>삭제</th>
		    </tr>
		    <c:forEach var="e" items="${eventList}">
		        <tr>
		            <td>${e.event_no}</td>
		            <td>${e.event_name}</td>
		            <td><fmt:formatDate value="${e.even_s_date}" pattern="yyyy-MM-dd" /></td>
		            <td><fmt:formatDate value="${e.even_e_date}" pattern="yyyy-MM-dd" /></td>
		            <td>
		               <button class="btn btn-dark" type="button" onclick="win_open('event?edit=${e.event_no}')">수정</button>
		            </td>
		            <td><a class="btn btn-dark" href="event?delete=${e.event_no}">삭제</a></td>
		        </tr>
		    </c:forEach>
		</table>
		
		<script type="text/javascript">
		
			function win_open(page){ 
				open(page,"","width=500, height=350, left=50, top=150");
			}
			// 날짜 비교 함수
			function date_check(form) {
			    const start = new Date(form.even_s_date.value);
			    const end = new Date(form.even_e_date.value);

			    if (start > end) {
			        alert("날짜를 다시 입력하세요. (시작일이 종료일보다 늦습니다)");
			        return false; // 전송 막음
			    }
			    return true;
			}
		</script>
	</body>
</html>