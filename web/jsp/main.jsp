<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<fmt:setLocale value="${sessionScope.locale}"/>
<%--<fmt:bundle basename="text"/>--%>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="main.page.welcomtitle"/></title>
</head>
<body>
<h3><fmt:message key="main.page.welcom"/></h3>
<hr/>
${user}, <fmt:message key="main.page.hello"/>!
<hr/>

<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="logout" />
    <br/>
    <input type="submit" value="<fmt:message key="main.page.logout"/>"/>
</form>

</body>
</html>
