<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%-- webapp/view/users/id.jsp --%>
<html>
	<head>
	<meta charset="UTF-8">	

	<title>아이디 찾기</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	</head>
	<body>
		<table class="table">
			<tr>
				<th>아이디는</th>
				<td>
					<p>${user_no}</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="아이디전송" onclick="idsend(`${user_no}`)" class="btn btn-primary" >
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function idsend(user_no){ 
				opener.document.f.user_no.value = user_no; 
				self.close(); // 현재 페이지를 닫기
			}
		</script>
	</body>
</html>
    