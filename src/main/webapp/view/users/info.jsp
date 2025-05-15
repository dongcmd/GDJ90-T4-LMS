<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기본 정보</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<ul value="기본 정보"><h4>기본정보</h4>
  <li width="30%">아이디(학번) : ${user.user_no}</li>
  <li>이름 : ${user.name}</li>
  <li>성별 : ${user.gender == 1?"남":"여"}</li>
  <c:if test="${user.role == 1}">
  <li>학년 : ${user.grade}</li>
  </c:if>
  <li>학과 : ${user.major_no}</li>
  <c:set var="tel1" value="${fn:substring(user.tel, 0, 3)}" />
  <c:set var="tel2" value="${fn:substring(user.tel, 3, 7)}" />
  <c:set var="tel3" value="${fn:substring(user.tel, 7, 11)}" />
  <li>전화번호 : ${tel1}-${tel2}-${tel3}</li>
  <li>이메일 : ${user.email}</li>
</ul>
     <a href="updateForm?id=${user.user_no}" class="btn btn-light btn-outline-secondary">
     		정보 수정 
     </a>
     <a href="pwForm?id=${user.user_no}" class="btn btn-light btn-outline-secondary">
     		비밀번호 재설정
     </a>
</body>
</html>