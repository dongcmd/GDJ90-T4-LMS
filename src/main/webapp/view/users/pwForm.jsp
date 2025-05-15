<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- /webapp/member/pwForm.jsp
1. 비밀번호 찾기 버튼 클릭시
   3개의 값이 모두 입력 되어야만 pw.jsp 페이지를 요청하도록 자바스크립트 추가하기
--%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호재설정</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
	<h3 align="center">비밀번호재설정</h3>
	<form action="updatepw" method="post"onsubmit="return input_check(this)">
 		<table class="table">
     		<tr><th>현재 비밀번호</th><td><input type="password" name="password"  class="form-control"></td></tr>
     		<tr><th>새로운 비밀번호</th><td><input type="password" name="new_password1"  class="form-control"></td></tr>
     		<tr><th>새로운 비밀번호 재입력</th><td><input type="password" name="new_password2"  class="form-control"></td></tr>
     		<tr><td colspan="2" align="center"><input type="submit" value="비밀번호 재설정"  class="btn btn-primary"></td></tr>
  		</table>	
	</form>
</body>
</html>

