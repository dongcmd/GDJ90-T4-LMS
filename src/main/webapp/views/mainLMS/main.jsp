<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>

<%-- 원동인 --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구디 대학교 학사관리 시스템</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
	    li{list-style: none;}
	    .di_btn{text-align: center;padding-top: 2px;color: #fff;width: 50px;border-radius: 100px;background-color: #343a40;font-size: 12px;}
	    .main_list{min-width:600px; height: 400px; background-color: #fff; border:1px solid #eee; border-radius: 10px;}
	    .main_list .list-group-item{border: none; border-bottom: 1px solid #eee;}
	    .main_list .list-group-item:last-child{border: none;}
    </style>
</head>
<body>
	<div class="row" style="justify-content: center; gap: 10px;">
	    <div class="main_list col-sm-5 px-4 py-5">
	        <h3 class="mb-4 fw_b">공지사항</h3>
	        <ul class="list-group list-group-flush">
	            <%--  <c:forEach var="m" items="${list}"> --%>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	             <%-- </c:forEach> --%>
	        </ul>
	    </div>

        <div class="main_list col-sm-5 px-4 py-5">
            <h3 class="mb-4 fw_b">캘린더 
            	<c:if test="${login.role == 3}">
            		<a href="${path}/mainLMS/event" class="btn btn-dark" role="button">등록</a>
            	</c:if>
            </h3>
			<h2>${year}년 ${month}월</h2>
			<table class="table">
			    <thead>
			        <tr>
			            <th>일</th>
			            <th>월</th>
			            <th>화</th>
			            <th>수</th>
			            <th>목</th>
			            <th>금</th>
			            <th>토</th>
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach var="cell" items="${calendarCells}" varStatus="status">
			            <c:if test="${status.index % 7 == 0}">
			                <tr>
			            </c:if>
			            <td>
			                <c:if test="${cell.date != null}">
			                    <div>${cell.date}</div>
			                    <c:forEach var="event" items="${cell.events}">
			                        <div>${event.event_name}</div>
			                    </c:forEach>
			                </c:if>
			            </td>
			            <c:if test="${status.index % 7 == 6}">
			                </tr>
			            </c:if>
			        </c:forEach>
			    </tbody>
			</table>
        </div>

        <div class="main_list col-sm-5 px-4 py-5">
            <h3 class="mb-4 fw_b">강의목록</h3>
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th class="fw_b">과목명</th>
                        <th class="fw_b">강의실</th>
                        <th class="fw_b">이름</th>
                        <th class="fw_b">강사명</th>
                    </tr>
                </thead>
                <tbody>
                   <%-- <c:forEach var="m" items="${list}"> --%>
                        <tr>
	                        <td>자바스크립트</td>
	                        <td>101호</td>
	                        <td>1교시</td>
	                        <td>홍길동</td>
                        </tr>
                        <tr>
	                        <td>자바스크립트</td>
	                        <td>101호</td>
	                        <td>1교시</td>
	                        <td>홍길동</td>
                        </tr>
                        <tr>
	                        <td>자바스크립트</td>
	                        <td>101호</td>
	                        <td>1교시</td>
	                        <td>홍길동</td>
                        </tr>
                        <tr>
	                        <td>자바스크립트</td>
	                        <td>101호</td>
	                        <td>1교시</td>
	                        <td>홍길동</td>
                        </tr>
                    <%-- </c:forEach> --%>
                </tbody>
            </table>
        </div>

        <div class="main_list col-sm-5 px-4 py-5">
            <h3 class="mb-4 fw_b">과제목록</h3>
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th class="fw_b">강의명</th>
                        <th class="fw_b">과제명</th>
                        <th class="fw_b">기한</th>
                    </tr>
                </thead>
                <tbody>
                   <%-- <c:forEach var="m" items="${list}"> --%>
                        <tr>
	                        <td>AJAX</td>
	                        <td>비동기 통신의 개념과 동작 방식 설명</td>
	                        <td>25.05.17 ~ 25.05.22</td>
                        </tr>
                        <tr>
	                        <td>DOM</td>
	                        <td>HTML 요소를 자바스크립트로 추가/삭제/변경</td>
	                        <td>25.05.17 ~ 25.05.22</td>
                        </tr>
                        <tr>
	                        <td>Event listener</td>
	                        <td>버튼 클릭, 마우스 이동 등의 이벤트 핸들링</td>
	                        <td>25.05.17 ~ 25.05.22</td>
                        </tr>
                        <tr>
	                        <td>Fetch API</td>
	                        <td>REST API와 비동기 요청 실습</td>
	                        <td>25.05.17 ~ 25.05.22</td>
                        </tr>
                    <%-- </c:forEach> --%>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
