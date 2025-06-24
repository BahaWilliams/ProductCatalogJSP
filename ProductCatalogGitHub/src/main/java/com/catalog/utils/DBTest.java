package com.catalog.utils;

import java.sql.Connection;

public class DBTest {
	public static void main(String[] args) {
        Connection conn = DBUtils.getConnection();

        if (conn != null) {
            System.out.println("✅ Connection successful!");
        } else {
            System.out.println("❌ Failed to connect.");
        }
    }
}
