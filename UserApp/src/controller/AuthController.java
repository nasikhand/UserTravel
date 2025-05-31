/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import javax.swing.JOptionPane;
import model.User;
import view.HomeView;
import view.LoginView;
import view.RegisterView;

public class AuthController {
    private final UserDAO userDAO;
    private LoginView loginView;
    private RegisterView registerView;

    public AuthController(LoginView view) {
        this.userDAO = new UserDAO();
        this.loginView = view;
    }

    public AuthController(RegisterView view) {
        this.userDAO = new UserDAO();
        this.registerView = view;
    }

    public void login(String email, String password) {
        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Email dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (userDAO.validateUser(trimmedEmail, password)) {
            new HomeView().setVisible(true);
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(loginView, "Email atau Password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void register(User user, String confirmPassword) {
        if (user.getNamaLengkap().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getNoTelepon().isEmpty() || user.getAlamat().isEmpty()) {
            JOptionPane.showMessageDialog(registerView, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!user.getPassword().equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerView, "Password dan Konfirmasi Password tidak cocok!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (userDAO.insertUser(user)) {
            JOptionPane.showMessageDialog(registerView, "Registrasi Berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            showLoginViewFromRegister();
        } else {
            JOptionPane.showMessageDialog(registerView, "Registrasi Gagal. Email mungkin sudah terdaftar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showRegisterViewFromLogin() {
        if (loginView != null) {
            new RegisterView().setVisible(true);
            loginView.dispose();
        }
    }

    public void showLoginViewFromRegister() {
        if (registerView != null) {
            new LoginView().setVisible(true);
            registerView.dispose();
        }
    }
}