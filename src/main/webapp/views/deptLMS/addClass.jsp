<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- addClass 강의 추가 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 개설</title>
</head>
<body>
	<h2>강의 개설</h2>
	<form action="addClass1" name="f" method="post" enctype="multipart/form-data" onsubmit="return input_check(this)">
		<table class="table">
			<tr>
				<td colspan="2">과목명</td>
				<td>과목 코드</td>
				<td>분반</td>
				<td>학년</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2"><input type="text" name="className" class="form-control" placeholder="과목 이름을 입력하세요." style="width: 300px;"></td>
				<td><input type="text" name="classNo" class="form-control" placeholder="과목 코드 입력하세요." style="width: 200px;"></td>
				<td><input type="text" name="classBan" class="form-control" placeholder="반을 입력하세요." style="width: 180px;"></td>
				<td><select name="classGrade" class="form-control" style="width: 80px;" required>
						<option value="">선택</option>
						<c:forEach begin="1" end="4" var="g">
							<option value="${g}">${g}학년</option>
						</c:forEach>
				</select></td>
				<td>
				<button type="button" class="btn btn-light btn-outline-secondary" onclick="win_open_classList('../deptLMS/addClassList')">강의 검색</button>
				</td>
			</tr>
			<tr>
				<td colspan="2">이수학점</td>
				<td>수강인원</td>
				<td>강의실</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2"><input type="number" name="credit" class="form-control" placeholder="이수학점을 입력하세요." style="width: 300px;"></td>
				<td><input type="number" name="maxP" class="form-control" placeholder="수강 인원을 입력하세요." style="width: 200px;"></td>
				<td><input type="text" name="classRoom" class="form-control" placeholder="강의실를 입력하세요." style="width: 180px;"></td>
				<td></td>
				<td></td>
			</tr>
			<tr></tr>
			<tr>
				<td style="text-align: right;">요일<span style="color: red;">*</span></td>
				<td style="text-align: center"><span>수업 시간</span>
				<br>
					<div>
						<div class="form-check form-check-inline flex-column align-items-center text-center">
							<input class="form-check-input" type="checkbox" id="mon" name="days" value="0">
							<label class="form-check-label" for="mon">월&nbsp;</label>
						</div>
						<div class="form-check form-check-inline flex-column align-items-center text-center">
							<input class="form-check-input" type="checkbox" id="tue" name="days" value="1">
							<label class="form-check-label" for="tue">화&nbsp;</label>
						</div>
						<div class="form-check form-check-inline flex-column align-items-center text-center">
							<input class="form-check-input" type="checkbox" id="wed" name="days" value="2">
							<label class="form-check-label" for="wed">수&nbsp;</label>
						</div>
						<div class="form-check form-check-inline flex-column align-items-center text-center">
							<input class="form-check-input" type="checkbox" id="thu" name="days" value="3">
							<label class="form-check-label" for="thu">목&nbsp;</label>
						</div>
						<div class="form-check form-check-inline flex-column align-items-center text-center">
							<input class="form-check-input" type="checkbox" id="fri" name="days" value="4">
							<label class="form-check-label" for="fri">금&nbsp;</label>
						</div>
					</div></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">시작 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type="number" min="1" max="9" name="sTime" oninput="updateTime(0,this)" placeholder="시작 교시를 입력하세요." style="width: 200px;" class="form-control"></td>
				<td>수업 시간<br>
				<span id="startPeriod"></span>
				<span id="middleTilde"></span>
				<span id="endPeriod"></span></td>
				<td>개강 날짜 <input type="date" class="form-control" name="sDate" placeholder="date input"></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">종료 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type="number" min="1" max="9" name="eTime" oninput="updateTime(1,this)" placeholder="종료 교시를 입력하세요." style="width: 200px;" class="form-control"></td>
				<td><span id="endPeriod"></span></td>
				<td>종강 날짜 <input type="date" class="form-control" name="eDate" placeholder="date input"></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><input type="file" name="file1" class="btn btn-light btn-outline-secondary"></td>

			</tr>
			<tr>
				<td colspan="5"><textarea class="form-control" name="courseSyllabus" style="resize: none; width: 600px;"></textarea></td>
				<td style="text-align: right;"><button type="submit" class="btn btn-dark">개설</button></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		function win_open_classList(page){
		    open(page, "", "width=1200, height=800, left=300, top=50");
		    console.log("발동하냐?");
		}
		const s_period = [ "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" ];
		const e_period = [ "09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50", "17:50" ];
		window.onload = function () {
			const sInput = document.querySelector('input[name="sTime"]');
			const eInput = document.querySelector('input[name="eTime"]');
			const sIndex = parseInt(sInput.value);
			const eIndex = parseInt(eInput.value);

			if (sIndex >= 1 && sIndex <= 9) {
				document.querySelector("#startPeriod").innerHTML = s_period[sIndex - 1];
			}
			if (eIndex >= 1 && eIndex <= 9) {
				document.querySelector("#endPeriod").innerHTML = e_period[eIndex - 1];
				document.querySelector("#middleTilde").innerHTML = "~";
			}
		};
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
			if(type == 0)document.querySelector("#startPeriod").innerHTML = s_period[index];
			if(type == 1)document.querySelector("#endPeriod").innerHTML = e_period[index];	
			if (document.querySelector("#startPeriod").innerHTML && document.querySelector("#endPeriod").innerHTML) {
				document.querySelector("#middleTilde").innerHTML = "~";
			}
		}
		function input_check(f) {
		    var e = f.elements;
		    if (!e['className'].value.trim()){ alert("과목명을 입력하세요"); e['className'].focus(); return false; }
		    if (!e['classNo'].value.trim()){ alert("과목 코드를 입력하세요"); e['classNo'].focus();    return false; }
		    if (!e['classBan'].value.trim()){ alert("분반을 입력하세요");    e['classBan'].focus();   return false; }
		    if (!e['classGrade'].value){ alert("학년을 선택하세요");    e['classGrade'].focus();return false; }
		    if (!e['credit'].value.trim()){ alert("이수학점을 입력하세요"); e['credit'].focus();     return false; }
		    if (!e['maxP'].value.trim()){ alert("수강인원을 입력하세요"); e['maxP'].focus();       return false; }
		    if (!e['classRoom'].value.trim()){ alert("강의실을 입력하세요");    e['classRoom'].focus();  return false; }
		    if (!f.querySelector("input[name='days']:checked")) { alert("요일을 하나 이상 선택하세요"); return false; }
		    if (!e['sTime'].value.trim()){ alert("시작 교시를 입력하세요"); e['sTime'].focus();      return false; }
		    var s = parseInt(e['sTime'].value,10);
		    if (s < 1 || s > 9){ alert("시작 교시는 1~9 사이여야 합니다"); e['sTime'].focus(); return false; }
		    if (!e['eTime'].value.trim()){ alert("종료 교시를 입력하세요"); e['eTime'].focus();      return false; }
		    var t = parseInt(e['eTime'].value,10);
		    if (s < 1 || s > 9){ alert("시작 교시는 1~9 사이여야 합니다"); e['sTime'].focus(); return false; }
		    if (t < 1 || t > 9){ alert("종료 교시는 1~9 사이여야 합니다"); e['eTime'].focus(); return false; }
		    if (t < s){ alert("종료 교시는 시작 교시 이후여야 합니다"); e['eTime'].focus(); return false; }
		    return true;
		}
	</script>
</body>
</html>

