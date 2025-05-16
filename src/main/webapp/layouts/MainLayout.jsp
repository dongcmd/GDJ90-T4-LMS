<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<c:set var="path" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:write property="title" /></title>
 	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">  
  	<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  	
  	<style>
		*{margin: 0; padding: 0; letter-spacing: -0.05rem; font-size: 16px; color: #333;}
    	h1{margin: 0;}
    	a{display: block; color: #333;}
    	img{display: block; max-width: 100%; margin: 0 auto;}
	</style>
  	<sitemesh:write property="head" />
</head>

<body>
	<header>
        <div class="text-center d-flex justify-content-around align-items-center" style="height: 150px; padding: 0 100px;">
            <h1 style="flex: 1;">
                <a href="#" style="width: 100px;">

                    <img src="logo.png" alt="메인 로고">
                </a>
            </h1>
            <%-- 메인 타이틀 --%>
            <c:if test="${fn:startsWith(relativeURI, '/mainLMS/')}">
            	<h2 class="m-0" style="flex: 2;">구디 대학교 학사관리 시스템</h2>
            </c:if>
            <c:if test="${fn:startsWith(relativeURI, '/deptLMS/')}">
            	<h2 class="m-0" style="flex: 2;"><span>${sessionScope.major_name}</span>학과 학사관리 시스템</h2>
            </c:if>
            <c:if test="${fn:startsWith(relativeURI, '/classLMS/')}">
            	<h2 class="m-0" style="flex: 2;"><span>${sessionScope.major_name}</span>학과 학사관리 시스템</h2>
            </c:if>
            
            <ul class="nav d-flex justify-content-end" style="flex: 1; gap: 10px;">
                <li class="nav-item">
                  <a class="nav-link" onclick="win_open('info')"><span>${sessionScope.user_name}</span> 님 반갑습니다.</a>
                </li>
                <li class="nav-item">
                    <button type="button" class="btn btn-light btn-outline-secondary" data-toggle="modal" data-target="#myModal">알림</button>
                </li>
                <li class="nav-item">
                    <a href="logout" class="btn btn-dark" role="button">로그아웃</a>
                </li>
            </ul>
        </div>
        <%-- 알림모달 --%>
        <div class="modal fade" id="myModal">
            <div class="modal-dialog modal-m">
              <div class="modal-content" style="width: 800px;">
                <div class="modal-header">
                  <h4 class="modal-title">알림창</h4>
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">

                    <table class="table">
                        <thead>
                            <tr>
                                <th class="checkbox-column border-top-0">
                                    <input type="checkbox" name="alchk" onchange="allchkbox(this)"> 고정
                                </th>
                                <th class="border-top-0" scope="col">No</th>
                                <th class="border-top-0" scope="col">알림내용</th>
                                <th class="border-top-0" scope="col">알림시간</th>
                                <th class="border-top-0" scope="col">삭제</th>
                            </tr>
                        </thead>
                        <tr>
                            <td class="checkbox-column">
                                <input type="checkbox" name="idchks" class="idchk" value="">

                                ${sessionScope.is_pinned}
                            </td>
                            <td>${sessionScope.notif_no}</td>
                            <td>${sessionScope.notif_content}</td>
                            <td>${sessionScope.notif_date}</td>
                            <td><a href="#" class="btn btn-dark">삭제</a></td>
                        </tr>
                    </table>
                </div>
              </div>
            </div>
        </div>
    </header>

	<%-- 메인 레이아웃 --%>
    <div class="d-flex bg-light" style="min-height: 800px;">
    	<%-- mainLMS 메뉴 --%>
    	<c:if test="${fn:startsWith(relativeURI, '/mainLMS/')}">
			<nav class="col-sm-2 navbar align-items-start  p-0" style="background-color: #999;">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">                       
	                <li class="nav-item">
	                    <a class="nav-link" href="${path}/mainLMS/bord?id=9999">공지 게시판</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link" href="${path}/deptLms/board?id=">학과 LNS</a>
	                </li> 
	                <c:if test="${sessionScope.user.role == 1}">

		                <li class="nav-item">
		                    <a class="nav-link" href="#">수강 신청</a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="#">학점 조회</a>
		                </li>
	                </c:if>

		            <c:if test="${sessionScope.role == 3}">
		                <li class="nav-item">
		                    <a class="nav-link" href="adminForm">사용자 관리</a>
		                </li>
	                </c:if>
	            </ul>
	        </nav>
		</c:if>

		<%-- deptLMS 메뉴 --%>
		<c:if test="${fn:startsWith(relativeURI, '/deptLMS/')}">

	        <nav class="col-sm-2 navbar align-items-start  p-0" style="background-color: #999;">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">                       
	                <li class="nav-item">
	                    <a class="nav-link" href="#">학과 게시판</a>
	                </li>

	                <c:if test="${sessionScope.role == 1 }">

		                <li class="nav-item">
		                    <a class="nav-link" href="#">수강 조회</a>
		                </li>
	                </c:if>

		            <c:if test="${sessionScope.role == 2 }">

		                <li class="nav-item">
		                    <a class="nav-link" href="#">강의 조회</a>
		                </li>
	                </c:if>
	            </ul>
	        </nav>
        </c:if>
        

        <%-- classLMS 메뉴 --%>
        <c:if test="${fn:startsWith(relativeURI, '/classLMS/')}">
	        <nav class="col-sm-2 navbar align-items-start  p-0" style="background-color: #999;">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">                       
	                <li class="nav-item">
	                    <a class="nav-link" href="#">자바 프로그래밍</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link" href="#">질문 게시판</a>
	                </li> 
	                <c:if test="${sessionScope.role == 1 }">
		                <li class="nav-item">
		                    <a class="nav-link" href="#">출결 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="#">과제 제출</a>
		                </li>
	                </c:if>
		            <c:if test="${sessionScope.role == 2 }">

		                <li class="nav-item">
		                    <a class="nav-link" href="#">과제 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="#">학점 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="#">출결 관리</a>
		                </li>
	                </c:if>
	            </ul>
	        </nav>        
        </c:if> 

        <%-- 메인 콘텐츠 --%>
        <div class="col-sm-10 container">
            <sitemesh:write property="body" />  
        </div>
    </div>
 	
	<%-- 푸터 --%>
    <footer class="footer d-flex" style="height: 100px; margin-top: 10px; background-color: #eee;">
        <ul class="nav justify-content-around align-items-center" style="width: 100%;">
            <li class="nav-item ">
                <p class="m-0">Tel) 02-1111-2222</p>
            </li>
            <li class="nav-item">
                <p class="m-0">담당자 : 홍길동</p>
            </li>
            <li class="nav-item">
                <p class="m-0">오시는길 : 서울시 금천구 가디로 12-34</p>
            </li>
        </ul>
    </footer>
    
    <script type="text/javascript">
	    function win_open(page){ //page : idForm, pwForm
			open(page,"","width=500, height=350, left=50, top=150");
		}
    </script>
</body>
</html>