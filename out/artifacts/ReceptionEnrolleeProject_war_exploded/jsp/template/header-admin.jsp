<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<header class="header">
    <div class="wrapper">
        <div class="header-row">
            <div class="logo">
                <a class="site-logo" href="<c:url value="/jsp/admin/admin_home.jsp"/>">
                    <img src="<c:url value="/resources/img/logo-bsu-enrollee.png"/>" alt="logo"/>
                </a>
            </div>
            <div class="user-description">
                <div class="username">${requestScope.user.firstName} ${requestScope.user.lastName}</div>
                <div class="role">${requestScope.role}</div>
            </div>
            <div class="logout">
                <form name="logoutForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="logout">
                    <button class="logout-btn">
                        <fmt:message key="main.page.logout"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</header>
