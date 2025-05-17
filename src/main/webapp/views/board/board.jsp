<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- board (공통)게시판 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board_name} 게시판</title>
</head>
<body>
<c:if test="${class1 != null}">
<table class="table table-bordered">
강의게시판일 경우 강의정보 표시 수정필요
	<%--
	<tr>
		<th>수강학기</th><td>${class1.year}-${class1.term}</td>
		<th>강의코드</th><td>${class1.class_no}-${ban}</td>
		<th>강의명</th><td>${class1.class_name}</td>
		<th>학년</th><td>${class1.class_grade}</td>
	</tr>
	<tr>
		<th>교수</th><td>${class1.prof}</td>
		<th>이수학점</th><td>${class1.credit}</td>
		<th>강의시간</th><td>요일 및 교시(시간)수정필요</td>
		<th>강의실</th><td>${class1.classroom}</td>
	</tr>
	 --%>
</table>
</c:if>
<h2>${board_name} 게시판</h2>
	<c:if test="${artiCount == 0}">
		등록된 게시글이 없습니다.
	</c:if>
	<c:if test="${artiCount > 0}"> <%-- 게시글 목록 --%>
		<form action="board" method="post" name="searchForm">
			<input type="hidden" name="pageNum" value="1">
			<input type="hidden" name="board_id" value="${board_id}">
			<select name="column">
				<option value="title">제목</option>
				<option value="writer">작성자</option>
				<option value="content">내용</option>
			</select>
			<c:if test="${not empty param.column}">
				<script>document.searchForm.column.value='${param.column}'</script>
			</c:if>
			<input placeholder="검색어를 입력하세요." name="keyword" value="${param.keyword}">
			<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
		</form>
		<table class="table"><thead class="thead-light">
			<tr><th width="8%">번호</th>
			<th width="53%">제목</th><th width="8%">첨부파일</th>
			<th width="14%">작성자</th><th width="17%">등록일</th>
			</tr></thead>
				<c:forEach var="arti" items="${artiList}">
					<tr><td>${artiIndex}</td><c:set var="artiIndex" value="${artiIndex-1}" />
							<td><a href="article?arti_no=${arti.arti_no}">${arti.arti_title}
								<c:if test="${arti.commCount > 0}">[${arti.commCount}]</c:if>
							</a></td>
							<td><c:if test="${not empty arti.file}">O</c:if></td>
							<td>${arti.user_name}</td>
							<td><fmt:formatDate value="${arti.arti_date}" pattern="yyyy-MM-dd HH:mm" /></td>							
					</tr>
				</c:forEach>
			<%-- 하단 페이지 처리하기 --%>
			<tr><td colspan="5" align="center">
				<c:if test="${pageNum <= 1}">[이전]</c:if>
				<c:if test="${pageNum > 1}">
					<a href="javascript:boardSubmit(${pageNum-1})">[이전]</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<c:if test="${i == pageNum}">[${i}]</c:if>
					<c:if test="${i != pageNum}">
						<a href="javascript:boardSubmit(${i})">[${i}]</a>
					</c:if>
				</c:forEach>
				<c:if test="${pageNum >= maxPage}">[다음]</c:if>
				<c:if test="${pageNum < maxPage}">
					<a href="javascript:boardSubmit(${pageNum+1})">[다음]</a>
				</c:if>
			</td></tr>
			<c:if test="${board_id != 9999 || sessionScope.login == '999'}">
				<tr><td colspan="5" style="text-align:right">
					<p align="right"><a href="writeForm?board_id=${board_id}" class="btn btn-dark">글쓰기</a></p>
				</td></tr>
			</c:if>
		</table>
	</c:if>
<script>
	function boardSubmit(page) {
		f = document.searchForm;
		f.pageNum.value = page;
		f.submit();
	}
</script> 교시 표시 수정필요
<script type="text/javascript">
   const period=[
      '09:00 ~ 09:50',
      '10:00 ~ 10:50',
      '11:00 ~ 11:50',
      '12:00 ~ 12:50',
      '13:00 ~ 13:50',
      '14:00 ~ 14:50',
      '15:00 ~ 15:50',
      '16:00 ~ 16:50',
      '17:00 ~ 17:50'
   ]
   function updateTime(type, input) {
      if(input.value < 1 || input.value > 9){
         input.value = '';
         if(type == 0)document.querySelector("#startPeriod").innerHTML = '';
         if(type == 1)document.querySelector("#endPeriod").innerHTML = '';   
         return null;
      }
      var index = input.value - 1;
      console.log(type);
      console.log(input.value);
      if(type == 0)document.querySelector("#startPeriod").innerHTML = period[index];
      if(type == 1)document.querySelector("#endPeriod").innerHTML = period[index];   
   }
</script>
</body>
</html>