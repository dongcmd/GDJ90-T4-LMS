<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 전 화면 조회</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<form action="update" method="post" onsubmit="return input_check(this)" name="f">
<table>
<th>아이디</th>
     <td><input type="text" value="${user.user_no}" name="user_no" readonly></td>
</tr>
<tr><th>이름</th><td><input type="text" name="name" value="${user.name}"></td>
	<th>학과</th><td><input type="text" name="major" value="${user.major_no}"></td>
</tr>
<tr>
<th>성별</th><td>
<input type="radio" name="gender" value="1" ${user.gender == 1?"checked":""}>남
<input type="radio" name="gender" value="2" ${user.gender == 2?"checked":""}>여
</td>
<c:if test="${user.role == 1}">
<th>학년</th><td>
 <select name="grade">
  <option value="choose" selected>-- 선택하세요 --</option>
  <option value="1">1학년</option>
  <option value="2">2학년</option>
  <option value="3">3학년</option>
  <option value="4">4학년</option>
 </select>
</td>
</c:if>
</tr>
<tr><th>전화번호</th><td colspan="2">
<input type="text" name="tel" value="${user.tel}"></td></tr>
<tr><th>이메일</th><td colspan="2">
<input type="text" name="email" value="${user.email}"></td></tr>
<tr><th>비밀번호 확인</th>
	 <td><input type="password" name="password"></td></tr>
<tr><td colspan="3"><button class="btn btn-light btn-outline-secondary">내 정보 수정</button>
</td></tr>
</table></form>

<script type="text/javascript">
   function  inputcheck(f) {
       if(f.password.value == "") {
		   alert("비밀번호를 입력하세요");
		   f.password.focus();
		   return false;
	   }
       return true;
   }   
   function win_passchg() {
	  var op = "width=500, height=250, left=50,top=150";
	  open("passwordForm","",op);
   }
</script>
</body></html>