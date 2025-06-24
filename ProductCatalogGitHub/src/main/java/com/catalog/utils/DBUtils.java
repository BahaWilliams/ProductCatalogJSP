package com.catalog.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private static final String dbUrl = "jdbc:mysql://localhost:3306/product_catalog";
	private static final String dbUser = "root";
	private static final String dbPassword = "";
    
    public static Connection getConnection() {
    	Connection connection = null;
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connection;
    }
}