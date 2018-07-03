<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

</head>
<body>

<div style="text-align: center;font-size: 18px;">${tp.tpName}</div>
<div style="font-size: 14px;">
    <c:forEach items="${questionList}" var="q">
        <div>${q.questionName}
            <c:if test="${!empty q.pointName}">
                <span style="color:red;">（指标点：毕业要求${q.pointName}）</span>
            </c:if>
        </div>
        <div>${q.questionContent}</div>
        <br>
        <br>
    </c:forEach>
</div>

</body>
</html>