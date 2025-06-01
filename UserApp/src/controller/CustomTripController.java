/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CustomTripDAO;
import dao.DestinasiDAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.CustomTrip;
import model.Destinasi;
import model.RincianCustomTrip;

public class CustomTripController {
    
    private final DestinasiDAO destinasiDAO;
    private final CustomTripDAO customTripDAO;
    
    // Data Trip yang sedang dibangun
    private final List<Destinasi> destinasiTerpilih;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private String pilihanTransportasi;
    private String preferensiAkomodasi;
    private boolean termasukSarapan;
    private String catatanAkomodasi;
    private final List<String> jenisAktivitas;
    private String catatanAktivitas;
    private String catatanFinalUser;
    private String namaTripCustom = "Trip Kustom Saya"; // Default nama trip
    private int jumlahPesertaCustom = 1; // Default jumlah peserta

    public CustomTripController() {
        this.destinasiDAO = new DestinasiDAO();
        this.customTripDAO = new CustomTripDAO();
        this.destinasiTerpilih = new ArrayList<>();
        this.jenisAktivitas = new ArrayList<>();
    }
    
    // --- Pengelolaan Destinasi ---
    public List<Destinasi> getAllDestinasi() {
        return destinasiDAO.getAllDestinasi();
    }
    public void tambahDestinasi(Destinasi destinasi) {
        if (destinasi != null && !destinasiTerpilih.contains(destinasi)) {
            destinasiTerpilih.add(destinasi);
        }
    }
    public void hapusDestinasi(Destinasi destinasi) {
        if (destinasi != null) {
            destinasiTerpilih.remove(destinasi);
        }
    }
    public List<Destinasi> getDestinasiTerpilih() {
        return destinasiTerpilih;
    }
    public BigDecimal hitungTotalBiaya() {
        BigDecimal total = BigDecimal.ZERO;
        for (Destinasi d : destinasiTerpilih) {
            total = total.add(d.getHarga() == null ? BigDecimal.ZERO : d.getHarga());
        }
        // TODO: Tambahkan biaya lain seperti transportasi, akomodasi, dll.
        return total;
    }

    // --- Pengelolaan Tanggal ---
    public void setTanggal(Date mulai, Date selesai) {
        this.tanggalMulai = mulai;
        this.tanggalSelesai = selesai;
    }
    public Date getTanggalMulai() { return tanggalMulai; }
    public Date getTanggalSelesai() { return tanggalSelesai; }

    // --- Pengelolaan Transportasi ---
    public void setPilihanTransportasi(String pilihan) { this.pilihanTransportasi = pilihan; }
    public String getPilihanTransportasi() { return pilihanTransportasi; }

    // --- Pengelolaan Akomodasi ---
    public void setPreferensiAkomodasi(String preferensi) { this.preferensiAkomodasi = preferensi; }
    public String getPreferensiAkomodasi() { return preferensiAkomodasi; }
    public void setTermasukSarapan(boolean sarapan) { this.termasukSarapan = sarapan; }
    public boolean isTermasukSarapan() { return termasukSarapan; }
    public void setCatatanAkomodasi(String catatan) { this.catatanAkomodasi = catatan; }
    public String getCatatanAkomodasi() { return catatanAkomodasi; }

    // --- Pengelolaan Aktivitas ---
    public void addJenisAktivitas(String aktivitas) { 
        if(!jenisAktivitas.contains(aktivitas)) jenisAktivitas.add(aktivitas); 
    }
    public void removeJenisAktivitas(String aktivitas) { jenisAktivitas.remove(aktivitas); }
    public List<String> getJenisAktivitas() { return jenisAktivitas; }
    public void setCatatanAktivitas(String catatan) { this.catatanAktivitas = catatan; }
    public String getCatatanAktivitas() { return catatanAktivitas; }

    // --- Info Tambahan untuk Trip ---
    public void setNamaTripCustom(String nama) { this.namaTripCustom = nama; }
    public String getNamaTripCustom() { return namaTripCustom; }
    public void setJumlahPesertaCustom(int jumlah) { this.jumlahPesertaCustom = jumlah > 0 ? jumlah : 1; }
    public int getJumlahPesertaCustom() { return jumlahPesertaCustom; }
    public void setCatatanFinalUser(String catatan) { this.catatanFinalUser = catatan; }
    public String getCatatanFinalUser() { return catatanFinalUser; }


    // --- Proses Penyimpanan Custom Trip ---
    public boolean simpanCustomTrip() {
        if (destinasiTerpilih.isEmpty() || tanggalMulai == null || tanggalSelesai == null) {
            // Validasi dasar
            return false; 
        }

        CustomTrip trip = new CustomTrip();
        // trip.setUserId( MainApp.currentUser != null ? MainApp.currentUser.getId() : null ); // Jika ada info user login
        trip.setNamaTrip(getNamaTripCustom());
        trip.setTanggalMulai(tanggalMulai);
        trip.setTanggalAkhir(tanggalSelesai);
        trip.setJumlahPeserta(getJumlahPesertaCustom());
        trip.setStatus("draft"); // Atau "dipesan" jika langsung
        trip.setTotalHarga(hitungTotalBiaya());
        
        // Gabungkan semua catatan menjadi satu
        StringBuilder catatanUserBuilder = new StringBuilder();
        if(catatanAkomodasi != null && !catatanAkomodasi.isEmpty()) catatanUserBuilder.append("Akomodasi: ").append(catatanAkomodasi).append("\n");
        if(catatanAktivitas != null && !catatanAktivitas.isEmpty()) catatanUserBuilder.append("Aktivitas: ").append(catatanAktivitas).append("\n");
        if(catatanFinalUser != null && !catatanFinalUser.isEmpty()) catatanUserBuilder.append("Catatan Final: ").append(catatanFinalUser);
        trip.setCatatanUser(catatanUserBuilder.toString());

        // Simpan trip utama
        int customTripId = customTripDAO.insertCustomTrip(trip);
        if (customTripId == -1) {
            return false; // Gagal menyimpan trip utama
        }

        // Siapkan dan simpan rincian trip
        List<RincianCustomTrip> rincianList = new ArrayList<>();
        for (int i = 0; i < destinasiTerpilih.size(); i++) {
            Destinasi d = destinasiTerpilih.get(i);
            RincianCustomTrip rincian = new RincianCustomTrip();
            rincian.setCustomTripId(customTripId);
            rincian.setDestinasiId(d.getId());
            rincian.setUrutanKunjungan(i + 1);
            rincian.setHargaDestinasi(d.getHarga() == null ? BigDecimal.ZERO : d.getHarga());
            // rincian.setTanggalKunjungan(); // Bisa diisi jika perlu per destinasi
            // rincian.setDurasiJam();
            // rincian.setBiayaTransport(); 
            rincianList.add(rincian);
        }
        
        return customTripDAO.insertRincianCustomTrip(rincianList);
    }
}