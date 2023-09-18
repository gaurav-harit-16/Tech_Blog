package com.techBlog.helper;

import java.sql.*;

public class ConnectionProvider {

	private static Connection con;

    public static Connection getConnection() {

        try {
            if (con == null) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Correct class name
                String url = "jdbc:mysql://localhost:3306/techBlog";
                // Establish the connection
                con = DriverManager.getConnection(url, "Your_UserName", "Your_Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
