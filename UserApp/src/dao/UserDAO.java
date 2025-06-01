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
import model.User; // Pastikan model User sudah ada dan sesuai
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    // Method insertUser yang sudah ada
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (nama_lengkap, email, password, no_telepon, alamat, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            pstmt.setString(1, user.getNamaLengkap());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, user.getNoTelepon());
            pstmt.setString(5, user.getAlamat());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saat insert user: " + e.getMessage());
            return false;
        }
    }

    // Method validateUser yang sudah ada
    public boolean validateUser(String email, String plainPassword) {
        String sql = "SELECT password FROM user WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    if (hashedPasswordFromDB != null) {
                        return BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat validasi user: " + e.getMessage());
        }
        return false;
    }

    // --- METHOD BARU UNTUK PROFIL ---
    /**
     * Mengambil data User berdasarkan email.
     * @param email Email pengguna yang dicari.
     * @return Objek User jika ditemukan, null jika tidak.
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT id, nama_lengkap, email, no_telepon, alamat, gambar FROM user WHERE email = ?"; // Tambahkan kolom 'gambar'
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNamaLengkap(rs.getString("nama_lengkap"));
                    user.setEmail(rs.getString("email"));
                    user.setNoTelepon(rs.getString("no_telepon"));
                    user.setAlamat(rs.getString("alamat"));
                    user.setGambarPath(rs.getString("gambar")); // <-- AMBIL PATH GAMBAR
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil user berdasarkan email: " + e.getMessage());
        }
        return user;
    }

    /**
     * Memperbarui data profil pengguna (kecuali password).
     * @param user Objek User dengan data baru. ID pengguna harus ada.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateUserProfile(User user) {
        // Tambahkan 'gambar = ?' ke query UPDATE
        String sql = "UPDATE user SET nama_lengkap = ?, no_telepon = ?, alamat = ?, gambar = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getNamaLengkap());
            pstmt.setString(2, user.getNoTelepon());
            pstmt.setString(3, user.getAlamat());
            pstmt.setString(4, user.getGambarPath()); // <-- SIMPAN PATH GAMBAR
            pstmt.setInt(5, user.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saat update profil user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Memperbarui password pengguna.
     * @param userId ID pengguna.
     * @param newPassword Password baru yang belum di-hash.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE user SET password = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saat update password user: " + e.getMessage());
            return false;
        }
    }
}