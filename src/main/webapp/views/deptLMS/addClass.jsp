<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- addClass 강의 추가 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 추가</title>
</head>
<body>
	<h2>강의 추가</h2>
	<form action="add" name="f" method="post" onsubmit="return input_check(this)">
		<table class="table">
			<tr>
				<td>과목명</td>
				<td>과목 코드</td>
				<td>분반</td>
				<td>학년</td>
			</tr>
			<tr>
				<td><input type="text" name="className" class="form-control"></td>
				<td><input type="text" name="classNo" class="form-control"></td>
				<td><input type="text" name="classBan" class="form-control" style="width: 80px;"></td>
				<td><input type="text" name="class" class="form-control" style="width: 80px;"></td>
				<td><button class="btn btn-light btn-outline-secondary">강의 검색</button></td>
			</tr>
			<tr>
				<td>이수학점</td>
				<td>수강인원</td>
				<td>강의실</td>
				<td></td>
			</tr>
			<tr>
				<td><input type="number" name="credit" class="form-control"></td>
				<td><input type="number" name="maxP" class="form-control"></td>
				<td><input type="text" name="classRoom" class="form-control" style="width: 80px;"></td>
				<td></td>
				<td></td>
			</tr>
			<tr></tr>
			<tr>
				<td style="text-align: right;">요일<span style="color: red;">*</span></td>
				<td style="text-align: center"><span>수업 시간</span><br>
					<div class="day-checkbox">
						<input type="checkbox" id="mon" name="days" value="월">
						&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="tue" name="days" value="화">
						&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="wed" name="days" value="수">
						&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="thu" name="days" value="목">
						&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="fri" name="days" value="금">
						<br>
						<label for="mon">월&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label for="tue">화&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label for="wed">수&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label for="thu">목&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label for="fri">금</label>
					</div></td>
					<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">시작 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type="number" min="1" max="9" name="sTime" oninput="updateTime(0,this)" class="form-control"></td>
				<td><span id="startPeriod"></span></td>
			</tr>
			<tr>
				<td style="text-align: right;">종료 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type="number" min="1" max="9" name="eTime" oninput="updateTime(1,this)" class="form-control"></td>
				<td><span id="endPeriod"></span></td>
				<td></td>
				<td><button type="button" class="btn btn-dark">수정</button></td>
			</tr>
		</table>
	</form>
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
		function input_check(f) {
			if (f.className.value.trim() == "") {
				alert("아이디를 입력하세요");
				f.id.focus();
				return false; //기본 이벤트 취소 
			}
		}
	</script>
</body>
</html>