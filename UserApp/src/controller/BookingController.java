/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Reservasi;

import dao.BookingDAO;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import model.PaketPerjalanan;
import model.Penumpang;
import model.Reservasi;

public class BookingController {
    
    private final BookingDAO dao;

    public BookingController() {
        this.dao = new BookingDAO();
    }
    
    public void processBooking(PaketPerjalanan paket, List<Penumpang> penumpangList) {
        // 1. Buat Objek Reservasi
        Reservasi reservasi = new Reservasi();
        reservasi.setTripType("paket_perjalanan");
        reservasi.setTripId(paket.getId());
        reservasi.setTanggalReservasi(new Date()); // Tanggal hari ini
        reservasi.setStatus("dipesan");
        // Buat kode reservasi unik
        reservasi.setKodeReservasi("SJT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // 2. Simpan Reservasi ke DB dan dapatkan ID-nya
        int reservasiId = dao.createReservasi(reservasi);
        
        if (reservasiId != -1) {
            // 3. Set ID reservasi untuk setiap penumpang
            for (Penumpang p : penumpangList) {
                p.setReservasiId(reservasiId);
            }
            
            // 4. Simpan semua data penumpang
            boolean isSuccess = dao.insertPenumpang(penumpangList);
            
            if (isSuccess) {
                JOptionPane.showMessageDialog(null, 
                    "Pemesanan Berhasil!\nKode Reservasi Anda: " + reservasi.getKodeReservasi(),
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                // TODO: Arahkan ke halaman konfirmasi atau riwayat perjalanan
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data penumpang.", "Error", JOptionPane.ERROR_MESSAGE);
                // TODO: Hapus reservasi yang sudah dibuat karena penumpangnya gagal disimpan (rollback)
            }
        } else {
            JOptionPane.showMessageDialog(null, "Gagal membuat reservasi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
