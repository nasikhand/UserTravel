/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import app.ThemeManager; // Pastikan import ThemeManager ada
import com.formdev.flatlaf.FlatClientProperties;
import controller.PaketController; // Import PaketController
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class HomeView extends JFrame {
    private String loggedInUserEmail; // Untuk menyimpan email pengguna yang login
    private PaketController paketController; // Controller untuk pencarian paket

    // Constructor yang menerima email pengguna
    public HomeView(String emailPengguna) {
        this.loggedInUserEmail = emailPengguna;
        this.paketController = new PaketController(); // Inisialisasi controller
        initComponents();
    }

    // Default constructor (jika diperlukan, misal untuk testing tanpa login)
    public HomeView() {
        this("Pengguna Tamu"); // Default jika tidak ada email
    }

    private void initComponents() {
        setTitle("Sinar Jaya Travel - Beranda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750); // Sedikit perbesar
        setLocationRelativeTo(null);
        
        setJMenuBar(createMenuBar());
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding untuk keseluruhan
        mainPanel.setBackground(UIManager.getColor("control")); // Warna background sesuai tema
        
        mainPanel.add(createSideBar(), BorderLayout.WEST);
        mainPanel.add(createMainContent(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Anda bisa menambahkan FlatLaf style property jika ingin menu bar lebih modern
        // menuBar.putClientProperty(FlatClientProperties.STYLE, "arc: 10");


        JMenu fileMenu = new JMenu("Berkas");
        JMenuItem exitMenuItem = new JMenuItem("Keluar Aplikasi");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        JMenu helpMenu = new JMenu("Bantuan");
        JMenuItem aboutMenuItem = new JMenuItem("Tentang Aplikasi");
        helpMenu.add(aboutMenuItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    private JPanel createSideBar() {
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setPreferredSize(new Dimension(240, 0)); // Sedikit lebih lebar
        sideBar.setBackground(UIManager.getColor("Panel.background"));// Warna sidebar
        sideBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, UIManager.getColor("Component.borderColor")), // Garis kanan
            new EmptyBorder(15, 15, 15, 15) // Padding dalam
        ));


        JLabel appName = new JLabel("SINAR JAYA TRAVEL");
        appName.setFont(new Font("SansSerif", Font.BOLD, 20)); // Font lebih besar
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setForeground(new Color(0, 102, 102)); // Warna khas
        appName.setBorder(new EmptyBorder(0,0,20,0)); // Padding bawah

        sideBar.add(appName);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tombol navigasi
        // Tombol "Dashboard" bisa ditambahkan jika ada halaman dashboard khusus
        // sideBar.add(createNavButton("Beranda", "assets/icons/home.png", e -> {/* Aksi Beranda */} ));

        JButton searchPackageButton = createNavButton("Cari Paket", "assets/icons/search.png");
        searchPackageButton.addActionListener(e -> {
            // Saat ini pencarian ada di panel Quick Search.
            // Jika ingin halaman pencarian terpisah, bisa diimplementasikan di sini.
            JOptionPane.showMessageDialog(this, "Gunakan panel 'Pencarian Cepat' di halaman utama.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        });
        sideBar.add(searchPackageButton);

        JButton customTripButton = createNavButton("Buat Trip Kustom", "assets/icons/custom.png");
        customTripButton.addActionListener(e -> {
            new CustomTripBuilderView().setVisible(true);
            this.dispose();
        });
        sideBar.add(customTripButton);
        
        JButton profileButton = createNavButton("Profil Saya", "assets/icons/profile.png");
        profileButton.addActionListener(e -> {
            if (this.loggedInUserEmail != null && !this.loggedInUserEmail.equals("Pengguna Tamu") && !this.loggedInUserEmail.trim().isEmpty()) {
                new ProfileView(this.loggedInUserEmail).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Fitur ini memerlukan login. Silakan login terlebih dahulu.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            }
        });
        sideBar.add(profileButton);
        
        sideBar.add(Box.createVerticalGlue()); // Mendorong tombol di bawahnya ke dasar
        
        JButton themeButton = createNavButton("Ubah Tema", "assets/icons/theme.png");
        themeButton.addActionListener(e -> ThemeManager.switchTheme());
        sideBar.add(themeButton);
        
        JButton logoutButton = createNavButton("Logout", "assets/icons/logout.png");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                this.dispose();
            }
        });
        sideBar.add(logoutButton);
        
        return sideBar;
    }
    
    // Overload createNavButton untuk yang tanpa action listener langsung
    private JButton createNavButton(String text, String iconPath) {
        return createNavButton(text, iconPath, null);
    }
    
    private JButton createNavButton(String text, String iconPath, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon tidak ditemukan untuk tombol '" + text + "': " + iconPath);
            button.setText(" " + text); // Beri spasi jika ikon gagal dimuat
        }
        
        button.setHorizontalAlignment(JButton.LEFT);
        button.setIconTextGap(15);
        button.setFont(new Font("SansSerif", Font.PLAIN, 15)); // Font sedikit lebih besar
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // Tinggi tombol lebih besar
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true); // Penting untuk efek hover jika background diatur
        button.setBackground(UIManager.getColor("Panel.background")); // Samakan dengan background sidebar

        final Color hoverColor = UIManager.getColor("Button.hoverBackground");
        final Color defaultColor = UIManager.getColor("Panel.background");

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });
        
        if (action != null) {
            button.addActionListener(action);
        }
        
        return button;
    }

    private JPanel createMainContent() {
        JPanel mainContent = new JPanel(new BorderLayout(10, 20));
        mainContent.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainContent.setOpaque(false); // Ikuti background mainPanel

        // Panel Atas: Sapaan dan Tombol Cari
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Selamat Datang, " + (loggedInUserEmail != null ? loggedInUserEmail.split("@")[0] : "Pengguna") + "!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 28)); // Font lebih besar
        
        JButton searchIconTopButton = new JButton();
        try {
            ImageIcon searchIcon = new ImageIcon(new ImageIcon("assets/icons/search-big.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
            searchIconTopButton.setIcon(searchIcon);
        } catch (Exception e) {
            System.err.println("Ikon pencarian besar tidak ditemukan.");
        }
        searchIconTopButton.setToolTipText("Pencarian Lanjutan");
        searchIconTopButton.setBorderPainted(false);
        searchIconTopButton.setFocusPainted(false);
        searchIconTopButton.setContentAreaFilled(false);

        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(searchIconTopButton, BorderLayout.EAST);
        
        // Panel Tengah: Berisi semua card
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setOpaque(false);
        
        cardsPanel.add(createQuickSearchPanel());
        cardsPanel.add(Box.createRigidArea(new Dimension(0, 25))); // Spasi lebih besar
        
        JPanel midSection = new JPanel(new GridLayout(1, 2, 25, 0)); // Gap lebih besar
        midSection.setOpaque(false);
        midSection.add(createRecentTripPanel());
        midSection.add(createSpecialOfferPanel());
        
        cardsPanel.add(midSection);
        cardsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardsPanel.add(createPopularDestinationPanel());
        
        mainContent.add(topPanel, BorderLayout.NORTH);
        mainContent.add(new JScrollPane(cardsPanel), BorderLayout.CENTER); // Bungkus dengan ScrollPane jika konten bisa panjang

        return mainContent;
    }

    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Panel.background")); // Warna panel sesuai tema
        Border padding = new EmptyBorder(15, 15, 15, 15); // Padding lebih besar
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")), title);
        titledBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 16)); // Font judul panel
        panel.setBorder(BorderFactory.createCompoundBorder(titledBorder, padding));
        return panel;
    }

    private JPanel createQuickSearchPanel() {
        JPanel panel = createTitledPanel("Pencarian Cepat Paket Wisata");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Gap lebih besar

        String[] destinations = {"Pilih Tujuan", "Bali", "Yogyakarta", "Lombok", "Bandung", "Surabaya", "Raja Ampat"};
        JComboBox<String> destinationCombo = new JComboBox<>(destinations);
        destinationCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        destinationCombo.setPreferredSize(new Dimension(200, 35));
        
        JTextField dateField = new JTextField("Pilih Tanggal"); // Placeholder, idealnya JDateChooser
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateField.setPreferredSize(new Dimension(180, 35));
        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pilih Tanggal");


        JComboBox<String> travelersCombo = new JComboBox<>(new String[]{"Jumlah Orang", "1 Orang", "2 Orang", "3 Orang", "4 Orang", "5+ Orang"});
        travelersCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        travelersCombo.setPreferredSize(new Dimension(150, 35));

        JButton searchButton = new JButton("Cari Paket");
        searchButton.setBackground(new Color(0, 102, 102));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(120, 35));
        
        searchButton.addActionListener(e -> {
            String selectedDestination = (String) destinationCombo.getSelectedItem();
            if (selectedDestination == null || selectedDestination.equals("Pilih Tujuan")) {
                JOptionPane.showMessageDialog(this, "Harap pilih tujuan terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            paketController.searchPackages(selectedDestination);
            this.dispose();
        });

        // panel.add(new JLabel("Tujuan:")); // Label bisa dihilangkan jika placeholder cukup jelas
        panel.add(destinationCombo);
        // panel.add(new JLabel("Tanggal:"));
        panel.add(dateField);
        // panel.add(new JLabel("Wisatawan:"));
        panel.add(travelersCombo);
        panel.add(searchButton);
        
        return panel;
    }

    private JPanel createRecentTripPanel() {
        JPanel panel = createTitledPanel("Perjalanan Terakhir Anda");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Placeholder, nantinya diisi data dinamis
        panel.add(new JLabel("Belum ada perjalanan terakhir."));
        return panel;
    }

    private JPanel createSpecialOfferPanel() {
        JPanel panel = createTitledPanel("Penawaran Spesial Untuk Anda");
        panel.setLayout(new GridLayout(1, 3, 15, 0)); // Gap lebih besar
        // Placeholder, nantinya diisi data dinamis
        for (int i=0; i<3; i++) {
            JPanel offerItem = new JPanel();
            offerItem.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
            offerItem.add(new JLabel("Penawaran " + (i+1)));
            panel.add(offerItem);
        }
        return panel;
    }
    
    private JPanel createPopularDestinationPanel() {
        JPanel panel = createTitledPanel("Destinasi Populer");
        panel.setLayout(new GridLayout(1, 5, 15, 0)); // Gap lebih besar
        // Placeholder, nantinya diisi data dinamis
         for (int i=0; i<5; i++) {
            JPanel destItem = new JPanel();
            destItem.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
            destItem.add(new JLabel("Destinasi " + (i+1)));
            panel.add(destItem);
        }
        return panel;
    }
}