<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table thead-light">
    <thead>
        <tr>
            <th class="checkbox-column border-top-0 fw_b">
                <input type="checkbox" name="alchk" onchange="allchkbox(this)"> 고정
            </th>
            <th class="border-top-0 fw_b">No</th>
            <th class="border-top-0 fw_b">알림내용</th>
            <th class="border-top-0 fw_b">알림시간</th>
            <th class="border-top-0 fw_b">삭제</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="noti" items="${notificationsList}">
            <tr>
                <td class="text-center">
                    <input type="checkbox" name="alchk_single" value="${noti.notif_no}" />
                </td>
                <td class="text-center">${noti.notif_no}</td>
                <td class="text-center">${noti.notif_content}</td>
                <td class="text-center"><fmt:formatDate value="${noti.notif_date}" pattern="yyyy-MM-dd" /></td>
                <td class="text-center">
                    <a class="btn btn-outline-danger btn-sm" href="event?delete=${noti.notif_no}">삭제</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty notificationsList}">
            <tr>
                <td colspan="5" class="text-center text-muted">알림이 없습니다.</td>
            </tr>
        </c:if>
    </tbody>
</table>
