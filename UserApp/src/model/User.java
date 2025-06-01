/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal; // Jika Anda punya field harga di User, jika tidak, bisa dihapus
import java.util.Date;
import java.util.List; // Jika ada list di User, jika tidak, bisa dihapus
import java.util.ArrayList; 

public class User {
    private int id;
    private String namaLengkap;
    private String email;
    private String password;
    private String noTelepon;
    private String alamat;
    private String gambarPath;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getGambarPath() { // <-- GETTER BARU
        return gambarPath;
    }

    public void setGambarPath(String gambarPath) { // <-- SETTER BARU
        this.gambarPath = gambarPath;
    }
}