/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BookingDAO;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.PaketPerjalanan;
import model.Penumpang;
import model.Reservasi;
import view.HomeView; // Untuk kembali ke home setelah booking sukses

public class BookingController {
    
    private final BookingDAO bookingDAO;

    public BookingController() {
        this.bookingDAO = new BookingDAO();
    }
    
    /**
     * Memproses pemesanan untuk Paket Perjalanan.
     * @param paket Paket Perjalanan yang dipesan.
     * @param jumlahOrang Jumlah orang/penumpang.
     * @param penumpangList Daftar objek Penumpang.
     * @param frameAsal Frame asal (misal BookingView) untuk ditutup setelah sukses.
     */
    public void processPaketBooking(PaketPerjalanan paket, int jumlahOrang, List<Penumpang> penumpangList, JFrame frameAsal) {
        // Validasi dasar
        if (penumpangList == null || penumpangList.isEmpty()) {
            JOptionPane.showMessageDialog(frameAsal, "Data penumpang tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Penumpang p : penumpangList) {
            if (p.getNamaPenumpang() == null || p.getNamaPenumpang().trim().isEmpty() ||
                p.getJenisKelamin() == null || p.getTanggalLahir() == null) {
                JOptionPane.showMessageDialog(frameAsal, "Harap lengkapi semua data wajib untuk setiap penumpang (Nama, Jenis Kelamin, Tgl Lahir).", "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 1. Buat Objek Reservasi
        Reservasi reservasi = new Reservasi();
        reservasi.setTripType("paket_perjalanan"); // Tandai sebagai paket perjalanan
        reservasi.setTripId(paket.getId());
        reservasi.setTanggalReservasi(new Date()); // Tanggal hari ini
        reservasi.setStatus("dipesan"); // Status awal
        // Buat kode reservasi unik (bisa diperbaiki agar lebih robust)
        reservasi.setKodeReservasi("SJP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        // 2. Simpan Reservasi ke DB dan dapatkan ID-nya
        int reservasiId = bookingDAO.createReservasi(reservasi);
        
        if (reservasiId != -1) {
            // 3. Set ID reservasi untuk setiap penumpang
            for (Penumpang p : penumpangList) {
                p.setReservasiId(reservasiId);
            }
            
            // 4. Simpan semua data penumpang
            boolean isPenumpangSaved = bookingDAO.insertPenumpang(penumpangList);
            
            if (isPenumpangSaved) {
                JOptionPane.showMessageDialog(frameAsal, 
                    "Pemesanan Berhasil!\nKode Reservasi Anda: " + reservasi.getKodeReservasi() +
                    "\nAnda akan diarahkan kembali ke Halaman Utama.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                if (frameAsal != null) {
                    frameAsal.dispose(); // Tutup jendela booking
                }
                new HomeView().setVisible(true); // Kembali ke Home
                
            } else {
                JOptionPane.showMessageDialog(frameAsal, "Gagal menyimpan data penumpang. Silakan coba lagi.", "Error Database", JOptionPane.ERROR_MESSAGE);
                // TODO: Idealnya, implementasikan mekanisme rollback untuk menghapus reservasi yang sudah dibuat jika penumpang gagal disimpan.
            }
        } else {
            JOptionPane.showMessageDialog(frameAsal, "Gagal membuat reservasi. Silakan coba lagi.", "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
}