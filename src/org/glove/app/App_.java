package org.glove.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App_ {
    public static void main(String[] args) {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql::/localhost:3306/glovedb","root","1557");
    }
}
