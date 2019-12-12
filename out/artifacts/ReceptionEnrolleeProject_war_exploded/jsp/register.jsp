<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/register_style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.4.1.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/select2-4.0.10/css/select2.min.css"/>">
    <script src="<c:url value="/resources/select2-4.0.10/js/select2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/fileinput.js"/>"></script>
    <script src="<c:url value="/resources/js/ready.js"/>"></script>
    <script src="<c:url value="/resources/js/registerFormValidation.js"/>"></script>
    <title>
        <fmt:message key="register.page.title"/>
    </title>
</head>
<body>
    <div class="form-style">
        <form name="registerForm" enctype="multipart/form-data" method="POST" action="controller">
            <input type="hidden" name="command" value="register"/>
             <fieldset>
                <legend><span class="number">1</span><fmt:message key="register.page.empl_info"/></legend>
                <div class="row">
                    <div class="left-column-enrollee"><label for="completename"><fmt:message key="register.page.completename"/></label></div>
                    <div class="right-column-enrollee"><input id="completename" required type="text" name="completename" placeholder="<fmt:message key="register.page.placeholder.completename"/>"/></div>
                </div>
                <div class="row">
                    <div class="left-column-enrollee"><label for="surname"><fmt:message key="register.page.surname"/></label></div>
                    <div class="right-column-enrollee"><input id="surname" required type="text" name="surname" placeholder="<fmt:message key="register.page.placeholder.surname"/>"/></div>
                </div>
                <div class="row">
                    <div class="left-column-enrollee"><label for="middlename"><fmt:message key="register.page.middlename"/></label></div>
                    <div class="right-column-enrollee"><input id="middlename" required type="text" name="middlename" placeholder="<fmt:message key="register.page.placeholder.middlename"/>"/></div>
                </div>
                 <div class="sys-error">
                     <div class="error" id="errorFullname"></div>
                     <div class="error">${requestScope.invalidateRegisterFields}</div>
                 </div>
            </fieldset>
            <fieldset>
                <legend><span class="number">2</span><fmt:message key="register.page.user_info"/></legend>
                <label>
                    <input id="login_reg" required type="email" name="login" placeholder="<fmt:message key="register.page.placeholder.email"/>">
                </label>
                <div class="sys-error">
                    <div class="error" id="emailError"></div>
                    <div>${requestScope.errorEmailExistsMessage}</div>
                </div>
                <label>
                    <input id="password_reg" required type="text" name="password" placeholder="<fmt:message key="register.page.pass"/>"/>
                </label>
                <div class="sys-error">
                    <div class="error" id="passwordError"></div>
                </div>
            </fieldset>
            <div class="container-register-form-btn">
                <button class="register-form-btn"><fmt:message key="register.page.register"/></button>
            </div>
        </form>
    </div>
</body>
</html>