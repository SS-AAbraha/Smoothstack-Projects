package com.library.dao;

import java.sql.*;
import java.util.List;

public abstract class BaseDAO<T> {

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/library";
    private String username = "aabraha";
    private String password = "Aa212730!";


    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }

    public void Save(String sql, Object[] vals) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        if(vals != null) {
            int count = 1;
            for (Object val : vals) {
                pstmt.setObject(count, val);
                count++;
            }
        }
        pstmt.execute();    // executeUpdate?
    }

    public List<T> Read(String sql, Object[] vals) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        if(vals!=null){
            int count = 1;
            for (Object val : vals) {
                pstmt.setObject(count++, val);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        return ExtractData(rs);
    }

    public abstract List<T> ExtractData(ResultSet rs) throws SQLException;

}
