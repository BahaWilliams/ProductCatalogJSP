package com.catalog.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.catalog.entities.Product;
import com.catalog.entities.ProductType;
import com.catalog.service.ProductCatalogService;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductCatalogService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductCatalogService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if ("new".equals(action)) {
                showForm(request, response, null);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("download".equals(action)) {
                downloadCSV(response);
            } else {
                listProducts(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if ("delete".equals(action)) {
                deleteProduct(request, response);
            } else {
                saveProduct(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Product> products = productService.findAll();
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/catalog.jsp");
        dispatcher.forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Product product) 
            throws ServletException, IOException {
        
        List<String> types = ProductType.getTypes();
        request.setAttribute("types", types);
        
        if (product != null) {
            request.setAttribute("product", product);
        }
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int no = Integer.parseInt(request.getParameter("no"));
            Product product = productService.findById(no);
            showForm(request, response, product);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid product number", e);
        }
    }

    private void saveProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setType(request.getParameter("type"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        
        String noParam = request.getParameter("no");
        if (noParam != null && !noParam.isEmpty()) {
            product.setNo(Integer.parseInt(noParam));
            productService.update(product);
        } else {
            productService.add(product);
        }
        
        response.sendRedirect("products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int no = Integer.parseInt(request.getParameter("no"));
        productService.delete(no);
        response.sendRedirect("products");
    }

    private void downloadCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");
        
        List<Product> products = productService.findAll();
        StringBuilder csv = new StringBuilder("No,Product Name,Type,Price\n");
        
        for (Product p : products) {
            csv.append(p.getNo()).append(",")
               .append(p.getName()).append(",")
               .append(p.getType()).append(",")
               .append(p.getPrice()).append("\n");
        }
        
        response.getWriter().write(csv.toString());
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        request.setAttribute("errorMessage", "Error: " + e.getMessage());
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}