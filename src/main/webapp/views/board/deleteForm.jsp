<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 삭제하기</title>
</head>
<body>
삭제한 글은 복구가 불가능합니다.<br>
<%-- 수정필요 글제목 보여주기 길면 ...--%>
글을 삭제하려면 비밀번호를 입력하세요.
<form action="delete" method="post">
<input type="hidden" name="arti_no" value="${param.arti_no}">
<input type="hidden" name="board_id" value="${board_id}">
<input type="password" name="password" class="form-control">
<button type="submit" class="btn btn-outline-danger">삭제</button>
</form>
</body>
</html>