<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>404 error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/error.css"/>">
</head>
<body>
    <div id="main">
        <div class="fof">
            <h1>Error 404</h1>
            <h2>Request from ${pageContext.errorData.requestURI} is failed</h2>
            <h2>Servlet name: ${pageContext.errorData.servletName}</h2>
            <h2>Status code: ${pageContext.errorData.statusCode}</h2>
            <h2>Exception: ${pageContext.exception}</h2>
            <h2>Message from exception: ${pageContext.exception.message}</h2>
            <h3><a href="${pageContext.request.contextPath}/index.jsp">Back to home</a></h3>
        </div>
    </div>
</body>
</html>
