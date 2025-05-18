<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 원동인 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 제출</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<h3 class="my-4">과제 추가</h3>
	
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center"></th>
					<th style="text-align: center">과제 제목</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">제출 여부</th>
					<th style="text-align: center">업로드</th>
					<th style="text-align: center">다운로드</th>
				</tr>
			</thead>
			
			<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>
			<tbody>
				<tr>
					<td style="text-align: center">1</td>
					<td>형변환 문제</td>
					<td>~ 2025-3-10 23:59:59</td>
					<td style="text-align: center">제출</td>
					<td>
						<button type="button" class="btn btn-light btn-outline-secondary">업로드</button>
					</td>
					<td>
						<button type="button" class="btn btn-dark">다운로드</button>
					</td>
				</tr>
				<tr>
					<td style="text-align: center">2</td>
					<td>형변환 문제</td>
					<td>~ 2025-3-10 23:59:59</td>
					<td style="text-align: center">제출</td>
					<td>
						<button type="button" class="btn btn-light btn-outline-secondary">업로드</button>
					</td>
					<td>
						<button type="button" class="btn btn-dark">다운로드</button>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>