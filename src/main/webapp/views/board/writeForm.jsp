<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board_name} 게시판 글쓰기</title>
</head>
<body>
<h2>${board_name} 게시판 글쓰기</h2>
<form action="write" method="post" enctype="multipart/form-data" name="writeForm">
	<input type="hidden" name="board_id" value="${board_id}">
		<table class="table">
		<tr><td>제목</td><td><input name="title" class="form-control"></td></tr>
		<tr><td colspan="2">
			<textarea rows="10" name="content" class="form-control"
			></textarea>
			<%-- 수정필요
			 id="summernote"></textarea>
			  --%>
		</td></tr>
		<tr><td>첨부파일</td><td><input type="file" name="file"></td></tr>
		<tr><td colspan="2" class="text-center">
			<a href="javascript:chkInput()" class="btn-light btn-outline-secondary">게시물등록</a>
		</td></tr>
	</table>
</form>
<script>
	function chkInput() {
		const f = document.writeForm;
		
		if(f.title.value.trim()=="") {
			 alert("제목을 입력하세요");
			 f.title.focus();
			 return;
			}
		if(f.content.value.trim()=="") {
			 alert("내용을 입력하세요");
			 f.content.focus();
			 return;
		}
		/* 수정필요
		if($('#summernote').summernote('code').trim()=="") {
			 alert("내용을 입력하세요");
			 $('#summernote').summernote('focus');
			 return;
		}
		*/
		f.submit(); //submit 발생=> form의 action 페이지로 요청
	}
</script>
</body>
</html>