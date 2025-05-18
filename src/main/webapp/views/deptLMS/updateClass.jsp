<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- updateClass 강의 수정 오예록 --%>
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
								<td colspan="2">과목명</td>
								<td>과목 코드</td>
								<td>분반</td>
								<td>학년</td>
								<td></td>
						</tr>
						<tr>
								<td colspan="2"><input type="text" name="className" class="form-control" placeholder="과목을 입력하세요."
												style="width: 300px;"></td>
								<td><input type="text" name="classNo" class="form-control" placeholder="과목 코드 입력하세요." style="width: 200px;"></td>
								<td><input type="text" name="classBan" class="form-control" placeholder="반을 입력하세요." style="width: 180px;"></td>
								<td><select name="classGrade" class="form-control" style="width: 80px;" required>
												<option value="">선택</option>
												<c:forEach begin="1" end="4" var="g">
														<option value="${g}">${g}학년</option>
												</c:forEach>
								</select></td>
								<td><button class="btn btn-light btn-outline-secondary">강의 검색</button></td>
						</tr>
						<tr>
								<td colspan="2">이수학점</td>
								<td>수강인원</td>
								<td>강의실</td>
								<td></td>
								<td></td>
						</tr>
						<tr>
								<td colspan="2"><input type="number" name="credit" class="form-control" placeholder="이수학점을 입력하세요."
												style="width: 300px;"></td>
								<td><input type="number" name="maxP" class="form-control" placeholder="수강 인원을 입력하세요." style="width: 200px;"></td>
								<td><input type="text" name="classRoom" class="form-control" placeholder="강의실를 입력하세요."
												style="width: 180px;"></td>
								<td></td>
								<td></td>
						</tr>
						<tr></tr>
						<tr>
								<td style="text-align: right;">요일<span style="color: red;">*</span></td>
								<td style="text-align: center"><span>수업 시간</span><br>
										<div>
												<div class="form-check form-check-inline flex-column align-items-center text-center">
														<input class="form-check-input" type="checkbox" id="mon" name="days" value="월">
														<label class="form-check-label" for="mon">월&nbsp;</label>
												</div>
												<div class="form-check form-check-inline flex-column align-items-center text-center">
														<input class="form-check-input" type="checkbox" id="tue" name="days" value="화">
														<label class="form-check-label" for="tue">화&nbsp;</label>
												</div>
												<div class="form-check form-check-inline flex-column align-items-center text-center">
														<input class="form-check-input" type="checkbox" id="wed" name="days" value="수">
														<label class="form-check-label" for="wed">수&nbsp;</label>
												</div>
												<div class="form-check form-check-inline flex-column align-items-center text-center">
														<input class="form-check-input" type="checkbox" id="thu" name="days" value="목">
														<label class="form-check-label" for="thu">목&nbsp;</label>
												</div>
												<div class="form-check form-check-inline flex-column align-items-center text-center">
														<input class="form-check-input" type="checkbox" id="fri" name="days" value="금">
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
								<td><input type="number" min="1" max="9" name="sTime" oninput="updateTime(0,this)"
												placeholder="시작 교시를 입력하세요." style="width: 200px;" class="form-control"></td>
								<td><span id="startPeriod"></span></td>
								<td></td>
								<td></td>
								<td></td>
						</tr>
						<tr>
								<td style="text-align: right;">종료 교시<span style="color: red;">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td><input type="number" min="1" max="9" name="eTime" oninput="updateTime(1,this)"
												placeholder="종료 교시를 입력하세요." style="width: 200px;" class="form-control"></td>
								<td><span id="endPeriod"></span></td>
								<td></td>
								<td></td>
								<td></td>
						</tr>
						<tr>
								<td colspan="6"><input type="file" name="file1" class="btn btn-light btn-outline-secondary"></td>

						</tr>
						<tr>
								<td colspan="5"><textarea class="form-control" name="courseSyllabus" style="resize: none; width: 600px;"></textarea></td>
								<td style="text-align: right;"><a href="javascript:input_check()" class="btn btn-dark">수정</a></td>
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
			f.submit();
		}
	</script>
</body>
</html>