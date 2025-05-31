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
import java.util.ArrayList;
import java.util.List;
import model.Destinasi;

public class DestinasiDAO {
    
    public List<Destinasi> getAllDestinasi() {
        List<Destinasi> listDestinasi = new ArrayList<>();
        String sql = "SELECT * FROM destinasi";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while(rs.next()) {
                Destinasi d = new Destinasi();
                d.setId(rs.getInt("id"));
                d.setKotaId(rs.getInt("kota_id"));
                d.setNamaDestinasi(rs.getString("nama_destinasi"));
                d.setDeskripsi(rs.getString("deskripsi"));
                d.setHarga(rs.getBigDecimal("harga"));
                listDestinasi.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data destinasi: " + e.getMessage());
        }
        return listDestinasi;
    }
}
