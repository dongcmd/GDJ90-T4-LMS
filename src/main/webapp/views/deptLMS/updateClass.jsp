<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- updateclass 강의 수정 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 수정</title>
</head>
<body>
	<h2>강의 수정</h2>
	<table>
		<tr>
			<td>과목명</td>
			<td>과목 코드</td>
			<td>분반</td>
		</tr>
		<tr>
			<td><input type="text"></td>
			<td><input type="text"></td>
			<td><input type="text"></td>
			<td><button>강의 검색</button></td>
		</tr>
		<tr>
			<td>과목명</td>
			<td>과목 코드</td>
			<td>분반</td>
		</tr>
		<tr>
			<td><input type="text"></td>
			<td><input type="text"></td>
			<td><select>
			</select></td>
		</tr>
		<tr></tr>
		<tr>
			<td>요일<span style="color: red;">*</span></td>
			<td style="text-align: center"><span>수업 시간</span><br>
				<div class="day-checkbox">
					<input type="checkbox" id="mon" name="days" value="월">
					<input type="checkbox" id="tue" name="days" value="화">
					<input type="checkbox" id="wed" name="days" value="수">
					<input type="checkbox" id="thu" name="days" value="목">
					<input type="checkbox" id="fri" name="days" value="금">
					<br>
					<label for="mon">월&nbsp;</label>
					<label for="tue">화&nbsp;</label>
					<label for="wed">수&nbsp;</label>
					<label for="thu">목&nbsp;</label>
					<label for="fri">금</label>
				</div>
				<button onclick="test">확인</button> <script type="text/javascript">
					function test() {
						console.log("")
					}
				</script></td>
		</tr>
		<tr>
			<td>시작 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="number" min="1" max="9" oninput="updateTime(0,this)"></td>
			<td><span id="startPeriod"></span></td>
		</tr>
		<tr>
			<td>종료 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="number" min="1" max="9" oninput="updateTime(1,this)"></td>
			<td><span id="endPeriod"></span></td>
		</tr>
	</table>
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