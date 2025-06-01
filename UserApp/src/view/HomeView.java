/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import controller.PaketController;

import com.formdev.flatlaf.FlatClientProperties;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class HomeView extends JFrame {
    private PaketController paketController;

    public HomeView() {
        this.paketController = new PaketController();
        
        setTitle("Sinar Jaya Travel - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        setJMenuBar(createMenuBar());
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createSideBar(), BorderLayout.WEST);
        mainPanel.add(createMainContent(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu bookingsMenu = new JMenu("Bookings");
        
        fileMenu.add(new JMenuItem("Keluar"));
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(bookingsMenu);
        
        return menuBar;
    }

    private JPanel createSideBar() {
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setPreferredSize(new Dimension(220, 0));
        //sideBar.setBackground(new Color(245, 245, 245));
        sideBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel appName = new JLabel("SINAR JAYA TRAVEL");
        appName.setFont(new Font("SansSerif", Font.BOLD, 18));
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setForeground(new Color(0, 102, 102));

        sideBar.add(appName);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tombol navigasi
        sideBar.add(createNavButton("Dashboard", "assets/icons/home.png"));
        sideBar.add(createNavButton("Cari Paket Perjalanan", "assets/icons/search.png"));
        JButton customTripButton = createNavButton("Buat Custom Trip", "assets/icons/custom.png");
        // Tambahkan ActionListener ini:
        customTripButton.addActionListener(e -> {
            new CustomTripBuilderView().setVisible(true);
            this.dispose(); // Tutup HomeView
        });
        sideBar.add(customTripButton);
        sideBar.add(createNavButton("Profil Saya", "assets/icons/profile.png"));
        
        // Mendorong tombol di bawahnya ke dasar sidebar
        sideBar.add(Box.createVerticalGlue());
        
        // Buat tombol ubah tema
        JButton themeButton = createNavButton("Ubah Tema", "assets/icons/theme.png");
        themeButton.addActionListener(e -> app.ThemeManager.switchTheme()); // Tambahkan aksi
        sideBar.add(themeButton); // Tambahkan tombol ke sidebar
        
        sideBar.add(createNavButton("Logout", "assets/icons/logout.png"));
        
        return sideBar;
    }
    
    private JButton createNavButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
        }
        
        button.setHorizontalAlignment(JButton.LEFT);
        button.setIconTextGap(15);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Pengaturan awal: Tombol dibuat transparan
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        
        // Menambahkan listener untuk efek hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Saat mouse masuk, ubah latar belakang tombol
                button.setContentAreaFilled(true);
                // Gunakan warna hover default dari tema FlatLaf
                button.setBackground(UIManager.getColor("Button.hoverBackground"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Saat mouse keluar, kembalikan tombol menjadi transparan
                button.setContentAreaFilled(false);
            }
        });
        
        return button;
    }

    private JPanel createMainContent() {
        JPanel mainContent = new JPanel(new BorderLayout(10, 20));
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Selamat Datang, User!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        JButton searchButton = new JButton();
        try {
            ImageIcon searchIcon = new ImageIcon(new ImageIcon("assets/icons/search-big.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
            searchButton.setIcon(searchIcon);
        } catch (Exception e) {
            System.err.println("Icon not found: assets/icons/search-big.png");
        }
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(searchButton, BorderLayout.EAST);
        
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        
        cardsPanel.add(createQuickSearchPanel());
        cardsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel midSection = new JPanel(new GridLayout(1, 2, 20, 0));
        midSection.add(createRecentTripPanel());
        midSection.add(createSpecialOfferPanel());
        
        cardsPanel.add(midSection);
        cardsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardsPanel.add(createPopularDestinationPanel());
        
        mainContent.add(topPanel, BorderLayout.NORTH);
        mainContent.add(cardsPanel, BorderLayout.CENTER);

        return mainContent;
    }

    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), title);
        titledBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        panel.setBorder(BorderFactory.createCompoundBorder(titledBorder, padding));
        return panel;
    }

    private JPanel createQuickSearchPanel() {
        JPanel panel = createTitledPanel("Quick Search");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        // Kita gunakan data contoh dulu untuk tujuan
        String[] destinations = {"Bali", "Yogyakarta", "Lombok", "Bandung", "Surabaya"};
        JComboBox<String> destinationCombo = new JComboBox<>(destinations);
        
        JTextField dateField = new JTextField("Pilih Tanggal");
        JComboBox<String> travelersCombo = new JComboBox<>(new String[]{"1 Traveler", "2 Travelers"});
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 102, 102));
        searchButton.setForeground(Color.WHITE);
        
        // TAMBAHKAN ACTION LISTENER INI
        searchButton.addActionListener(e -> {
            String selectedDestination = (String) destinationCombo.getSelectedItem();
            paketController.searchPackages(selectedDestination);
            // Tutup HomeView setelah pencarian
            this.dispose();
        });

        panel.add(new JLabel("Destination:"));
        panel.add(destinationCombo);
        panel.add(new JLabel("Dates:"));
        panel.add(dateField);
        panel.add(new JLabel("Travelers:"));
        panel.add(travelersCombo);
        panel.add(searchButton);
        
        return panel;
    }

    private JPanel createRecentTripPanel() {
        JPanel panel = createTitledPanel("Recent Trip");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel tripName = new JLabel("Paris Weekend");
        tripName.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel tripDate = new JLabel("May 14-16");
        tripDate.setForeground(Color.GRAY);
        panel.add(tripName);
        panel.add(tripDate);
        return panel;
    }

    private JPanel createSpecialOfferPanel() {
        JPanel panel = createTitledPanel("Special Offer");
        panel.setLayout(new GridLayout(1, 3, 10, 0));
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        return panel;
    }
    
    private JPanel createPopularDestinationPanel() {
        JPanel panel = createTitledPanel("Popular Destination");
        panel.setLayout(new GridLayout(1, 5, 10, 0));
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        return panel;
    }
}