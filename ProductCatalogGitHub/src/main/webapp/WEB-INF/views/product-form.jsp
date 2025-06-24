<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${not empty product.no ? 'Edit' : 'Add'} Product</title>
</head>
<body>
    <h1>${not empty product.no ? 'Edit' : 'Add'} Product</h1>
    
    <form action="<c:url value='/products'/>" method="post">
        <c:if test="${not empty product.no}">
            <input type="hidden" name="no" value="${product.no}">
        </c:if>
        
        <div>
            <label>Product Name:</label>
            <input type="text" name="name" value="${product.name}" required>
        </div>
        
        <div>
            <label>Type:</label>
            <select name="type" required>
                <option value="">Select Type</option>
                <c:forEach items="${types}" var="typeOption">
                    <option value="${typeOption}" 
                        ${product.type eq typeOption ? 'selected' : ''}>
                        ${typeOption}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <div>
            <label>Price:</label>
            <input type="number" name="price" step="0.01" value="${product.price}" required>
        </div>
        
        <div>
            <button type="button" onclick="location.href='<c:url value='/products'/>'">Cancel</button>
            <button type="submit">Save</button>
        </div>
    </form>
</body>
</html>