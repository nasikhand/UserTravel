/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_app";
    private static final String USER = "root"; // Sesuaikan dengan username DB Anda
    private static final String PASSWORD = ""; // Sesuaikan dengan password DB Anda

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            // Cek jika koneksi null ATAU sudah ditutup
            if (connection == null || connection.isClosed()) {
                // Buat koneksi baru jika diperlukan
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi ke Database Gagal: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return connection;
    }
}