/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomTrip {
    private int id;
    private Integer userId; // Bisa null jika pengguna tidak login
    private String namaTrip;
    private Date tanggalMulai;
    private Date tanggalAkhir;
    private int jumlahPeserta;
    private String status; // 'draft', 'dipesan', 'dibayar', 'selesai'
    private BigDecimal totalHarga;
    private String catatanUser;
    private String pilihanTransportasi;
    private String preferensiAkomodasi;
    private boolean termasukSarapan;
    private String catatanAkomodasi;
    private List<String> jenisAktivitas; // Menyimpan pilihan aktivitas
    private String catatanAktivitas;
    private String catatanFinal;


    // Konstruktor, Getters, dan Setters lengkap
    public CustomTrip() {
        this.jenisAktivitas = new ArrayList<>(); // Inisialisasi list
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getNamaTrip() { return namaTrip; }
    public void setNamaTrip(String namaTrip) { this.namaTrip = namaTrip; }
    public Date getTanggalMulai() { return tanggalMulai; }
    public void setTanggalMulai(Date tanggalMulai) { this.tanggalMulai = tanggalMulai; }
    public Date getTanggalAkhir() { return tanggalAkhir; }
    public void setTanggalAkhir(Date tanggalAkhir) { this.tanggalAkhir = tanggalAkhir; }
    public int getJumlahPeserta() { return jumlahPeserta; }
    public void setJumlahPeserta(int jumlahPeserta) { this.jumlahPeserta = jumlahPeserta; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getTotalHarga() { return totalHarga; }
    public void setTotalHarga(BigDecimal totalHarga) { this.totalHarga = totalHarga; }
    public String getCatatanUser() { return catatanUser; }
    public void setCatatanUser(String catatanUser) { this.catatanUser = catatanUser; }
    public String getPilihanTransportasi() { return pilihanTransportasi; }
    public void setPilihanTransportasi(String pilihanTransportasi) { this.pilihanTransportasi = pilihanTransportasi; }
    public String getPreferensiAkomodasi() { return preferensiAkomodasi; }
    public void setPreferensiAkomodasi(String preferensiAkomodasi) { this.preferensiAkomodasi = preferensiAkomodasi; }
    public boolean isTermasukSarapan() { return termasukSarapan; }
    public void setTermasukSarapan(boolean termasukSarapan) { this.termasukSarapan = termasukSarapan; }
    public String getCatatanAkomodasi() { return catatanAkomodasi; }
    public void setCatatanAkomodasi(String catatanAkomodasi) { this.catatanAkomodasi = catatanAkomodasi; }
    public List<String> getJenisAktivitas() { return jenisAktivitas; }
    public void setJenisAktivitas(List<String> jenisAktivitas) { this.jenisAktivitas = jenisAktivitas; }
    public String getCatatanAktivitas() { return catatanAktivitas; }
    public void setCatatanAktivitas(String catatanAktivitas) { this.catatanAktivitas = catatanAktivitas; }
    public String getCatatanFinal() { return catatanFinal; }
    public void setCatatanFinal(String catatanFinal) { this.catatanFinal = catatanFinal; }
}
