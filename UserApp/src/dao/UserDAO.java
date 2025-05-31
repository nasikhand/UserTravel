/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    public boolean insertUser(User user) {
        // --- DEBUGGING REGISTRASI ---
        System.out.println("--- PROSES REGISTRASI DIMULAI ---");
        System.out.println("DAO (Register): Menerima email: '" + user.getEmail() + "'");
        System.out.println("DAO (Register): Menerima password mentah: '" + user.getPassword() + "'");
        // -----------------------------

        String sql = "INSERT INTO user (nama_lengkap, email, password, no_telepon, alamat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            
            // --- DEBUGGING HASH ---
            System.out.println("DAO (Register): Password setelah di-hash: '" + hashedPassword + "'");
            // ------------------------

            pstmt.setString(1, user.getNamaLengkap());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, user.getNoTelepon());
            pstmt.setString(5, user.getAlamat());

            int affectedRows = pstmt.executeUpdate();
            System.out.println("DAO (Register): Proses insert selesai, baris terpengaruh: " + affectedRows);
            System.out.println("------------------------------------");
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saat insert user: " + e.getMessage());
            System.out.println("------------------------------------");
            return false;
        }
    }

    public boolean validateUser(String email, String plainPassword) {
        // --- DEBUGGING LOGIN ---
        System.out.println("--- PROSES LOGIN DIMULAI ---");
        System.out.println("DAO (Login): Validasi untuk email: '" + email + "'");
        System.out.println("DAO (Login): Menerima password mentah: '" + plainPassword + "'");
        // -------------------------

        String sql = "SELECT password FROM user WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    
                    // --- DEBUGGING VALIDASI ---
                    System.out.println("DAO (Login): Hash dari DB ditemukan: '" + hashedPasswordFromDB + "'");
                    
                    boolean isPasswordMatch = BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
                    
                    System.out.println("DAO (Login): Hasil perbandingan BCrypt: " + isPasswordMatch);
                    System.out.println("--------------------------------");
                    return isPasswordMatch;
                    // ----------------------------
                } else {
                    System.out.println("DAO (Login): Email tidak ditemukan di database.");
                    System.out.println("--------------------------------");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat validasi user: " + e.getMessage());
            System.out.println("--------------------------------");
        }
        return false;
    }
}