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
    <link rel="stylesheet" href="<c:url value="/resources/css/user_style.css"/>">
    <title><fmt:message key="user.page.welcomtitle"/></title>
</head>
<body>
    <header class="header">
        <div class="wrapper">
            <div class="header-row">
                <div class="logo">
                    <a class="site-logo" href="<c:url value="/jsp/user.jsp"/>">
                        <img src="<c:url value="/resources/img/logo-bsu-enrollee.png"/>" alt="logo"/>
                    </a>
                </div>
                <div class="user-description">
                    <div class="username">${requestScope.user.firstName} ${requestScope.user.lastName}</div>
                    <div class="role">${requestScope.userRole}</div>
                </div>
                <div class="logout">
                    <div class="user-avatar"><img src="data:image/jpg;base64, ${requestScope.avatar}" alt="user-avatar"/></div>
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
        <div class="enrollees-info">
            <ul class="enrollees">
                <li>
                    <div class="enrollee">
                        <div class="enrollee-info-img">
                            <div class="enrollee-img img-config">
                                <img src="data:image/jpg;base64, ${requestScope.avatar}" alt="user-avatar"/>
                            </div>
                        </div>
                        <div class="enrollee-info-fullname">
                            <div class="fullname">
                                <h3>${requestScope.user.firstName} ${requestScope.user.lastName}</h3>
                            </div>
                        </div>
                        <div class="enrollee-info-description">
                            <div class="user-info-row">
                                <div class="user-info-row-title"><fmt:message key="enrollee.place_of_residence"/></div>
                                <div class="user-info-row-discription">${requestScope.enrollee.district}, ${requestScope.enrollee.locality}</div>
                            </div>
                            <div class="user-info-row">
                                <div class="user-info-row-title"><fmt:message key="enrollee.birthday"/></div>
                                <div class="user-info-row-discription">${requestScope.enrollee.birthday}</div>
                            </div>
                            <div class="user-info-row">
                                <div class="user-info-row-title"><fmt:message key="enrollee.faculty"/></div>
                                <div class="user-info-row-discription">${requestScope.educationInformation.facultyName}</div>
                            </div>
                            <div class="user-info-row">
                                <div class="user-info-row-title"><fmt:message key="enrollee.speciality"/></div>
                                <div class="user-info-row-discription">${requestScope.educationInformation.specialityName}</div>
                            </div>
                            <div class="user-info-row">
                                <div class="text-score"><fmt:message key="enrollee.score"/></div>
                                <div class="user-info-row-discription score">${requestScope.educationInformation.enrolleeScore}</div>
                            </div>
                            <div class="user-info-row">
                                <div class="user-info-row-title"><fmt:message key="enrollee.ratingToAll"/></div>
                                <div class="user-info-row-discription">${requestScope.educationInformation.enrolleePosition}/${requestScope.educationInformation.enrolleesCount}</div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
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
