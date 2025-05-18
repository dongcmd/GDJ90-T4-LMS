<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사일정수정</title>
</head>
	<body>
	    <h3 class="mb-4 fw_b">학사 일정 수정</h3>
	
	    <form action="event" method="post" name="f" onsubmit="return date_check(this)">
	        <input type="hidden" name="event_no" value="${event.event_no}" />
	
	        <table class="table">
	            <tr>
	                <th>제목</th>
	                <td><input type="text" name="event_name" value="${event.event_name}" required></td>
	            </tr>
	            <tr>
	                <th>시작일</th>
	                <td>
	                    <input type="date" name="even_s_date"
	                        value="<fmt:formatDate value='${event.even_s_date}' pattern='yyyy-MM-dd' />" required>
	                </td>
	            </tr>
	            <tr>
	                <th>종료일</th>
	                <td>
	                    <input type="date" name="even_e_date"
	                        value="<fmt:formatDate value='${event.even_e_date}' pattern='yyyy-MM-dd' />" required>
	                </td>
	            </tr>
	        </table>
	
	        <button type="submit" class="btn btn-dark">수정 완료</button>
	    </form>
	
	    <script>
	        // 날짜 비교 함수
	        function date_check(form) {
	            const start = new Date(form.even_s_date.value);
	            const end = new Date(form.even_e_date.value);
	
	            if (start > end) {
	                alert("시작일이 종료일보다 늦을 수 없습니다.");
	                return false;
	            }
	            return true;
	        }
	    </script>
	</body>
</html>