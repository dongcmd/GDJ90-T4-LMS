<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  		@font-face {font-family: 'GmarketSansMedium'; src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff'); font-weight: normal; font-style: normal;}
		*{margin: 0; padding: 0; font-family: 'GmarketSansMedium'; font-weight:500; letter-spacing: -0.05rem; font-size: 16px; color: #333; box-sizing:border-box;}
    	a{display: block; color: #333 !important;}
    	img{display: block; max-width: 100%; margin: 0 auto;}
    	.table td, .table th{vertical-align:middle !important;}
    	.table tr td{white-space: nowrap;overflow: hidden; text-overflow: ellipsis; max-width: 200px;}
    	.btn-dark{color: #fff  !important;}
    	.fw_b{font-weight:600;}
    	.nav-link{padding:0; cursor:pointer; font-weight:500; color:#333;}
    	.nav-link:hover, .nav-link:hover span{color:#333; font-weight:600;}
    	.nav-link span{text-decoration:underline;}
    	.main_menu li{height: 100px; line-height:100px; border-bottom:1px solid #eee;}
    	.main_menu li a{display:block; font-size:20px; font-weight:600;text-decoration:none; width:100%; height:100%; background-color:#fff; color:#333;}
		.main_menu li a:hover, .main_menu li a:focus{background-color:#343a40; color:#fff !important; transition: 0.3s ease-out;}
		footer ul li p{font-size:14px; line-height:1; font-weight:500; color:#eee;}
	</style>
  	<sitemesh:write property="head" />
</head>
<%-- 원동인 --%>
<body>
	<header>
        <div class="text-center d-flex justify-content-around align-items-center" style="height: 120px; padding: 0 5rem; border-bottom:2px solid #343a40;">
            <h1 class="m-0" style="flex: 1;">
                <a href="${path}/mainLMS/main" style="width: 100px;">
                    <img src="${pageContext.request.contextPath}/picture/main_logo.png" alt="메인로고" title="메인 페이지">
                </a>
            </h1>
            <%-- 메인 타이틀 --%>
            <c:if test="${fn:startsWith(relativeURI, '/mainLMS/')}">
            	<h2 class="m-0 fw_b" style="flex: 2;">구디 대학교 학사관리 시스템</h2>
            </c:if>
            <c:if test="${fn:startsWith(relativeURI, '/deptLMS/')}">
            	<h2 class="m-0 fw_b" style="flex: 2;"><span>${sessionScope.major_name}</span>학과 학사관리 시스템</h2>
            </c:if>
            <c:if test="${fn:startsWith(relativeURI, '/classLMS/')}">
            	<h2 class="m-0 fw_b" style="flex: 2;"><span>${sessionScope.major_name}</span>학과 학사관리 시스템</h2>
            </c:if>
            
            <ul class="nav d-flex justify-content-end align-items-center" style="flex: 1; gap: 10px; flex-wrap:nowrap;">
                <li class="nav-item">

                  <a class="nav-link" onclick="win_open('../users/info')"><span>${login.user_name}</span> 님 반갑습니다.</a> <!-- 기흔 수정 -->
                </li>
                <li class="nav-item">
                    <button type="button" class="btn btn-light btn-outline-secondary" data-toggle="modal" data-target="#myModal">알림</button>
                </li>
                <li class="nav-item">

                    <a href="../users/logout" class="btn btn-dark" role="button">로그아웃</a> <!-- 기흔 수정 -->
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
    <div class="d-flex bg-light" style="min-height: 800px; overflow:hidden;">
    	<nav class="col-sm-2 navbar align-items-start  p-0" style="background-color: #fff; box-shadow:4px 4px 10px 0px #eee;">
    	
    		<%-- mainLMS 메뉴 --%>
    		<c:if test="${fn:startsWith(relativeURI, '/mainLMS/')}">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">
	                <li class="nav-item">
	                    <a href="../mainLMS/signUpClass">수강신청</a>
	                </li>
	                <li class="nav-item">
	                    <a href="#">학과 LMS</a>
	                </li>
	                <li class="nav-item">
	                    <a href="#">학점조회</a>
	                </li>
	                <li class="nav-item"> <%-- 이동원 --%>
	                    <a href="../board/board?board_id=9999">공지게시판</a>
	                </li>
	                <c:if test="${login.role == 3}"> <%-- 기흔 수정 --%>
	                <li class="nav-item">
	                    <a href="../mainLMS/adminForm">사용자관리</a>
	                </li>
	                </c:if>
	            </ul>
            </c:if>

	        <%-- deptLMS 메뉴 --%>
	        <c:if test="${fn:startsWith(relativeURI, '/deptLMS/')}">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">                       
	                <li class="nav-item">
	                    <a href="#">학과 게시판</a>
	                </li>
	                <c:if test="${login.role == 1 }"> <!-- 기흔 수정 -->
		                <li class="nav-item">
		                    <a href="#">수강 조회</a>
		                </li>
	                </c:if>
		            <c:if test="${login.role == 2 }"> <!-- 기흔 수정 -->
		                <li class="nav-item">
		                    <a href="#">강의 조회</a>
		                </li>
	                </c:if>
	            </ul>
	        </c:if>
	        
	        <%-- classLMS 메뉴 --%>
	        <c:if test="${fn:startsWith(relativeURI, '/classLMS/')}">
	            <ul class="main_menu nav flex-column text-center" style="width: 100%;">                       
	                <li class="nav-item">
	                    <a href="#">자바 프로그래밍</a>
	                </li>
	                <li class="nav-item">
	                    <a href="#">질문 게시판</a>
	                </li> 

	                <c:if test="${login.role == 1 }"> <!-- 기흔 수정 -->
		                <li class="nav-item">
		                    <a href="#">출결 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a href="#">과제 제출</a>
		                </li>
	                </c:if>
		            <c:if test="${login.role == 2 }"> <!-- 기흔 수정 -->
		                <li class="nav-item">
		                    <a href="#">과제 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a href="#">학점 관리</a>
		                </li>
		                <li class="nav-item">
		                    <a href="#">출결 관리</a>
		                </li>
	                </c:if>
	            </ul>
			</c:if>
        </nav>
        
        <%-- 메인 콘텐츠 --%>
        <div class="col-sm-10 container p-5">
            <sitemesh:write property="body" />  
        </div>
    </div>
 	
	<%-- 푸터 --%>
    <footer style="background-color:#343a40;">
        <ul class="d-flex justify-content-around align-items-center m-0 py-5" style="width: 100%;">
            <li>
                <p class="m-0">&copy; 2025 구디 대학교. All rights reserved.</p>
            </li>
            <li>
                <p class="m-0">문의 : 학사관리팀 &nbsp;|&nbsp; 전화 : 02-1234-5678 &nbsp;|&nbsp; 이메일 : info@gdschool.ac.kr</p>
            </li>
            <li>
               <p class="m-0">주소 : 서울특별시 구디구 디비로 517, 구디 대학교</p>
            </li>
        </ul>
    </footer>
    
<script type="text/javascript">
    function win_open(page){
        open(page,"","width=500, height=350, left=50, top=150");
    }
</script>
</body>
</html>