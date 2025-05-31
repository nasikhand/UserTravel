/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// File: src/app/MainApp.java

package app;

import javax.swing.SwingUtilities;
import view.LoginView;

public class MainApp {
    public static void main(String[] args) {
        // Panggil ThemeManager untuk menerapkan tema awal
        ThemeManager.applyInitialTheme();
        
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}