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
    <link rel="stylesheet" href="<c:url value="/resources/css/edit_enrollee_data.css"/>">
    <script src="<c:url value="/resources/js/editDataPage.js"/>"></script>
    <title><fmt:message key="user.page.welcomtitle"/></title>
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
        <div class="back-to-user-home">
            <form name="backToUserHomeForm" method="POST" action="controller">
                <input type="hidden" name="command" value="back_to_user_home">
                <input type="hidden" name="userFirstName" value="${requestScope.user.firstName}">
                <input type="hidden" name="userLastName" value="${requestScope.user.lastName}">
                <input type="hidden" name="userRole" value="${requestScope.userRole}">
                <input type="hidden" name="avatar" value="${requestScope.avatar}">
                <button class="back-to-user-home-btn" name="login" value="<c:out value="${requestScope.login}"/>">
                    <fmt:message key="edit.page.button.back.enrollee.home"/>
                </button>
            </form>
        </div>
        <div class="contact">
            <div class="form-style">
                <div class="attemptNotification">
                    <c:choose>
                        <c:when test="${requestScope.attempt == 0}">
                            <h3>${requestScope.userFirstName}<fmt:message key="edit.page.attempt.message.attempts_limit_exceeded"/></h3>
                            <input type="hidden" id="attemptNull" name="attemptNull" value="${requestScope.attempt}">
                        </c:when>
                        <c:otherwise>
                            <h3>${requestScope.userFirstName}<fmt:message key="edit.page.attempt.message.part1"/>&nbsp;</h3><h2 class="attempt" id="attempt">${requestScope.attempt}</h2><h3>&nbsp;<fmt:message key="edit.page.attempt.message.part2"/></h3>
                        </c:otherwise>
                    </c:choose>
                </div>
                <form name="editForm" method="POST" action="controller" onclick="validateForm()">
                    <input type="hidden" name="command" value="edit">
                    <input type="hidden" name="userFirstName" value="${requestScope.user.firstName}">
                    <input type="hidden" name="userLastName" value="${requestScope.user.lastName}">
                    <input type="hidden" name="userRole" value="${requestScope.userRole}">
                    <div class="accordion">
                        <div class="trigger">
                            <input type="checkbox" id="checkbox-1" name="checkbox-1" onclick="setChoosingInformation();"/>
                            <label for="checkbox-1" class="checkbox">
                                <fmt:message key="edit.page.change.message.personal"/><i></i>
                            </label>
                            <div class="content">
                                <h3><label for="first-name" class="label-from"><fmt:message key="register.page.completename"/></label></h3>
                                <input required type="text" id="first-name" name="firstNameToChange" placeholder="<fmt:message key="register.page.placeholder.completename"/>"/>
                                <h3><label for="last-name" class="label-to"><fmt:message key="register.page.surname"/></label></h3>
                                <input required type="text" id="last-name" name="lastNameToChange" placeholder="<fmt:message key="register.page.placeholder.surname"/>"/>
                                <h3><label for="middle-name" class="label-to"><fmt:message key="register.page.middlename"/></label></h3>
                                <input required type="text" id="middle-name" name="middleNameToChange" placeholder="<fmt:message key="register.page.placeholder.middlename"/>"/>
                                <div class="error" id="fullNameError"></div>
                                <h3><fmt:message key="register.page.birthday"/></h3>
                                <div class="birthday">
                                    <input id="month" required type="number" min="1" max="12" name="month" placeholder="<fmt:message key="register.page.birth_month"/>"/>
                                    <input id="day" required type="number" min="1" max="31" name="day" placeholder="<fmt:message key="register.page.birth_day"/>"/>
                                    <input id="year" required type="number" min="1900" name="year" placeholder="<fmt:message key="register.page.birth_year"/>"/>
                                </div>
                                <div class="error" id="birthdayError"></div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion">
                        <div class="trigger">
                            <input type="checkbox" id="checkbox-2" name="checkbox-2" onclick="setChoosingInformation()"/>
                            <label for="checkbox-2" id="label-checkbox-2" class="checkbox">
                                <fmt:message key="edit.page.change.message.educational"/><i></i>
                            </label>
                            <div class="content">
                                <div class="faculties-div">
                                    <h4><fmt:message key="register.page.faculty"/></h4>
                                    <label>
                                        <select id="faculties" class="select2-faculties" name="department" onChange="changeDepartmentValues(this.selectedIndex)">
                                            <option><fmt:message key="faculty.bio"/></option>
                                            <option><fmt:message key="faculty.his"/></option>
                                            <option><fmt:message key="faculty.ecolog"/></option>
                                            <option><fmt:message key="faculty.mmf"/></option>
                                            <option><fmt:message key="faculty.geo"/></option>
                                            <option><fmt:message key="faculty.journ"/></option>
                                            <option><fmt:message key="faculty.inter_relationship"/></option>
                                            <option><fmt:message key="faculty.fami"/></option>
                                            <option><fmt:message key="faculty.radiophys"/></option>
                                            <option><fmt:message key="faculty.philo_social"/></option>
                                            <option><fmt:message key="faculty.physics"/></option>
                                            <option><fmt:message key="faculty.philol"/></option>
                                            <option><fmt:message key="faculty.chem"/></option>
                                            <option><fmt:message key="faculty.econom"/></option>
                                            <option><fmt:message key="faculty.law"/></option>
                                        </select>
                                    </label>
                                </div>
                                <div class="specialities-div">
                                    <h4><fmt:message key="register.page.speciality"/></h4>
                                    <label>
                                        <select id="specialities" class="select2-specialities" name="speciality">
                                            <option value="N/A">N/A</option>
                                        </select>
                                    </label>
                                </div>
                               <input type="hidden" id="changeCase" name="changeCase" value=""/>
                            </div>
                        </div>
                    </div>
                    <script src="<c:url value="/resources/js/editDataPage.js"/>" charset="UTF-8"></script>
                    <button type="submit" class="submit" id="submit" name="login" value="${requestScope.login}"><fmt:message key="notification.page.submit"/></button>
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
