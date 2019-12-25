<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="icon" type="image/png" href="<c:url value="/resources/img/icons/favicon.ico"/>">
    <script src="<c:url value="/resources/js/selectOnlyThisCheckbox.js"/>" charset="UTF-8"></script>
    <script src="<c:url value="/resources/js/notificationFormValidation.js"/>" charset="UTF-8"></script>
    <script src="<c:url value="/resources/js/jquery-3.4.1.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/notification_style.css"/>">
    <title><fmt:message key="enrollees.notificate"/></title>
</head>
<body>
    <header class="header">
        <div class="wrapper">
            <div class="header-row">
                <div class="logo">
                    <a class="site-logo" href="<c:url value="/jsp/admin/notificate-enrollee.jsp"/>">
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
        <div class="contact">
            <div class="enrollee-info">
                <div class="enrollee-info-row">
                    <div class="enrollee-full-name"><h2><fmt:message key="notification.page.enrollee"/></h2></div>
                    <div class="enrollee-info-row-full-name">${requestScope.educationInformation.enrolleeFirstName} ${requestScope.educationInformation.enrolleeLastName}</div>
                </div>
                <div class="enrollee-info-row">
                    <div class="enrollee-faculty"><h2><fmt:message key="enrollee.faculty"/></h2></div>
                    <div class="enrollee-info-row-faculty">${requestScope.educationInformation.facultyName}</div>
                </div>
                <div class="enrollee-info-row">
                    <div class="enrollee-speciality"><h2><fmt:message key="enrollee.speciality"/></h2></div>
                    <div class="enrollee-info-row-speciality">${requestScope.educationInformation.specialityName}</div>
                </div>
            </div>
            <div class="form-style">
                <form name="contactForm" method="POST" action="controller" onclick="validateForm()">
                    <input type="hidden" name="command" value="contact">
                    <input type="hidden" name="userFirstName" value="${requestScope.userFirstName}">
                    <input type="hidden" name="userLastName" value="${requestScope.userLastName}">
                    <input type="hidden" name="userRole" value="${requestScope.userRole}">
                    <h3><label for="admin-email" class="label-from"><fmt:message key="notification.page.label.admin_email"/></label></h3>
                    <input required type="email" id="admin-email" name="adminEmail" placeholder="<fmt:message key="notification.page.placeholder.admin_email"/>"/>
                    <div class="error" id="adminEmailError"></div>
                    <h3><label for="enrollee-email" class="label-to"><fmt:message key="notification.page.label.enrollee_email"/></label></h3>
                    <input required type="email" id="enrollee-email" name="enrolleeEmail" value="${requestScope.enrolleeEmail}"/>
                    <div class="error" id="enrolleeEmailError"></div>
                    <div class="enrollee-status">
                        <div class="status">
                            <h4><label for="check1" class="label-to checkbox"><fmt:message key="notification.page.status.possitive"/></label></h4>
                            <input type="checkbox" id="check1" class="enrolleeEntered" name="enrolleeEntered" value="1" onclick="selectOnlyThis(this.id)">
                        </div>
                        <div class="status">
                            <h4><label for="check2" class="label-to checkbox"><fmt:message key="notification.page.status.negative"/></label></h4>
                            <input type="checkbox" id="check2" class="enrolleeNotEntered" name="enrolleeNotEntered" value="0" onclick="selectOnlyThis(this.id)" checked="checked">
                        </div>
                    </div>
                    <h3><label for="subject" class="label-subject"><fmt:message key="notification.page.label.subject"/></label></h3>
                    <input type="text" id="subject" name="subject" placeholder="<fmt:message key="notification.page.placeholder.subject"/>"/>
                    <textarea name="message" placeholder="<fmt:message key="notification.page.placeholder.message"/>"></textarea>
                    <button type="submit" class="submit" id="submit" name="enrolleeStatus" value=""><fmt:message key="notification.page.submit"/></button>
                </form>
            </div>
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
