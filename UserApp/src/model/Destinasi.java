/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

public class Destinasi {
    private int id;
    private int kotaId;
    private String namaDestinasi;
    private String deskripsi;
    private BigDecimal harga;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getKotaId() { return kotaId; }
    public void setKotaId(int kotaId) { this.kotaId = kotaId; }
    public String getNamaDestinasi() { return namaDestinasi; }
    public void setNamaDestinasi(String namaDestinasi) { this.namaDestinasi = namaDestinasi; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public BigDecimal getHarga() { return harga; }
    public void setHarga(BigDecimal harga) { this.harga = harga; }

    // Override toString() agar tampilannya bagus di JList
    @Override
    public String toString() {
        return namaDestinasi;
    }
}
