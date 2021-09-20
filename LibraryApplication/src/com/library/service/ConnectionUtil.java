package com.library.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static final String driver = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/library";
    public static final String username = "aabraha";
    public static final String password = "Aa212730!";

    public Connection getConnetion() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,username,password);
        conn.setAutoCommit(Boolean.FALSE);
        return conn;
    }

}
