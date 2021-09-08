<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="icon" type="image/png" href="<c:url value="/resources/img/icons/favicon.ico"/>">
    <script src="<c:url value="/resources/js/jquery-3.4.1.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/confirmation_style.css"/>">
    <title><fmt:message key="confirmation.page.welcomtitle"/></title>
</head>
<body>
    <header class="header">
        <div class="wrapper">
            <div class="header-row">
                <div class="logo">
                    <a class="site-logo">
                        <img src="<c:url value="/resources/img/logo-bsu-enrollee.png"/>" alt="logo"/>
                    </a>
                </div>
                <div class="user-description">
                    <div class="username">${requestScope.userFirstName} ${requestScope.userLastName}</div>
                    <div class="role">${requestScope.userRole}</div>
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
    <div class="container">
        <div class="confirmation">
            <i class="icon-box"><span class="glyphicon glyphicon-ok"></span></i>
            <h1><fmt:message key="confirmation.page.confirmation"/></h1>
            <p><fmt:message key="confirmation.page.notified.student"/></p>
        </div>
        <div class="goToFacultiesList">
            <form name="goToFacultiesListForm" method="POST" action="controller">
                <input type="hidden" name="command" value="back_To_Admin_Home">
                <input type="hidden" name="userFirstName" value="${requestScope.userFirstName}">
                <input type="hidden" name="userLastName" value="${requestScope.userLastName}">
                <input type="hidden" name="userRole" value="${requestScope.userRole}">
                <button class="logout-btn btn-back-to-faculty">
                    <fmt:message key="enrollees.back_to_faculty"/>
                </button>
            </form>
        </div>
    </div>
    <footer>
        <div class="bottom-footer">
            <div class="copyright"><fmt:message key="page.copyright"/> &#169; <script src="<c:url value="/resources/js/year.js"/>"></script>
            </div>
        </div>
    </footer>
</body>
</html>
