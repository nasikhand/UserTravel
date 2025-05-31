/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Reservasi;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.Penumpang;
import model.Reservasi;

public class BookingDAO {

    /**
     * Menyimpan data reservasi baru dan mengembalikan ID yang baru dibuat.
     * @param reservasi Objek Reservasi yang akan disimpan.
     * @return ID dari reservasi yang baru dibuat, atau -1 jika gagal.
     */
    public int createReservasi(Reservasi reservasi) {
        String sql = "INSERT INTO reservasi (trip_type, trip_id, kode_reservasi, tanggal_reservasi, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, reservasi.getTripType());
            pstmt.setInt(2, reservasi.getTripId());
            pstmt.setString(3, reservasi.getKodeReservasi());
            pstmt.setDate(4, new java.sql.Date(reservasi.getTanggalReservasi().getTime()));
            pstmt.setString(5, reservasi.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Mengembalikan ID yang di-generate
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat membuat reservasi: " + e.getMessage());
        }
        return -1; // Gagal
    }
    
    /**
     * Menyimpan daftar penumpang untuk sebuah reservasi.
     * @param penumpangList Daftar objek Penumpang.
     * @return true jika semua berhasil disimpan, false jika ada yang gagal.
     */
    public boolean insertPenumpang(List<Penumpang> penumpangList) {
        String sql = "INSERT INTO penumpang (reservasi_id, nama_penumpang, jenis_kelamin, tanggal_lahir, nomor_telepon, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (Penumpang p : penumpangList) {
                pstmt.setInt(1, p.getReservasiId());
                pstmt.setString(2, p.getNamaPenumpang());
                pstmt.setString(3, p.getJenisKelamin());
                // Konversi java.util.Date ke java.sql.Date
                pstmt.setDate(4, new java.sql.Date(p.getTanggalLahir().getTime()));
                pstmt.setString(5, p.getNomorTelepon());
                pstmt.setString(6, p.getEmail());
                pstmt.addBatch(); // Tambahkan ke batch
            }
            
            int[] results = pstmt.executeBatch(); // Eksekusi semua query sekaligus
            
            // Cek apakah semua batch berhasil
            for (int result : results) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error saat insert penumpang: " + e.getMessage());
            return false;
        }
    }
}
