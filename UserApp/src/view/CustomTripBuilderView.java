/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.CustomTripController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import model.Destinasi;

public class CustomTripBuilderView extends JFrame {

    private final CustomTripController controller;
    private final CardLayout mainContentCardLayout;
    private final JPanel mainContentPanel;

    public CustomTripBuilderView() {
        this.controller = new CustomTripController();
        
        setTitle("Custom Trip Builder");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel Kiri: Langkah-langkah
        mainPanel.add(createBuildStepsPanel(), BorderLayout.WEST);
        
        // Panel Tengah: Konten utama dengan CardLayout
        mainContentCardLayout = new CardLayout();
        mainContentPanel = new JPanel(mainContentCardLayout);
        mainContentPanel.add(createDestinationStepPanel(), "DESTINATION_STEP");
        // Tambahkan panel untuk langkah lain di sini nanti
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        // Panel Kanan: Ringkasan Trip
        mainPanel.add(createTripSummaryPanel(), BorderLayout.EAST);
        
        // Panel Bawah: Tombol Navigasi
        mainPanel.add(createNavigationButtonsPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createBuildStepsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Langkah Pembuatan"));
        
        panel.add(new JLabel("1. Pilih Destinasi"));
        panel.add(new JLabel("2. Tentukan Tanggal"));
        panel.add(new JLabel("3. Pilih Transportasi"));
        
        return panel;
    }
    
    private JPanel createDestinationStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JLabel title = new JLabel("Pilih Destinasi yang Ingin Dikunjungi", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        // Ambil data dari database
        List<Destinasi> listDestinasi = controller.getAllDestinasi();
        DefaultListModel<Destinasi> listModel = new DefaultListModel<>();
        for (Destinasi d : listDestinasi) {
            listModel.addElement(d);
        }
        
        JList<Destinasi> jListDestinasi = new JList<>(listModel);
        jListDestinasi.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListDestinasi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(jListDestinasi), BorderLayout.CENTER);
        panel.add(new JButton("Tambah ke Trip"), BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTripSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Ringkasan Trip"));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Destinasi Terpilih:"));
        // JList atau JTextArea untuk menampilkan destinasi akan ditambahkan di sini
        panel.add(Box.createVerticalGlue());
        panel.add(new JLabel("Estimasi Biaya: Rp 0"));
        
        return panel;
    }
    
    private JPanel createNavigationButtonsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        panel.add(new JButton("< Kembali"), BorderLayout.WEST);
        panel.add(new JButton("Simpan Draf"), BorderLayout.CENTER);
        panel.add(new JButton("Langkah Selanjutnya >"), BorderLayout.EAST);
        return panel;
    }
}
