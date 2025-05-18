<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<!-- CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>

<meta charset="UTF-8">
<title>${arti.arti_title} 수정하기</title>
</head>
<body>
<h2>${board_name} 게시판/${arti.arti_title} 수정하기</h2>
<form action="update" method="post" enctype="multipart/form-data" name="updateForm">
	<input type="hidden" name="arti_no" value="${arti.arti_no}">
	<input type="hidden" name="file2" value="${arti.file}">
		<table class="table">
		<tr><td>제목</td><td><input name="title" value="${arti.arti_title}" class="form-control"></td></tr>
		<tr><td colspan="2">
			<textarea rows="10" name="content" class="form-control" id="summernote">
			${arti.arti_content}</textarea>
		</td></tr>
		<tr><td>첨부파일</td>
		<td><c:if test="${!empty arti.arti_file}">
				<div id="file_desc">${arti.arti_file}
			  <a href="javascript:file_delete()">[첨부파일 삭제]</a></div>
			</c:if>    
			<input type="file" name="file1"></td></tr>
		<tr><td colspan="2" class="text-center">
			<a href="javascript:chkInput()" class="btn-light btn-outline-secondary">게시물수정</a>
		</td></tr>
	</table>
</form>
<script>
function chkInput() {
	const f = document.updateForm;
	
	if(f.title.value.trim()=="") {
		 alert("제목을 입력하세요");
		 f.title.focus();
		 return;
		}
	if($('#summernote').summernote('code').trim()=="") {
		 alert("내용을 입력하세요");
		 $('#summernote').summernote('focus');
		 return;
	}
	f.submit(); //submit 발생=> form의 action 페이지로 요청
}
</script>
<script>
function file_delete() { 
 document.updateForm.file2.value=""; 
 file_desc.style.display="none"; 
}
</script>
<script>
function sendFile(file) {
	   let data = new FormData();
	   data.append("file",file);
	   $.ajax({
		   url : "${path}/files/uploadImage",
		   type : "post",
		   data : data,
		   processData : false,
		   contentType : false,
		   success : function(url) {
			   $("#summernote").summernote("insertImage",url);
		   },
		   error : function(e) {
			   alert("이미지 업로드 실패 :" + e.status);
		   }
	   })
}
</script>
</body>
</html>