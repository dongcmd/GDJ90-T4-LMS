<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- webapp/view/users/loginForm.jsp --%>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인화면</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	</head>
	<body>
		<form action="login" method="post" name="f" onsubmit="return input_check(this)">
			<table class="table">
				<tr><th>아이디</th><td><input type="text" class="form-control" name="user_no"></td></tr>
				<tr><th>비밀번호</th><td><input type="password" class="form-control" name="password"></td></tr>
				<tr><td colspan="2"><button class="btn btn-secondary">로그인</button>
				<button type="button" class="btn btn-secondary" onclick="win_open('idForm')">아이디 찾기</button>
				<button type="button" class="btn btn-secondary" onclick="win_open('resetPwForm')">비밀번호 초기화</button>
				</td></tr>
			</table>
		</form>
		
		<script type="text/javascript">
			function input_check(f){
				if(f.user_no.value.trim() == ""){
					alert("아이디를 입력하세요");
					f.user_no.focus();
					return false; // 기존 이벤트 취소
				}
				if(f.password.value.trim() == ""){
					alert("비밀번호를 입력하세요");
					f.password.focus();
					return false; 
				}
				return true;
			}
			
			function win_open(page){ //page : idForm, pwForm
				open(page,"","width=500, height=350, left=50, top=150");
			}
		</script>
		
		
	</body>
</html>