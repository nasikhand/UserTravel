/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import javax.swing.JOptionPane;
import model.User;
// Asumsi kita punya cara untuk mengetahui user yang sedang login, misal via AuthController
// Untuk contoh ini, kita akan pass email user yang login

public class ProfileController {
    private UserDAO userDAO;
    private User currentUser; // User yang datanya sedang ditampilkan/diedit

    public ProfileController() {
        this.userDAO = new UserDAO();
    }

    /**
     * Memuat data pengguna berdasarkan email (dari user yang login).
     * @param email Email pengguna yang login.
     * @return Objek User jika ditemukan, null jika tidak.
     */
    public User loadUserProfile(String email) {
        this.currentUser = userDAO.getUserByEmail(email);
        return this.currentUser;
    }

    /**
     * Menyimpan perubahan data profil.
     * @param namaLengkap Nama lengkap baru.
     * @param noTelepon Nomor telepon baru.
     * @param alamat Alamat baru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean saveProfileChanges(String namaLengkap, String noTelepon, String alamat, String newImagePath) { // Pastikan nama parameter di sini adalah newImagePath
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Tidak ada data pengguna yang dimuat.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (namaLengkap.trim().isEmpty() || noTelepon.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama Lengkap dan Nomor Telepon tidak boleh kosong.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        currentUser.setNamaLengkap(namaLengkap.trim());
        currentUser.setNoTelepon(noTelepon.trim());
        currentUser.setAlamat(alamat.trim());
        
        // Jika ada path gambar baru, gunakan itu.
        // Pastikan variabel yang digunakan di sini SAMA dengan nama parameter method.
        if (newImagePath != null && !newImagePath.isEmpty()) { // Menggunakan newImagePath, bukan newimagepath
            currentUser.setGambarPath(newImagePath);
        }
        // Jika newImagePath null atau kosong, path gambar lama (currentImagePath yang sudah ada di currentUser) tidak akan diubah.
        // Jika currentUser.getGambarPath() awalnya null dan newImagePath juga null, maka akan tetap null.

        boolean success = userDAO.updateUserProfile(currentUser);
        if (success) {
            JOptionPane.showMessageDialog(null, "Profil berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal memperbarui profil.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }


    /**
     * Mengubah password pengguna.
     * @param oldPassword Password lama untuk verifikasi.
     * @param newPassword Password baru.
     * @param confirmNewPassword Konfirmasi password baru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Tidak ada data pengguna yang dimuat.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field password harus diisi.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            JOptionPane.showMessageDialog(null, "Password Baru dan Konfirmasi Password Baru tidak cocok.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Verifikasi password lama
        if (!userDAO.validateUser(currentUser.getEmail(), oldPassword)) {
            JOptionPane.showMessageDialog(null, "Password Lama salah.", "Verifikasi Gagal", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Update password baru
        boolean success = userDAO.updateUserPassword(currentUser.getId(), newPassword);
        if (success) {
            JOptionPane.showMessageDialog(null, "Password berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengubah password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }
     public User getCurrentUser() {
        return currentUser;
    }
}
