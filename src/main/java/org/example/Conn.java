package org.example;

import java.sql.*;

class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library", "root", "1234");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

