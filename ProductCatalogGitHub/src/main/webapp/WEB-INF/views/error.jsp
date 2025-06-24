<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p>${errorMessage}</p>
    <a href="<c:url value='/products'/>">Back to Product Catalog</a>
</body>
</html>