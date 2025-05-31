/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import java.math.BigDecimal;
import model.PaketPerjalanan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaketPerjalananDAO {

    public List<PaketPerjalanan> searchAvailablePackages(String destination) {
        List<PaketPerjalanan> packages = new ArrayList<>();
        // Query sederhana untuk mencari paket yang 'tersedia' berdasarkan nama kota
        // TODO: Tambahkan kriteria lain seperti tanggal jika diperlukan
        String sql = "SELECT p.* FROM paket_perjalanan p JOIN kota k ON p.kota_id = k.id WHERE k.nama_kota LIKE ? AND p.status = 'tersedia'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + destination + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PaketPerjalanan pkg = new PaketPerjalanan();
                    pkg.setId(rs.getInt("id"));
                    pkg.setKotaId(rs.getInt("kota_id"));
                    pkg.setNamaPaket(rs.getString("nama_paket"));
                    pkg.setTanggalMulai(rs.getDate("tanggal_mulai"));
                    pkg.setTanggalAkhir(rs.getDate("tanggal_akhir"));
                    pkg.setKuota(rs.getInt("kuota"));
                    pkg.setHarga(rs.getBigDecimal("harga"));
                    pkg.setDeskripsi(rs.getString("deskripsi"));
                    pkg.setStatus(rs.getString("status"));
                    // pkg.setGambar(rs.getString("gambar")); // Jika ada kolom gambar
                    packages.add(pkg);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari paket perjalanan: " + e.getMessage());
        }
        return packages;
    }
}
