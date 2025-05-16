<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 관리</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">

<div class="container">

    <h4 class="mb-4">사용자 관리</h4>

    <!--검색 필터 -->
    <form action="searchUsers" method="post" class="form-inline mb-3">
        <label class="mr-2">검색 조건</label>
        <select name="type" class="form-control mr-2">
            <option value="user_name">이름</option>
            <option value="user_no" selected>유저번호</option>
            <option value="email">이메일</option>
        </select>
        <input type="text" name="keyword" class="form-control mr-2" placeholder="검색어를 입력하세요">
        <button type="submit" class="btn btn-primary">검색</button>
    </form>

    <!-- 사용자 테이블 -->
    <table class="table table-bordered table-hover bg-white text-center">
        <thead class="thead-light">
            <tr>
                <th>No</th>
                <th>이름</th>
                <th>유저번호</th>
                <th>구분</th>
                <th>성별</th>
                <th>학과코드</th>
                <th>학년</th>
                <th>이메일</th>
                <th>전화번호</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${user.user_name}</td>
                    <td>${user.user_no}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.role == 1}">학생</c:when>
                            <c:when test="${user.role == 2}">교수</c:when>
                            <c:otherwise>관리자</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${user.gender == 1 ? '남' : '여'}</td>
                    <td>${user.major_no}</td>
                    <td>${user.grade}</td>
                    <td>${user.email}</td>
                    <td>${user.tel}</td>
                    <td>
                        <a href="updateaccountForm?id=${user.user_no}" class="btn btn-sm btn-outline-primary">수정</a>
                    </td>
                    <td>
                        <a href="deleteaccount" class="btn btn-sm btn-outline-danger" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- ➕ 사용자 추가 버튼 -->
    <div class="d-flex justify-content-end">
        <a href="addUserForm" class="btn btn-success">사용자 추가</a>
    </div>

</div>

</body>
</html>