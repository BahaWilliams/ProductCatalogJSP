package com.catalog.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.catalog.entities.Product;
import com.catalog.repository.ProductCatalogRepository;
import com.catalog.utils.DBUtils;

public class ProductCatalogService {
	private final ProductCatalogRepository catalogRepository = new ProductCatalogRepository();
	
	public List<Product> findAll(){
		try (Connection connection = DBUtils.getConnection()) {
            return catalogRepository.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get products", e);
        }
	}
	
	public void add(Product product) {
        try (Connection connection = DBUtils.getConnection()) {
            connection.setAutoCommit(false);
            try {
                catalogRepository.add(connection, product);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add product", e);
        }
    }
    
    public void update(Product product) {
        try (Connection connection = DBUtils.getConnection()) {
            connection.setAutoCommit(false);
            try {
                catalogRepository.update(connection, product);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product", e);
        }
    }
    
    public void delete(int no) {
        try (Connection connection = DBUtils.getConnection()) {
            connection.setAutoCommit(false);
            try {
                catalogRepository.softDelete(no, connection);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete product", e);
        }
    }
}
