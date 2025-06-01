/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import controller.PaketController;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.PaketPerjalanan;

public class PaketItemPanel extends JPanel {
    private PaketPerjalanan paketObject; 

    public PaketItemPanel(PaketPerjalanan pkg, PaketController controller) {
        this.paketObject = pkg;
        
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)), // Border luar
            BorderFactory.createEmptyBorder(15, 15, 15, 15) // Padding dalam
        ));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Batasi tinggi panel
        setBackground(Color.WHITE);

        // --- Panel Gambar (Kiri) ---
        // Ganti "assets/placeholder.png" dengan path gambar dari database jika ada
        ImageIcon icon = new ImageIcon(new ImageIcon("assets/placeholder.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(icon);
        add(imageLabel, BorderLayout.WEST);

        // --- Panel Detail (Tengah) ---
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false); // Buat transparan agar mengikuti warna background utama

        JLabel namaPaketLabel = new JLabel(pkg.getNamaPaket());
        namaPaketLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Deskripsi singkat atau informasi hotel
        JLabel deskripsiLabel = new JLabel("<html>" + (pkg.getDeskripsi() != null ? pkg.getDeskripsi() : "Nikmati perjalanan tak terlupakan dengan layanan terbaik kami.") + "</html>");
        deskripsiLabel.setForeground(Color.GRAY);

        // Format harga ke Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        JLabel hargaLabel = new JLabel(formatRupiah.format(pkg.getHarga()));
        hargaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        hargaLabel.setForeground(new Color(0, 102, 102));

        detailPanel.add(namaPaketLabel);
        detailPanel.add(new JLabel(" ")); // Spacer
        detailPanel.add(deskripsiLabel);
        detailPanel.add(Box.createVerticalGlue()); // Mendorong harga ke bawah
        detailPanel.add(hargaLabel);

        add(detailPanel, BorderLayout.CENTER);

        // --- Panel Tombol (Kanan) ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        JButton detailButton = new JButton("Lihat Detail");
        JButton pesanButton = new JButton("Pesan Cepat");;
        
        detailButton.addActionListener(e -> controller.showTripDetails(pkg));
        
        // Styling tombol
        detailButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15;");
        pesanButton.setBackground(new Color(0, 153, 102));
        pesanButton.setForeground(Color.WHITE);
        pesanButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15;");
        
        detailButton.addActionListener(e -> controller.showTripDetails(pkg));
        
        // Modifikasi ActionListener untuk tombol pesanButton
        pesanButton.addActionListener(e -> {
            // Minta input jumlah penumpang
            String jumlahPenumpangStr = JOptionPane.showInputDialog(
                    this, 
                    "Masukkan jumlah penumpang:", 
                    "Jumlah Penumpang untuk " + pkg.getNamaPaket(), 
                    JOptionPane.PLAIN_MESSAGE
            );

            if (jumlahPenumpangStr != null && !jumlahPenumpangStr.trim().isEmpty()) {
                try {
                    int jumlahOrang = Integer.parseInt(jumlahPenumpangStr.trim());
                    if (jumlahOrang > 0 && jumlahOrang <= pkg.getKuota()) {
                        // Dapatkan frame induk (SearchResultView) untuk ditutup
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        if (parentFrame != null) {
                            parentFrame.dispose();
                        }
                        new BookingView(this.paketObject, jumlahOrang).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Jumlah penumpang tidak valid atau melebihi kuota (" + pkg.getKuota() + ").", "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Harap masukkan angka yang valid untuk jumlah penumpang.", "Input Salah", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        buttonPanel.add(detailButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi antar tombol
        buttonPanel.add(pesanButton);

        add(buttonPanel, BorderLayout.EAST);
    }
}
