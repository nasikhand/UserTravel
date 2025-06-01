/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

public class RincianCustomTrip {
    private int id;
    private int customTripId;
    private int destinasiId;
    private Date tanggalKunjungan; // Bisa jadi relevan per destinasi
    private int durasiJam;
    private int urutanKunjungan;
    private BigDecimal hargaDestinasi; // Harga saat ditambahkan
    private BigDecimal biayaTransport; // Biaya transport ke destinasi ini

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomTripId() { return customTripId; }
    public void setCustomTripId(int customTripId) { this.customTripId = customTripId; }
    public int getDestinasiId() { return destinasiId; }
    public void setDestinasiId(int destinasiId) { this.destinasiId = destinasiId; }
    public Date getTanggalKunjungan() { return tanggalKunjungan; }
    public void setTanggalKunjungan(Date tanggalKunjungan) { this.tanggalKunjungan = tanggalKunjungan; }
    public int getDurasiJam() { return durasiJam; }
    public void setDurasiJam(int durasiJam) { this.durasiJam = durasiJam; }
    public int getUrutanKunjungan() { return urutanKunjungan; }
    public void setUrutanKunjungan(int urutanKunjungan) { this.urutanKunjungan = urutanKunjungan; }
    public BigDecimal getHargaDestinasi() { return hargaDestinasi; }
    public void setHargaDestinasi(BigDecimal hargaDestinasi) { this.hargaDestinasi = hargaDestinasi; }
    public BigDecimal getBiayaTransport() { return biayaTransport; }
    public void setBiayaTransport(BigDecimal biayaTransport) { this.biayaTransport = biayaTransport; }
}
