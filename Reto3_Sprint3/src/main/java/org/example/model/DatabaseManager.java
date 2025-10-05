package org.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Sprint3";
    private static final String USER = "root";
    private static final String PASS = "Luzoscura0531";


    public Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}