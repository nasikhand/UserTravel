/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BookingDAO; // Asumsi method getReservasiByUserId ada di sini atau di ReservasiDAO terpisah
import dao.PaketPerjalananDAO;
import dao.CustomTripDAO;
import model.Reservasi;
import model.PaketPerjalanan;
import model.CustomTrip;
import model.User; // Untuk mendapatkan ID user
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class HistoryController {
    private BookingDAO bookingDAO; // Atau ReservasiDAO
    private PaketPerjalananDAO paketPerjalananDAO;
    private CustomTripDAO customTripDAO;
    // private UserDAO userDAO; // Jika user_id perlu diambil dulu

    public HistoryController() {
        this.bookingDAO = new BookingDAO(); // Inisialisasi DAO yang sesuai
        this.paketPerjalananDAO = new PaketPerjalananDAO();
        this.customTripDAO = new CustomTripDAO();
        // this.userDAO = new UserDAO();
    }

    /**
     * Mengambil semua data riwayat perjalanan untuk pengguna tertentu.
     * Kita akan asumsikan user_id bisa didapatkan dari email pengguna yang login.
     * Untuk saat ini, kita akan buat dummy implementasi karena tabel reservasi
     * belum secara eksplisit menyimpan user_id di contoh kode DAO sebelumnya.
     * Ini perlu disesuaikan dengan struktur database Anda.
     */
    public List<Map<String, Object>> getRiwayatPerjalanan(String emailPenggunaLogin) {
        List<Map<String, Object>> daftarRiwayat = new ArrayList<>();

        // Langkah 1: Dapatkan User ID dari email (jika diperlukan dan belum ada)
        // User currentUser = userDAO.getUserByEmail(emailPenggunaLogin);
        // if (currentUser == null) return daftarRiwayat; // Tidak ada user, tidak ada riwayat
        // int userId = currentUser.getId();

        // Langkah 2: Ambil semua reservasi. Untuk contoh, kita ambil semua.
        // Idealnya: List<Reservasi> reservasiList = bookingDAO.getReservasiByUserId(userId);
        // Karena BookingDAO kita belum punya getReservasiByUserId dan reservasi belum ada user_id,
        // kita akan buat contoh data statis untuk demonstrasi UI.
        // Saat implementasi nyata, Anda akan mengambil ini dari database.

        // Contoh Data Statis (GANTI DENGAN PENGAMBILAN DATA DB ASLI)
        // Contoh 1: Paket Perjalanan
        Map<String, Object> riwayat1 = new HashMap<>();
        riwayat1.put("tipe", "Paket Perjalanan");
        riwayat1.put("namaTrip", "Explore Bali 3H2M");
        riwayat1.put("tanggal", "10 Agu 2025 - 12 Agu 2025");
        riwayat1.put("kodeReservasi", "SJP-BALI001");
        riwayat1.put("status", "Selesai");
        riwayat1.put("totalHarga", "Rp 2.500.000");
        daftarRiwayat.add(riwayat1);

        // Contoh 2: Custom Trip
        Map<String, Object> riwayat2 = new HashMap<>();
        riwayat2.put("tipe", "Custom Trip");
        riwayat2.put("namaTrip", "Petualangan Yogyakarta Saya");
        riwayat2.put("tanggal", "15 Sep 2025 - 18 Sep 2025");
        riwayat2.put("kodeReservasi", "SJC-JOGJA002");
        riwayat2.put("status", "Dipesan");
        riwayat2.put("totalHarga", "Rp 1.850.000");
        daftarRiwayat.add(riwayat2);
        
        Map<String, Object> riwayat3 = new HashMap<>();
        riwayat3.put("tipe", "Paket Perjalanan");
        riwayat3.put("namaTrip", "Wisata Lombok Gili Trawangan");
        riwayat3.put("tanggal", "20 Okt 2025 - 23 Okt 2025");
        riwayat3.put("kodeReservasi", "SJP-LOMBOK003");
        riwayat3.put("status", "Dibayar");
        riwayat3.put("totalHarga", "Rp 3.200.000");
        daftarRiwayat.add(riwayat3);


        // Logika sebenarnya:
        // for (Reservasi r : reservasiList) {
        //     Map<String, Object> itemRiwayat = new HashMap<>();
        //     itemRiwayat.put("kodeReservasi", r.getKodeReservasi());
        //     itemRiwayat.put("tanggalReservasi", r.getTanggalReservasi());
        //     itemRiwayat.put("status", r.getStatus());
        //     itemRiwayat.put("tipe", r.getTripType());
        //
        //     if ("paket_perjalanan".equals(r.getTripType())) {
        //         PaketPerjalanan paket = paketPerjalananDAO.getPaketById(r.getTripId());
        //         if (paket != null) {
        //             itemRiwayat.put("namaTrip", paket.getNamaPaket());
        //             itemRiwayat.put("tanggalMulai", paket.getTanggalMulai());
        //             // itemRiwayat.put("totalHarga", paket.getHarga()); // Ini harga per orang, perlu dikali jumlah peserta
        //         }
        //     } else if ("custom_trip".equals(r.getTripType())) {
        //         CustomTrip custom = customTripDAO.getCustomTripById(r.getTripId());
        //         if (custom != null) {
        //             itemRiwayat.put("namaTrip", custom.getNamaTrip());
        //             itemRiwayat.put("tanggalMulai", custom.getTanggalMulai());
        //             itemRiwayat.put("totalHarga", custom.getTotalHarga());
        //         }
        //     }
        //     daftarRiwayat.add(itemRiwayat);
        // }
        return daftarRiwayat;
    }
}
