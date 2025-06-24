package com.catalog.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catalog.entities.Product;

public class ProductCatalogRepository {
	Connection connection;

	public List<Product> findAll(Connection connection){
		List<Product> productList = new ArrayList<>();
        String query = "SELECT no, product_name, type, price FROM catalog WHERE deleted = 0";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            
            while (result.next()) {
                productList.add(mapProduct(result));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get products", e);
        }
        return productList;
	}
	
	private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setNo(rs.getInt("no"));
        product.setName(rs.getString("product_name"));
        product.setType(rs.getString("type"));
        product.setPrice(rs.getDouble("price"));
        return product;
    }
	
	public void add(Connection connection, Product product) {
		String query = "INSERT INTO catalog (product_name, type, price) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query, 
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, product.getName());
            statement.setString(2, product.getType());
            statement.setDouble(3, product.getPrice());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setNo(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add product", e);
        }
	}
	
	public void update(Connection connection, Product product) {
		String query = "UPDATE catalog SET product_name = ?, type = ?, price = ? WHERE no = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getType());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getNo());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product", e);
        }
	}
	
	public void softDelete(int no, Connection connection) {
		String query = "UPDATE catalog SET deleted = 1 WHERE no = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, no);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete product", e);
        }
	}
}
