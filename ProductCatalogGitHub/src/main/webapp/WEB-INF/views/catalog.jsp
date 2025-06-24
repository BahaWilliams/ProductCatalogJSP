<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Catalog</title>
    <script type="text/javascript">
        function confirmDelete(no, name) {
            if (confirm('Are you sure you want to delete "' + name + '"?')) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '<c:url value="/products"/>';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'delete';
                form.appendChild(actionInput);
                
                const noInput = document.createElement('input');
                noInput.type = 'hidden';
                noInput.name = 'no';
                noInput.value = no;
                form.appendChild(noInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>
<body>
    <h1>Product Catalog</h1>
    
    <div>
        <button onclick="location.href='<c:url value="/products?action=new"/>'">Add Product</button>
        <button onclick="location.href='<c:url value="/products?action=download"/>'">Download CSV</button>
    </div>
    
    <table border="1">
        <tr>
            <th>No</th>
            <th>Product Name</th>
            <th>Type</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.no}</td>
                <td>${product.name}</td>
                <td>${product.type}</td>
                <td>${product.price}</td>
                <td>
                    <a href="<c:url value="/products?action=edit&no=${product.no}"/>">Edit</a>
                    <button onclick="confirmDelete(${product.no}, '${product.name}')">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>