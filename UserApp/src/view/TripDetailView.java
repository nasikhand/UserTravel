/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import model.PaketPerjalanan;

public class TripDetailView extends JFrame {

    private PaketPerjalanan paket;
    private JLabel totalHargaLabel;
    private JSpinner jumlahOrangSpinner;
    private final NumberFormat formatRupiah;

    public TripDetailView(PaketPerjalanan paket) {
        this.paket = paket;
        this.formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        
        setTitle("Detail Paket: " + paket.getNamaPaket());
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        mainPanel.add(createActionPanel(), BorderLayout.WEST);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 0));
        
        panel.add(new JLabel("Aksi")).setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Opsi menggunakan RadioButton agar terlihat seperti di wireframe
        panel.add(new JRadioButton("Simpan Trip"));
        panel.add(new JRadioButton("Bagikan"));
        
        return panel;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        
        // Bagian Atas: Nama Paket dan Galeri Foto (placeholder)
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel namaPaketLabel = new JLabel(paket.getNamaPaket());
        namaPaketLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        
        JPanel galleryPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        galleryPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        for (int i = 0; i < 4; i++) {
            JPanel photoPlaceholder = new JPanel();
            photoPlaceholder.setBackground(Color.GRAY);
            photoPlaceholder.setPreferredSize(new Dimension(150, 100));
            galleryPanel.add(photoPlaceholder);
        }
        
        topPanel.add(namaPaketLabel, BorderLayout.NORTH);
        topPanel.add(galleryPanel, BorderLayout.CENTER);
        
        // Bagian Bawah: Detail dan Harga
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.add(createTripDetailsInfoPanel());
        bottomPanel.add(createTotalPricePanel());
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTripDetailsInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(null, "Rincian Trip", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 14)));
        // Di sini Anda bisa menampilkan rincian dari tabel rincian_paket_perjalanan
        panel.add(new JLabel("<html>- Mengunjungi Pantai Kuta<br>- Tur ke Ubud<br>- Makan malam di Jimbaran</html>"));
        return panel;
    }
    
    private JPanel createTotalPricePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(null, "Total Harga", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 14)));
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        
        // Harga Dasar
        formPanel.add(new JLabel("Harga Dasar:"));
        formPanel.add(new JLabel(formatRupiah.format(paket.getHarga())));
        
        // Jumlah Orang
        formPanel.add(new JLabel("Jumlah Orang:"));
        jumlahOrangSpinner = new JSpinner(new SpinnerNumberModel(1, 1, paket.getKuota(), 1));
        jumlahOrangSpinner.addChangeListener(e -> updateTotalHarga()); // Listener untuk update harga
        formPanel.add(jumlahOrangSpinner);
        
        // Pajak & Biaya
        formPanel.add(new JLabel("Pajak & Biaya:"));
        formPanel.add(new JLabel("Termasuk")); // Contoh
        
        // Total Harga
        JLabel totalLabel = new JLabel("Total Harga:");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalHargaLabel = new JLabel();
        totalHargaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        formPanel.add(totalLabel);
        formPanel.add(totalHargaLabel);
        
        JButton bookButton = new JButton("Pesan");
        bookButton.setBackground(new Color(0, 102, 102));
        bookButton.setForeground(Color.WHITE);
        
        bookButton.addActionListener(e -> {
            int penumpang = (int) jumlahOrangSpinner.getValue();
            new BookingView(this.paket, penumpang).setVisible(true);
            this.dispose(); // Tutup jendela detail
        });

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(bookButton, BorderLayout.SOUTH);
        
        updateTotalHarga(); // Panggil sekali untuk inisialisasi harga
        
        return panel;
    }
    
    private void updateTotalHarga() {
        int jumlahOrang = (int) jumlahOrangSpinner.getValue();
        BigDecimal hargaPerOrang = paket.getHarga();
        BigDecimal total = hargaPerOrang.multiply(new BigDecimal(jumlahOrang));
        totalHargaLabel.setText(formatRupiah.format(total));
    }
}
