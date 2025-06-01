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
import java.sql.Statement;
import java.util.List;
import model.CustomTrip;
import model.RincianCustomTrip;

public class CustomTripDAO {

    public int insertCustomTrip(CustomTrip trip) {
        String sql = "INSERT INTO custom_trip (user_id, nama_trip, tanggal_mulai, tanggal_akhir, jumlah_peserta, status, total_harga, catatan_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (trip.getUserId() != null) {
                pstmt.setInt(1, trip.getUserId());
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setString(2, trip.getNamaTrip());
            pstmt.setDate(3, new java.sql.Date(trip.getTanggalMulai().getTime()));
            pstmt.setDate(4, new java.sql.Date(trip.getTanggalAkhir().getTime()));
            pstmt.setInt(5, trip.getJumlahPeserta()); // Perlu field jumlah peserta
            pstmt.setString(6, trip.getStatus());
            pstmt.setBigDecimal(7, trip.getTotalHarga());
            pstmt.setString(8, trip.getCatatanUser()); // Gabungkan semua catatan

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return generated ID
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat insert custom_trip: " + e.getMessage());
        }
        return -1; // Gagal
    }

    public boolean insertRincianCustomTrip(List<RincianCustomTrip> rincianList) {
        String sql = "INSERT INTO rincian_custom_trip (custom_trip_id, destinasi_id, tanggal_kunjungan, durasi_jam, urutan_kunjungan, harga_destinasi, biaya_transport) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (RincianCustomTrip rincian : rincianList) {
                pstmt.setInt(1, rincian.getCustomTripId());
                pstmt.setInt(2, rincian.getDestinasiId());
                pstmt.setDate(3, rincian.getTanggalKunjungan() != null ? new java.sql.Date(rincian.getTanggalKunjungan().getTime()) : null);
                pstmt.setInt(4, rincian.getDurasiJam());
                pstmt.setInt(5, rincian.getUrutanKunjungan());
                pstmt.setBigDecimal(6, rincian.getHargaDestinasi());
                pstmt.setBigDecimal(7, rincian.getBiayaTransport());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println("Error saat insert rincian_custom_trip: " + e.getMessage());
            return false;
        }
    }
}
