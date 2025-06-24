<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${not empty product.no ? 'Edit' : 'Add'} Product</title>
</head>
<body>
    <h1>${not empty product.no ? 'Edit' : 'Add'} Product</h1>
    
    <form action="<c:url value='/products'/>" method="post"> <!-- Hapus "/mvc" -->
        <c:if test="${not empty product.no}">
            <input type="hidden" name="no" value="${product.no}">
            <input type="hidden" name="action" value="update">
        </c:if>
        <c:if test="${empty product.no}">
            <input type="hidden" name="action" value="save">
        </c:if>
        
        <div>
            <label>Product Name:</label>
            <input type="text" name="name" value="${product.name}" required>
        </div>
        
        <div>
            <label>Type:</label>
            <select name="type" required>
                <option value="">Select Type</option>
                <option value="Buku" ${product.type == 'Buku' ? 'selected' : ''}>Buku</option>
                <option value="Elektronik" ${product.type == 'Elektronik' ? 'selected' : ''}>Elektronik</option>
                <option value="Fashion" ${product.type == 'Fashion' ? 'selected' : ''}>Fashion</option>
                <option value="Handphone & Tablet" ${product.type == 'Handphone & Tablet' ? 'selected' : ''}>Handphone & Tablet</option>
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