<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<header>
    <div class="main-st">
        <div class="logo-user">
            <div class="t-center">
                <a class="site-logo" href="<c:url value="/jsp/admin/admin_home.jsp"/>">
                    <img src="<c:url value="/resources/img/logo-bsu-enrollee.png"/>" alt="logo"/>
                </a>
            </div>
            <div class="box-info">
                <div class="username">
                    <p>${requestScope.user}</p>
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
    </div>
</header>