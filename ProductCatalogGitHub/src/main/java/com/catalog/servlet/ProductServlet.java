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
        
        if ("new".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("download".equals(action)) {
            downloadCSV(response);
        } else {
            listProducts(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        } else {
            listProducts(request, response);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<Product> products = productService.findAll();
            request.setAttribute("products", products);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load products");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/catalog.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int no = Integer.parseInt(request.getParameter("no"));
            Product product = new Product();
            product.setNo(no);
            request.setAttribute("product", product);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid product");
        }
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }

    private void saveProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Product product = new Product();
            product.setName(request.getParameter("name"));
            product.setType(request.getParameter("type"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            productService.add(product);
            request.getSession().setAttribute("message", "Product added");
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error adding product");
        }
        
        request.getSession().setAttribute("message", "Product added successfully!");
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Product product = new Product();
            product.setNo(Integer.parseInt(request.getParameter("no")));
            product.setName(request.getParameter("name"));
            product.setType(request.getParameter("type"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            productService.update(product);
            request.getSession().setAttribute("message", "Product updated");
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error updating product");
        }
        
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int no = Integer.parseInt(request.getParameter("no"));
            productService.delete(no);
            request.getSession().setAttribute("message", "Product deleted");
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error deleting product");
        }
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void downloadCSV(HttpServletResponse response) throws IOException {
        try {
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
        } catch (Exception e) {
            response.sendRedirect("mvc/product");
        }
    }
}