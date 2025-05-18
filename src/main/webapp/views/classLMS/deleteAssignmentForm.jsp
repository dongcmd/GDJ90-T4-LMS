<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 삭제</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">
<div class="container">
    <h4 class="mb-4">과제 삭제</h4>
    <div class="bg-white p-4 rounded shadow-sm">
        <ul class="list-group mb-4">
            <li class="list-group-item"><strong>과제명:</strong> ${assignment.as_name}</li>
            <li class="list-group-item"><strong>내용:</strong> ${assignment.as_content}</li>
            <li class="list-group-item"><strong>제출 시작:</strong> ${assignment.as_s_date}</li>
            <li class="list-group-item"><strong>제출 마감:</strong> ${assignment.as_e_date}</li>
            <li class="list-group-item"><strong>배점:</strong> ${assignment.as_point}</li>
            <li class="list-group-item"><strong>강의코드:</strong> ${assignment.class_no}</li>
            <li class="list-group-item"><strong>반:</strong> ${assignment.ban}</li>
            <li class="list-group-item"><strong>년도:</strong> ${assignment.year}</li>
            <li class="list-group-item"><strong>학기:</strong> ${assignment.term}</li>
        </ul>

        <form action="deleteassignment" method="post">
            <input type="hidden" name="as_no" value="${assignment.as_no}">
            <div class="form-group">
                <label>교수 비밀번호 입력</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="text-right">
                <button type="submit" class="btn btn-danger">과제 삭제</button>
                <a href="manageassignment" class="btn btn-outline-secondary">취소</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>>