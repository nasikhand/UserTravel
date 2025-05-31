/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import controller.PaketController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import model.PaketPerjalanan;

public class SearchResultView extends JFrame {
    private PaketController paketController;

    public SearchResultView(String destination, List<PaketPerjalanan> packages) {
        this.paketController = new PaketController();
        
        setTitle("Hasil Pencarian Paket Perjalanan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        
        // Panel utama dengan background sedikit abu-abu
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Panel Filter di Kiri
        mainPanel.add(createFilterPanel(), BorderLayout.WEST);
        
        // Panel Hasil di Tengah
        mainPanel.add(createResultsPanel(destination, packages), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(220, 0));
        
        // Judul Filter
        JLabel filterTitle = new JLabel("Filter Hasil");
        filterTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        filterTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(filterTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Filter Harga
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBorder(BorderFactory.createTitledBorder("Rentang Harga"));
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pricePanel.add(new JTextField());
        pricePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        pricePanel.add(new JTextField());
        panel.add(pricePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Filter Durasi
        JPanel durationPanel = new JPanel();
        durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.Y_AXIS));
        durationPanel.setBorder(BorderFactory.createTitledBorder("Durasi"));
        durationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ButtonGroup durationGroup = new ButtonGroup();
        JRadioButton d1 = new JRadioButton("1 - 3 hari");
        JRadioButton d2 = new JRadioButton("4 - 7 hari");
        JRadioButton d3 = new JRadioButton("Lebih dari 7 hari");
        durationGroup.add(d1);
        durationGroup.add(d2);
        durationGroup.add(d3);
        durationPanel.add(d1);
        durationPanel.add(d2);
        durationPanel.add(d3);
        panel.add(durationPanel);

        panel.add(Box.createVerticalGlue()); // Mendorong tombol ke bawah
        
        JButton resetButton = new JButton("Reset Filter");
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(resetButton);

        return panel;
    }
    
    private JPanel createResultsPanel(String destination, List<PaketPerjalanan> packages) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JButton backButton = new JButton("Kembali ke Home");
        backButton.addActionListener(e -> {
            new HomeView().setVisible(true);
            this.dispose();
        });
        
        String titleText = String.format("<html>Menampilkan <b>%d paket</b> untuk tujuan <b>%s</b></html>", packages.size(), destination);
        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        
        if (packages.isEmpty()) {
            JLabel emptyLabel = new JLabel("Tidak ada paket perjalanan yang ditemukan.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            listPanel.add(emptyLabel);
        } else {
            for (PaketPerjalanan pkg : packages) {
                // Berikan controller saat membuat panel item
                listPanel.add(new PaketItemPanel(pkg, this.paketController)); // <-- UBAH BARIS INI
                listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
}