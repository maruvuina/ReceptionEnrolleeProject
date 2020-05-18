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
    <link rel="stylesheet" href="<c:url value="/resources/css/enrollees_by_faculty_style.css"/>">
    <title><fmt:message key="enrollees.title.by_faculty"/></title>
</head>
<body>
    <header class="header">
    <div class="wrapper">
        <div class="header-row">
            <div class="logo">
                <a class="site-logo" href="<c:url value="/jsp/admin/enrollees_by_faculty.jsp"/>">
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
        <div class="enrollees-info">
            <div class="goToFacultiesList">
                <form name="goToFacultiesListForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="back_To_Admin_Home">
                    <input type="hidden" name="userFirstName" value="${requestScope.userFirstName}">
                    <input type="hidden" name="userLastName" value="${requestScope.userLastName}">
                    <input type="hidden" name="userRole" value="${requestScope.userRole}">
                    <div class="faculty-info">
                        <button class="logout-btn btn-back-to-faculty">
                            <fmt:message key="enrollees.back_to_faculty"/>
                        </button>
                        <div class="facultyName">
                            <div>${requestScope.facultyName}</div>
                        </div>
                    </div>
                </form>
            </div>
            <c:choose>
                <c:when test="${empty requestScope.enrolleesInformationMap}">
                    <div class="no-enrollees">
                        <div class="no-enrollees-description">
                            <fmt:message key="enrollees.no_enrollees_description"/>
                        </div>
                        <div class="form-show-enrolled">
                            <form name="enrolleedStudentsForm" enctype="multipart/form-data" method="POST" action="controller">
                                <input type="hidden" name="command" value="enrolled_Students"/>
                                <input type="hidden" name="userFirstName" value="${requestScope.userFirstName}">
                                <input type="hidden" name="userLastName" value="${requestScope.userLastName}">
                                <input type="hidden" name="userRole" value="${requestScope.userRole}">
                                <button type="submit" class="show-enrolled-students" name="enrollee-faculty" value="${requestScope.facultyName}"><fmt:message key="enrollees.view_list_enrollees"/></button>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <form name="notificationForm" enctype="multipart/form-data" method="POST" action="controller">
                        <input type="hidden" name="command" value="notification"/>
                        <input type="hidden" name="userFirstName" value="${requestScope.userFirstName}">
                        <input type="hidden" name="userLastName" value="${requestScope.userLastName}">
                        <input type="hidden" name="userRole" value="${requestScope.userRole}">
                        <ul class="enrollees">
                            <c:forEach var="enrollees" items="${requestScope.enrolleesInformationMap}">
                            <li>
                                <div class="enrollee">
                                    <div class="enrollee-info-img">
                                        <div class="enrollee-img img-config">
                                            <img src="data:image/jpg;base64, <c:out value="${enrollees.key.avatar}"/>" alt="enrollee-avatar"/>
                                        </div>
                                    </div>
                                    <div class="enrollee-info-fullname">
                                        <div class="fullname"><h3><c:out value="${enrollees.value.enrolleeFirstName}"/> <c:out value="${enrollees.value.enrolleeLastName}"/></h3></div>
                                        <div class="enrollee-info-description">
                                            <div class="user-info-row">
                                                <div class="user-info-row-title"><fmt:message key="enrollee.faculty"/></div>
                                                <div class="user-info-row-discription"><c:out value="${enrollees.value.facultyName}"/></div>
                                            </div>
                                            <div class="user-info-row">
                                                <div class="user-info-row-title"><fmt:message key="enrollee.speciality"/></div>
                                                <div class="user-info-row-discription"><c:out value="${enrollees.value.specialityName}"/></div>
                                            </div>
                                            <div class="user-info-row">
                                                <div class="text-score"><fmt:message key="enrollee.score"/></div>
                                                <div class="user-info-row-discription score"><c:out value="${enrollees.value.enrolleeScore}"/></div>
                                            </div>
                                            <div class="user-info-row">
                                                <div class="user-info-row-title"><fmt:message key="enrollee.ratingToAll"/></div>
                                                <div class="user-info-row-discription"><c:out value="${enrollees.value.enrolleePosition}"/> / <c:out value="${enrollees.value.enrolleesCount}"/></div>
                                            </div>
                                            <div class="user-info-row">
                                                <div class="user-info-row-title"><fmt:message key="enrollee.ratingToFaculty"/></div>
                                                <div class="user-info-row-discription"><c:out value="${enrollees.value.enrolleePosition}"/> / <c:out value="${enrollees.value.facultyPlan}"/></div>
                                            </div>
                                            <div class="user-info-row">
                                                <div class="user-info-row-title">
                                                    <button type="submit" class="notificate-enrollee" name="enrolleeEmail" value="<c:out value="${enrollees.value.enrolleeEmail}"/>"><fmt:message key="enrollees.notificate"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>
                    </form>
                </c:otherwise>
            </c:choose>
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
