<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- 원동인 --%>
<title>학사 일정 등록</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
	<body>
		<form action="evnet" method="post" name="f" onsubmit="return date_check(this)" >
		  	<table class="table">
			    <tr>
			      <th>제목</th>
			      <td><input type="text" name="event_name" required></td>
			    </tr>
			    <tr>
			      <th>시작일</th>
			      <td><input type="datetime-local" name="even_s_date" required></td>
			    </tr>
			    <tr>
			      <th>종료일</th>
			      <td><input type="datetime-local" name="even_e_date" required></td>
			    </tr>
		  	</table>
		
		  	<button type="submit">등록</button>
		</form>
		
		
		<script type="text/javascript">
			function date_check(f){
				if(f.id.value.trim() == ""){
					alert("아이디를 입력하세요");
					f.id.focus();
					return false; // 기존 이벤트 취소
				}
				if(f.pass.value.trim() == ""){
					alert("비밀번호를 입력하세요");
					f.pass.focus();
					return false; 
				}
				return true;
			}
		</script>
	</body>
</html>