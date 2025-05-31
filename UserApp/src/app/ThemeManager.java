/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ThemeManager {

    // Enum untuk merepresentasikan tema yang ada
    public enum Theme {
        LIGHT, DARK
    }

    // Menyimpan tema yang sedang aktif
    private static Theme currentTheme = Theme.LIGHT;

    /**
     * Menerapkan tema awal saat aplikasi pertama kali dijalankan.
     */
    public static void applyInitialTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Gagal menerapkan tema awal.");
        }
    }

    /**
     * Mengganti tema antara LIGHT dan DARK.
     * Metode ini akan memperbarui UI semua jendela yang sedang terbuka.
     */
    public static void switchTheme() {
        // Tentukan tema berikutnya
        currentTheme = (currentTheme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;

        try {
            // Terapkan LookAndFeel yang baru berdasarkan tema yang dipilih
            if (currentTheme == Theme.DARK) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }

            // Perbarui UI semua frame/jendela yang sedang aktif
            for (Frame frame : JFrame.getFrames()) { 
                SwingUtilities.updateComponentTreeUI(frame);
            }

        } catch (Exception ex) {
            System.err.println("Gagal mengganti tema.");
        }
    }
}
