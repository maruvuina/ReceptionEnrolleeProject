<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/resources/img/icons/favicon.ico"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/login_style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.4.1.js"/>"></script>
    <script src="<c:url value="/resources/js/loginFormValidation.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>"><link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendor/animate/animate.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendor/css-hamburgers/hamburgers.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendor/select2/select2.min.css"/>">
    <title>
        <fmt:message key="login.page.title"/>
    </title>
</head>
<body>
    <div class="container">
        <div class="container-login">
            <div class="choosed-lang">
                <form class="form-selected-lang">
                    <select id="language" name="language" onchange="submit()">
                        <option value="en_US" ${language == 'en_US' ? 'selected' : ''}><fmt:message key="login.page.language_type.english"/></option>
                        <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}><fmt:message key="login.page.language_type.russian"/></option>
                        <option value="be_BY" ${language == 'be_BY' ? 'selected' : ''}><fmt:message key="login.page.language_type.belarussian"/></option>
                    </select>
                </form>
            </div>
            <div class="wrap-login p-l-50 p-r-50 p-t-77 p-b-30">
                <form name="loginForm" enctype="multipart/form-data" method="POST" action="controller" class="login-form validate-form">
                    <input type="hidden" name="command" value="login"/>
                    <span class="login-form-title p-b-55">
                        <fmt:message key="login.page.form.title"/>
                    </span>
                    <div class="wrap-input validate-input m-b-16" data-validate = "Valid username is required: ex@abc.xyz">
                        <label for="login"></label>
                        <input class="input" id="login" required type="email" name="login" placeholder="<fmt:message key="login.page.label.username"/>"/>
                        <span class="focus-input"></span>
                        <span class="symbol-input">
                            <span class="lnr lnr-envelope"></span>
                        </span>
                    </div>
                    <div class="wrap-input validate-input m-b-16" data-validate = "Password is required">
                        <label for="password"></label>
                        <input class="input" id="password" required type="password" name="password" placeholder="<fmt:message key="login.page.label.password"/>"/>
                        <span class="focus-input"></span>
                        <span class="symbol-input">
                            <span class="lnr lnr-lock"></span>
                        </span>
                    </div>
                    <div class="sys-error">
                        <div class="error" id="emailError"></div>
                        <div class="error" id="passwordError"></div>
                        <div class="error">${requestScope.errorLoginPassMessage}</div>
                        <div class="error">${requestScope.wrongAction}</div>
                        <div class="error">${requestScope.nullPage}</div>
                        <div class="error">${requestScope.unknownUser}</div>
                    </div>
                    <div class="container-login-form-btn p-t-25">
                        <button type="submit" class="login-form-btn">
                            <fmt:message key="login.page.button.singin"/>
                        </button>
                    </div>
                </form>
                <div class="go-to-register">
                    <fmt:message key="login.page.button.registration" var="registerValue"/>
                    <a href="<c:url value="/controller?command=go_To_Register"/>">${registerValue}</a>
                </div>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/popper.js"/>"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/vendor/select2/select2.min.js"/>"></script>
</body>
</html>
