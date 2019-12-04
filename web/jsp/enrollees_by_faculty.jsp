<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/enrollees_by_faculty_style.css"/>">
    <title>Enrollees to change</title>
</head>
<body>
<%--    <jsp:include page="template/header-admin.jsp"/>--%>
    <header class="header">
    <div class="wrapper">
        <div class="header-row">
            <div class="logo">
                <a class="site-logo" href="<c:url value="/jsp/admin_home.jsp"/>">
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
    <div class="container">
        <div class="enrollees-info">
            <div class="goToFacultiesList">
                <a href="<c:url value="/controller?command=back_To_Admin_Home_Command"/>"><fmt:message key="enrollees.back_to_faculty"/></a>
            </div>
            <c:choose>
                <c:when test="${empty requestScope.enrolleesInformationMap}">
                    <div class="no-enrollees">
                        <div class="no-enrollees-description">
                            <fmt:message key="enrollees.no_enrollees_description"/>
                        </div>
                        <div class="form-show-enrolled">
                            <form name="enrolleedStudentsForm" enctype="multipart/form-data" method="POST" action="controller">
                                <input type="hidden" name="command" value="enrolledStudents"/>
                                <button class="show-enrolleed-students"><fmt:message key="enrollees.view_list_enrollees"/></button>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <form name="notificationForm" enctype="multipart/form-data" method="POST" action="controller">
                        <input type="hidden" name="command" value="notification"/>
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
                                        <div class="fullname"><h3>FirstName LastName TODO</h3></div>
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
                                                    <button class="notificate-enrollee"><fmt:message key="enrollees.notificate"/></button>
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
<%--    <jsp:include page="template/footer.jsp"/>--%>
    <footer>
        <div class="bottom-footer">
            <div class="copyright"><fmt:message key="page.copyright"/> &#169; <script src="<c:url value="/resources/js/year.js"/>"></script>
            </div>
        </div>
    </footer>
</body>
</html>
