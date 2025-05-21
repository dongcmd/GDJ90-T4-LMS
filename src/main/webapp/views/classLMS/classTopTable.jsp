<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-bordered">
    <tr>
        <th>수강학기</th><td>${class1.year}-${class1.term}</td>
        <th>강의코드</th><td>${class1.class_no}-${class1.ban}</td>
        <th>강의명</th><td>${class1.class_name}</td>
        <th>학년</th><td>${class1.class_grade}</td>
    </tr>
    <tr>
        <th>교수</th><td>${class1.prof}</td>
        <th>이수학점</th><td>${class1.credit}</td>
        <th>강의시간</th><td>${class1.s_time}교시 ~ ${class1.e_time}교시</td>
        <th>강의실</th><td>${class1.classroom}</td>
    </tr>
</table>
