<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인</title>
    <!-- Bootstrap 4 CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
	    li{list-style: none;}
	    .main_list{height: 400px; padding: 5; background-color: #fff; border-radius: 10px;}
	    .main_list .list-group-item{border: none; border-bottom: 1px solid #eee;}
	    .main_list .list-group-item:last-child{border: none;}
    </style>
</head>
<body>
<div class="row py-5" style="justify-content: center; gap: 10px;">
                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">공지사항</h3>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="m" items="${list}">
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">캘린더</h3>
                    <p>세부 콘텐츠 내용글</p>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">강의목록</h3>
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>과목명</th>
                                <th>강의실</th>
                                <th>이름</th>
                                <th>강사명</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${list}">
                                <tr>
                                    <a href="#">
                                        <td>info?id=${m.id}   ${m.name}</td>
                                        <td>${m.name}</td>
                                        <td>${m.tel}</td>
                                        <td>${m.email}</td>
                                    </a>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">과제목록</h3>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="m" items="${list}">
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
</body>
</html>
