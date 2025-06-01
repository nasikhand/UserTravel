/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatClientProperties; // Untuk styling modern
import com.toedter.calendar.JDateChooser;
import controller.CustomTripController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image; // Untuk ikon, jika Anda menambahkannya
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon; // Untuk ikon, jika Anda menambahkannya
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Destinasi;
import view.StepIndicatorPanel;

public class CustomTripBuilderView extends JFrame {

    private final CustomTripController controller;
    private StepIndicatorPanel stepIndicatorPanel;
    private final CardLayout mainContentCardLayout;
    private final JPanel mainContentPanel;
    private int currentStepIndex = 0;
    private final String[] steps = {"1. Destinasi", "2. Tanggal", "3. Transportasi", "4. Akomodasi", "5. Aktivitas", "6. Final"};

    // Komponen UI Langkah 1 (Destinasi)
    private JList<Destinasi> listSaranDestinasi;
    private DefaultListModel<Destinasi> modelSaran;
    private JTextField fieldCariDestinasi;
    private JLabel labelNamaOpsiDestinasi, labelDeskripsiOpsiDestinasi, labelHargaOpsiDestinasi;

    // Komponen UI Langkah 2 (Tanggal)
    private JDateChooser tanggalMulaiChooser, tanggalSelesaiChooser;
    
    // Komponen UI Langkah 3 (Transportasi)
    private ButtonGroup transportGroup;

    // Komponen UI Langkah 4 (Akomodasi)
    private ButtonGroup akomodasiGroup;
    private JCheckBox sarapanCheckBox;
    private JTextArea catatanAkomodasiArea;

    // Komponen UI Langkah 5 (Aktivitas)
    private List<JCheckBox> aktivitasCheckBoxes;
    private JTextArea catatanAktivitasArea;

    // Komponen UI Langkah 6 (Final)
    private JTextField namaTripField;
    private JSpinner jumlahPesertaSpinner;
    private JTextArea catatanFinalArea;
    private JLabel finalDestinasiLabel, finalTanggalLabel, finalTransportLabel, 
                     finalAkomodasiLabel, finalAktivitasLabel, finalHargaLabelFinal;

    // Komponen UI Ringkasan Trip (Sidebar Kanan)
    private JList<Destinasi> listRingkasanTrip;
    private DefaultListModel<Destinasi> modelRingkasan;
    private JLabel labelTotalBiaya, labelRingkasanTanggal, labelRingkasanTransport, 
                     labelRingkasanAkomodasi, labelRingkasanAktivitas;
    private final NumberFormat formatRupiah;
    
    private JButton tombolKembali, tombolSelanjutnya, tombolSimpanDraf;

    public CustomTripBuilderView() {
        this.controller = new CustomTripController();
        this.formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        this.aktivitasCheckBoxes = new ArrayList<>();
        
        setTitle("Perancang Trip Kustom - Sinar Jaya Travel");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(UIManager.getColor("control")); // Warna background utama sesuai tema
        
        stepIndicatorPanel = new StepIndicatorPanel(steps);
        stepIndicatorPanel.setPreferredSize(new Dimension(220, 0));
        stepIndicatorPanel.setBackground(UIManager.getColor("Panel.background"));
        stepIndicatorPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
        
        mainContentCardLayout = new CardLayout();
        mainContentPanel = new JPanel(mainContentCardLayout);
        mainContentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
        mainContentPanel.add(createDestinationStepPanel(), steps[0]);
        mainContentPanel.add(createDateStepPanel(), steps[1]);
        mainContentPanel.add(createTransportStepPanel(), steps[2]);
        mainContentPanel.add(createAccommodationStepPanel(), steps[3]);
        mainContentPanel.add(createActivitiesStepPanel(), steps[4]);
        mainContentPanel.add(createFinalStepPanel(), steps[5]);
        
        mainPanel.add(stepIndicatorPanel, BorderLayout.WEST);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        mainPanel.add(createTripSummaryPanel(), BorderLayout.EAST);
        mainPanel.add(createNavigationButtonsPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        
        muatSemuaDestinasi();
        perbaruiStatusTombolNavigasi();
        perbaruiTampilanRingkasan();
    }
    
    private void muatSemuaDestinasi() {
        modelSaran.clear();
        List<Destinasi> semuaDestinasi = controller.getAllDestinasi();
        for (Destinasi d : semuaDestinasi) {
            modelSaran.addElement(d);
        }
    }
    
    // ----- PEMBUATAN PANEL UNTUK SETIAP LANGKAH -----

    private JPanel createDestinationStepPanel() {
        JPanel panelLangkahDestinasi = new JPanel(new BorderLayout(10,15));
        panelLangkahDestinasi.setBorder(new EmptyBorder(15,15,15,15));
        panelLangkahDestinasi.setBackground(UIManager.getColor("Panel.background"));

        // Bagian Atas: Input Pencarian dan Tombol Tambah
        JPanel panelPencarianDanTambah = new JPanel(new BorderLayout(10, 5));
        panelPencarianDanTambah.setOpaque(false);

        JPanel panelInputPencarian = createTitledPanel("Cari Destinasi");
        panelInputPencarian.setLayout(new BorderLayout(10,0));
        panelInputPencarian.setOpaque(false);
        fieldCariDestinasi = new JTextField();
        fieldCariDestinasi.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ketik nama destinasi...");
        fieldCariDestinasi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fieldCariDestinasi.setPreferredSize(new Dimension(0, 35));
        panelInputPencarian.add(fieldCariDestinasi, BorderLayout.CENTER);
        
        fieldCariDestinasi.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { filterDestinasi(); }
            public void removeUpdate(DocumentEvent e) { filterDestinasi(); }
            public void insertUpdate(DocumentEvent e) { filterDestinasi(); }
        });

        JButton tombolTambah = new JButton("Tambah ke Trip (+)");
        tombolTambah.setFont(new Font("SansSerif", Font.BOLD, 14));
        tombolTambah.setBackground(new Color(0,153,51));
        tombolTambah.setForeground(Color.WHITE);
        tombolTambah.addActionListener(e -> {
            Destinasi terpilih = listSaranDestinasi.getSelectedValue();
            if (terpilih != null) {
                controller.tambahDestinasi(terpilih);
                perbaruiTampilanRingkasan();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih satu destinasi dari daftar saran terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        panelPencarianDanTambah.add(panelInputPencarian, BorderLayout.CENTER);
        panelPencarianDanTambah.add(tombolTambah, BorderLayout.EAST);

        JPanel panelSaran = createTitledPanel("Pilih dari Daftar Destinasi Tersedia");
        panelSaran.setLayout(new BorderLayout());
        panelSaran.setOpaque(false);
        modelSaran = new DefaultListModel<>();
        listSaranDestinasi = new JList<>(modelSaran);
        listSaranDestinasi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSaranDestinasi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        listSaranDestinasi.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Destinasi selected = listSaranDestinasi.getSelectedValue();
                updatePanelOpsiDestinasi(selected);
            }
        });
        JScrollPane scrollSaran = new JScrollPane(listSaranDestinasi);
        scrollSaran.setPreferredSize(new Dimension(0, 200));
        panelSaran.add(scrollSaran, BorderLayout.CENTER);

        JPanel panelOpsiDestinasiPilihan = createTitledPanel("Detail Destinasi Pilihan");
        panelOpsiDestinasiPilihan.setLayout(new BoxLayout(panelOpsiDestinasiPilihan, BoxLayout.Y_AXIS));
        panelOpsiDestinasiPilihan.setOpaque(false);
        panelOpsiDestinasiPilihan.setPreferredSize(new Dimension(0, 150));

        labelNamaOpsiDestinasi = new JLabel("Nama: -");
        labelNamaOpsiDestinasi.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelDeskripsiOpsiDestinasi = new JLabel("Deskripsi: -");
        labelDeskripsiOpsiDestinasi.setFont(new Font("SansSerif", Font.ITALIC, 13));
        labelHargaOpsiDestinasi = new JLabel("Harga: -");
        labelHargaOpsiDestinasi.setFont(new Font("SansSerif", Font.PLAIN, 14));

        panelOpsiDestinasiPilihan.add(labelNamaOpsiDestinasi);
        panelOpsiDestinasiPilihan.add(Box.createRigidArea(new Dimension(0,5)));
        panelOpsiDestinasiPilihan.add(labelDeskripsiOpsiDestinasi);
        panelOpsiDestinasiPilihan.add(Box.createRigidArea(new Dimension(0,5)));
        panelOpsiDestinasiPilihan.add(labelHargaOpsiDestinasi);
        
        panelLangkahDestinasi.add(panelPencarianDanTambah, BorderLayout.NORTH);
        panelLangkahDestinasi.add(panelSaran, BorderLayout.CENTER);
        panelLangkahDestinasi.add(panelOpsiDestinasiPilihan, BorderLayout.SOUTH);

        return panelLangkahDestinasi;
    }
    
    private void filterDestinasi() {
        String teksCari = fieldCariDestinasi.getText().toLowerCase().trim();
        modelSaran.clear();
        for (Destinasi d : controller.getAllDestinasi()) {
            if (d.getNamaDestinasi().toLowerCase().contains(teksCari)) {
                modelSaran.addElement(d);
            }
        }
    }

    private void updatePanelOpsiDestinasi(Destinasi d) {
        if (d != null) {
            labelNamaOpsiDestinasi.setText("Nama: " + d.getNamaDestinasi());
            labelDeskripsiOpsiDestinasi.setText("<html><body style='width: 400px'>Deskripsi: " + (d.getDeskripsi() != null ? d.getDeskripsi() : "-") + "</body></html>");
            labelHargaOpsiDestinasi.setText("Harga: " + (d.getHarga() != null ? formatRupiah.format(d.getHarga()) : "N/A"));
        } else {
            labelNamaOpsiDestinasi.setText("Nama: -");
            labelDeskripsiOpsiDestinasi.setText("Deskripsi: -");
            labelHargaOpsiDestinasi.setText("Harga: -");
        }
    }

    private JPanel createDateStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 30));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setOpaque(false); 
        JLabel title = new JLabel("Langkah 2: Tentukan Rentang Tanggal Perjalanan Anda");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerPanel.add(title);
        
        JPanel formPanel = createTitledPanel("");
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel labelMulai = new JLabel("Tanggal Mulai:");
        labelMulai.setFont(new Font("SansSerif", Font.PLAIN, 16));
        formPanel.add(labelMulai, gbc);
        
        gbc.gridx = 1;
        tanggalMulaiChooser = new JDateChooser();
        tanggalMulaiChooser.setDateFormatString("dd MMMM yyyy");
        tanggalMulaiChooser.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tanggalMulaiChooser.setPreferredSize(new Dimension(280, 40));
        formPanel.add(tanggalMulaiChooser, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelSelesai = new JLabel("Tanggal Selesai:");
        labelSelesai.setFont(new Font("SansSerif", Font.PLAIN, 16));
        formPanel.add(labelSelesai, gbc);
        
        gbc.gridx = 1;
        tanggalSelesaiChooser = new JDateChooser();
        tanggalSelesaiChooser.setDateFormatString("dd MMMM yyyy");
        tanggalSelesaiChooser.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tanggalSelesaiChooser.setPreferredSize(new Dimension(280, 40));
        formPanel.add(tanggalSelesaiChooser, gbc);

        JPanel formWrapper = new JPanel(new GridBagLayout()); 
        formWrapper.setOpaque(false);
        formWrapper.add(formPanel); 

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formWrapper, BorderLayout.CENTER); 
        
        return panel;
    }
    private JPanel createTransportStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));

        JLabel title = new JLabel("Langkah 3: Pilih Opsi Transportasi Selama Trip");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1, 0, 15));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(new EmptyBorder(20,0,0,0));
        
        transportGroup = new ButtonGroup();
        
        String[][] transportOptions = {
            {"Mobil Pribadi (Sewa)", "Fleksibilitas maksimal dengan mobil dan supir pribadi selama perjalanan Anda."},
            {"Transportasi Umum", "Pilihan hemat biaya menggunakan layanan bus, kereta, atau transportasi publik lokal."},
            {"Campuran (Mobil & Umum)", "Kombinasi sewa mobil untuk area tertentu dan transportasi umum untuk mobilitas lainnya."}
        };
        
        for (String[] option : transportOptions) {
            optionsPanel.add(createStyledOptionPanel(option[0], option[1], transportGroup, "transportasi"));
        }
        
        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(UIManager.getColor("Panel.background"));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Method helper BARU untuk membuat panel opsi transportasi yang lebih keren
    private JPanel createStyledTransportOptionPanel(String title, String description) {
        JPanel optionPanel = new JPanel(new BorderLayout(10, 5));
        optionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // Border halus
            new EmptyBorder(15, 15, 15, 15) // Padding dalam
        ));
        optionPanel.setBackground(Color.WHITE);
        optionPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JRadioButton radioButton = new JRadioButton("<html><body style='width: 400px'><b>" + title + "</b><p style='margin-top:5px; color:gray;'>" + description + "</p></body></html>");
        radioButton.setActionCommand(title); // Penting untuk mengambil nilainya nanti
        radioButton.setOpaque(false); // Agar background panel terlihat
        radioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        transportGroup.add(radioButton); // Tambahkan ke ButtonGroup

        // Efek visual saat radio button dipilih
        final Color defaultBg = Color.WHITE;
        final Color selectedBg = new Color(230, 245, 255); // Warna biru muda untuk seleksi

        radioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Set semua panel opsi ke warna default dulu
                for(Component comp : radioButton.getParent().getParent().getComponents()){
                    if(comp instanceof JPanel){
                        comp.setBackground(defaultBg);
                        ((JPanel) comp).setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 200, 200)),
                            new EmptyBorder(15, 15, 15, 15)
                        ));
                    }
                }
                // Lalu set panel yang terseleksi
                optionPanel.setBackground(selectedBg);
                 optionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 153)), // Border lebih tebal saat aktif
                    new EmptyBorder(15, 15, 15, 15)
                ));
            }
        });
        
        // Membuat panel bisa diklik untuk memilih radio button
        optionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioButton.setSelected(true);
            }
        });
        
        // Icon (opsional, jika Anda punya ikon untuk setiap jenis transportasi)
        // JLabel iconLabel = new JLabel(new ImageIcon("path_to_icon.png"));
        // optionPanel.add(iconLabel, BorderLayout.WEST);
        optionPanel.add(radioButton, BorderLayout.CENTER);
        
        return optionPanel;
    }
    
    private JPanel createTransportOption(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JRadioButton radioButton = new JRadioButton("<html><b>" + title + "</b><br><p>" + description + "</p></html>");
        radioButton.setActionCommand(title);
        radioButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        transportGroup.add(radioButton);
        panel.add(radioButton, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAccommodationStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));

        JLabel title = new JLabel("Langkah 4: Preferensi Akomodasi Anda");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(new EmptyBorder(20,0,0,0));
        
        akomodasiGroup = new ButtonGroup();
        
        String[] tipeAkomodasi = { "Hotel (Bintang 3-5)", "Villa / Apartemen Pribadi", "Guest House / Penginapan Budget", "Saya Akan Urus Sendiri Akomodasi"};
        String[] deskripsiAkomodasi = { "Kenyamanan dan fasilitas lengkap di hotel berbintang.", "Privasi dan ruang lebih luas, cocok untuk keluarga atau grup.", "Pilihan ekonomis dengan suasana yang lebih personal.", "Anda memilih dan memesan akomodasi secara mandiri."};
        
        for (int i = 0; i < tipeAkomodasi.length; i++) {
            optionsPanel.add(createStyledOptionPanel(tipeAkomodasi[i], deskripsiAkomodasi[i], akomodasiGroup, "akomodasi"));
            if (i < tipeAkomodasi.length -1) optionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        sarapanCheckBox = new JCheckBox("Termasuk Sarapan (jika memungkinkan dengan pilihan di atas)");
        sarapanCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sarapanCheckBox.setOpaque(false);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        optionsPanel.add(sarapanCheckBox);
        
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel catatanLabel = new JLabel("Catatan Tambahan untuk Akomodasi (opsional):");
        catatanLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        optionsPanel.add(catatanLabel);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        catatanAkomodasiArea = new JTextArea(4, 0);
        catatanAkomodasiArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        catatanAkomodasiArea.setLineWrap(true);
        catatanAkomodasiArea.setWrapStyleWord(true);
        JScrollPane scrollCatatan = new JScrollPane(catatanAkomodasiArea);
        scrollCatatan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        optionsPanel.add(scrollCatatan);
        
        panel.add(optionsPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStyledOptionPanel(String title, String description, ButtonGroup group, String type) {
        JPanel optionPanel = new JPanel(new BorderLayout(10, 5));
        optionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        optionPanel.setBackground(Color.WHITE);
        optionPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JRadioButton radioButton = new JRadioButton("<html><body style='width: 450px'><b>" + title + "</b><p style='margin-top:5px; color:gray;'>" + description + "</p></body></html>");
        radioButton.setActionCommand(title);
        radioButton.setOpaque(false);
        radioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        group.add(radioButton);

        final Color defaultBg = Color.WHITE;
        final Color selectedBg = new Color(230, 245, 255); 
        final Border defaultBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(15, 15, 15, 15)
        );
        final Border selectedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 153), 2), // Border lebih tebal saat aktif
            new EmptyBorder(15, 15, 15, 15)
        );

        radioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                optionPanel.setBackground(selectedBg);
                optionPanel.setBorder(selectedBorder);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                optionPanel.setBackground(defaultBg);
                optionPanel.setBorder(defaultBorder);
            }
        });
        
        optionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioButton.setSelected(true);
            }
        });
        
        optionPanel.add(radioButton, BorderLayout.CENTER);
        return optionPanel;
    }

    private JPanel createActivitiesStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));

        JLabel title = new JLabel("Langkah 5: Jenis Aktivitas yang Diinginkan");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel checkPanel = new JPanel(new GridLayout(0, 2, 20, 15)); 
        checkPanel.setOpaque(false);
        checkPanel.setBorder(new EmptyBorder(20,0,20,0));

        String[] jenis = { "Wisata Alam (Pantai, Gunung, Air Terjun)", "Budaya & Sejarah (Museum, Candi, Situs)", "Kuliner Lokal dan Kafe Unik", "Belanja (Souvenir dan Produk Lokal)", "Relaksasi & Spa", "Petualangan (Rafting, Hiking, Diving)"};
        
        aktivitasCheckBoxes.clear();
        for (String tipe : jenis) {
            JCheckBox cb = new JCheckBox(tipe);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cb.setOpaque(false);
            aktivitasCheckBoxes.add(cb);
            checkPanel.add(cb);
        }
        
        JPanel catatanPanel = new JPanel(new BorderLayout(0,5));
        catatanPanel.setOpaque(false);
        JLabel catatanLabel = new JLabel("Aktivitas Spesifik Lainnya atau Catatan (opsional):");
        catatanLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        catatanPanel.add(catatanLabel, BorderLayout.NORTH);
        
        catatanAktivitasArea = new JTextArea(4, 0);
        catatanAktivitasArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        catatanAktivitasArea.setLineWrap(true);
        catatanAktivitasArea.setWrapStyleWord(true);
        catatanPanel.add(new JScrollPane(catatanAktivitasArea), BorderLayout.CENTER);
        
        panel.add(new JScrollPane(checkPanel), BorderLayout.CENTER);
        panel.add(catatanPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFinalStepPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));
        
        JLabel title = new JLabel("Langkah 6: Konfirmasi dan Selesaikan Trip Kustom Anda");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(15,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10); // Tambah padding
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font valueFont = new Font("SansSerif", Font.PLAIN, 14);

        int yPos = 0;
        gbc.gridy = yPos++; gbc.gridx = 0;
        formPanel.add(createFinalInfoLabel("Nama Trip Anda:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; // Beri weight agar field bisa expand
        namaTripField = new JTextField(controller.getNamaTripCustom() != null ? controller.getNamaTripCustom() : "Perjalanan Kustom Saya", 25);
        namaTripField.setFont(valueFont);
        namaTripField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(namaTripField, gbc);
        gbc.weightx = 0; // Reset weight

        gbc.gridy = yPos++; gbc.gridx = 0;
        formPanel.add(createFinalInfoLabel("Jumlah Peserta:"), gbc);
        gbc.gridx = 1;
        jumlahPesertaSpinner = new JSpinner(new SpinnerNumberModel(controller.getJumlahPesertaCustom(), 1, 100, 1));
        jumlahPesertaSpinner.setFont(valueFont);
        jumlahPesertaSpinner.setPreferredSize(new Dimension(100, 35));
        jumlahPesertaSpinner.addChangeListener(e -> {
            controller.setJumlahPesertaCustom((int) jumlahPesertaSpinner.getValue());
            updateFinalStepPanelContent();
            perbaruiTampilanRingkasan();
        });
        formPanel.add(jumlahPesertaSpinner, gbc);

        gbc.gridy = yPos++; gbc.gridx = 0; gbc.gridwidth = 2;
        JLabel ringkasanPilihanLabel = new JLabel("Ringkasan Pilihan Anda:");
        ringkasanPilihanLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        ringkasanPilihanLabel.setBorder(new EmptyBorder(15,0,5,0));
        formPanel.add(ringkasanPilihanLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = yPos++; gbc.gridx = 0; formPanel.add(createFinalInfoLabel("Destinasi:"), gbc);
        gbc.gridx = 1; finalDestinasiLabel = createFinalValueLabel("-"); formPanel.add(finalDestinasiLabel, gbc);
        
        gbc.gridy = yPos++; gbc.gridx = 0; formPanel.add(createFinalInfoLabel("Tanggal:"), gbc);
        gbc.gridx = 1; finalTanggalLabel = createFinalValueLabel("-"); formPanel.add(finalTanggalLabel, gbc);

        gbc.gridy = yPos++; gbc.gridx = 0; formPanel.add(createFinalInfoLabel("Transportasi:"), gbc);
        gbc.gridx = 1; finalTransportLabel = createFinalValueLabel("-"); formPanel.add(finalTransportLabel, gbc);

        gbc.gridy = yPos++; gbc.gridx = 0; formPanel.add(createFinalInfoLabel("Akomodasi:"), gbc);
        gbc.gridx = 1; finalAkomodasiLabel = createFinalValueLabel("-"); formPanel.add(finalAkomodasiLabel, gbc);

        gbc.gridy = yPos++; gbc.gridx = 0; formPanel.add(createFinalInfoLabel("Aktivitas:"), gbc);
        gbc.gridx = 1; finalAktivitasLabel = createFinalValueLabel("-"); formPanel.add(finalAktivitasLabel, gbc);
        
        gbc.gridy = yPos++; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel hargaPanelFinal = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        hargaPanelFinal.setOpaque(false);
        hargaPanelFinal.add(createFinalInfoLabel("Total Estimasi:"));
        finalHargaLabelFinal = new JLabel("Rp 0,00");
        finalHargaLabelFinal.setFont(new Font("SansSerif", Font.BOLD, 20));
        finalHargaLabelFinal.setForeground(new Color(0,102,102));
        hargaPanelFinal.add(Box.createRigidArea(new Dimension(10,0)));
        hargaPanelFinal.add(finalHargaLabelFinal);
        formPanel.add(hargaPanelFinal, gbc);

        gbc.gridy = yPos++; gbc.gridx = 0; gbc.gridwidth = 2;
        JLabel catatanFinalTitleLabel = new JLabel("Catatan Final untuk Tim Kami (opsional):");
        catatanFinalTitleLabel.setFont(labelFont);
        catatanFinalTitleLabel.setBorder(new EmptyBorder(15,0,5,0));
        formPanel.add(catatanFinalTitleLabel, gbc);
        gbc.gridy = yPos++;
        catatanFinalArea = new JTextArea(4, 0);
        catatanFinalArea.setFont(valueFont);
        catatanFinalArea.setLineWrap(true);
        catatanFinalArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(catatanFinalArea), gbc);

        JScrollPane scrollForm = new JScrollPane(formPanel);
        scrollForm.setBorder(null);
        scrollForm.getViewport().setBackground(UIManager.getColor("Panel.background"));
        panel.add(scrollForm, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ----- PANEL KANAN (RINGKASAN TRIP) -----
    private JPanel createTripSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(UIManager.getColor("Panel.background"));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")),
            new EmptyBorder(10,10,10,10)
        ));
        
        JPanel summaryListPanel = createTitledPanel("Ringkasan Trip Anda");
        summaryListPanel.setLayout(new BorderLayout());
        summaryListPanel.setOpaque(false);
        
        modelRingkasan = new DefaultListModel<>();
        listRingkasanTrip = new JList<>(modelRingkasan);
        listRingkasanTrip.setFont(new Font("SansSerif", Font.PLAIN, 13));
        summaryListPanel.add(new JScrollPane(listRingkasanTrip), BorderLayout.CENTER);
        
        JButton hapusButton = new JButton("Hapus Destinasi");
        hapusButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hapusButton.addActionListener(e -> {
            Destinasi terpilih = listRingkasanTrip.getSelectedValue();
             if (terpilih != null) {
                controller.hapusDestinasi(terpilih);
                perbaruiTampilanRingkasan();
            } else {
                 JOptionPane.showMessageDialog(this, "Pilih destinasi dari ringkasan untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
             }
        });
        summaryListPanel.add(hapusButton, BorderLayout.SOUTH);
        
        JPanel detailsAndCostPanel = new JPanel();
        detailsAndCostPanel.setLayout(new BoxLayout(detailsAndCostPanel, BoxLayout.Y_AXIS));
        detailsAndCostPanel.setOpaque(false);
        detailsAndCostPanel.setBorder(new EmptyBorder(15,5,5,5));

        Font ringkasanDetailFont = new Font("SansSerif", Font.PLAIN, 13);
        labelRingkasanTanggal = new JLabel("Tanggal: Belum dipilih");
        labelRingkasanTanggal.setFont(ringkasanDetailFont);
        labelRingkasanTransport = new JLabel("Transportasi: -");
        labelRingkasanTransport.setFont(ringkasanDetailFont);
        labelRingkasanAkomodasi = new JLabel("Akomodasi: -");
        labelRingkasanAkomodasi.setFont(ringkasanDetailFont);
        labelRingkasanAktivitas = new JLabel("Aktivitas: -");
        labelRingkasanAktivitas.setFont(ringkasanDetailFont);

        detailsAndCostPanel.add(labelRingkasanTanggal);
        detailsAndCostPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        detailsAndCostPanel.add(labelRingkasanTransport);
        detailsAndCostPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        detailsAndCostPanel.add(labelRingkasanAkomodasi);
        detailsAndCostPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        detailsAndCostPanel.add(labelRingkasanAktivitas);
        detailsAndCostPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        
        JLabel totalBiayaTitleLabel = new JLabel("Total Estimasi Biaya:");
        totalBiayaTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailsAndCostPanel.add(totalBiayaTitleLabel);
        
        labelTotalBiaya = new JLabel("Rp 0,00");
        labelTotalBiaya.setFont(new Font("SansSerif", Font.BOLD, 22)); // Ukuran lebih kecil dari panel final
        labelTotalBiaya.setForeground(new Color(0, 102, 102));
        detailsAndCostPanel.add(labelTotalBiaya);
        
        JPanel centerSummaryPanel = new JPanel(new BorderLayout());
        centerSummaryPanel.setOpaque(false);
        centerSummaryPanel.add(summaryListPanel, BorderLayout.CENTER);
        centerSummaryPanel.add(detailsAndCostPanel, BorderLayout.SOUTH);

        panel.add(centerSummaryPanel, BorderLayout.CENTER);
        return panel;
    }

    // ----- PANEL BAWAH (NAVIGASI) -----
    private JPanel createNavigationButtonsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 0, 10, 0));
        panel.setOpaque(false);

        tombolKembali = new JButton("< Kembali");
        tombolKembali.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tombolKembali.setPreferredSize(new Dimension(150, 40));

        tombolSelanjutnya = new JButton("Langkah Selanjutnya >");
        tombolSelanjutnya.setFont(new Font("SansSerif", Font.BOLD, 14));
        tombolSelanjutnya.setBackground(new Color(0, 102, 102));
        tombolSelanjutnya.setForeground(Color.WHITE);
        tombolSelanjutnya.setPreferredSize(new Dimension(200, 40));

        JButton tombolBatalHome = new JButton("Batal & Kembali ke Beranda");
        tombolBatalHome.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tombolBatalHome.setPreferredSize(new Dimension(220, 40));

        tombolSimpanDraf = new JButton("Simpan Draf"); // Inisialisasi tombolSimpanDraf
        tombolSimpanDraf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tombolSimpanDraf.setPreferredSize(new Dimension(150, 40));


        // --- Action Listeners ---
        tombolKembali.addActionListener(e -> navigasiKeLangkahSebelumnya());
        tombolSelanjutnya.addActionListener(e -> navigasiKeLangkahSelanjutnya());

        tombolBatalHome.addActionListener(e -> {
            int pilihan = JOptionPane.showConfirmDialog(
                this, 
                "Apakah Anda yakin ingin membatalkan pembuatan trip kustom dan kembali ke Beranda?\nSemua perubahan yang belum disimpan akan hilang.", 
                "Konfirmasi Pembatalan", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                new HomeView().setVisible(true);
                this.dispose();
            }
        });

        tombolSimpanDraf.addActionListener(e -> {
            kumpulkanDataLangkahSaatIniKeController(); // Kumpulkan data dari langkah saat ini
            // Validasi dasar sebelum simpan draf
            if (controller.getDestinasiTerpilih().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih minimal satu destinasi untuk menyimpan draf.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.getTanggalMulai() == null || controller.getTanggalSelesai() == null) {
                JOptionPane.showMessageDialog(this, "Tentukan tanggal mulai dan selesai untuk menyimpan draf.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sukses = controller.simpanCustomTrip(); // simpanCustomTrip sudah mengatur status "draft"
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Trip kustom berhasil disimpan sebagai draf!", "Draf Tersimpan", JOptionPane.INFORMATION_MESSAGE);
                // Opsional: Mungkin Anda ingin pengguna kembali ke HomeView atau menonaktifkan tombol simpan draf
                // new HomeView().setVisible(true);
                // this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan draf. Harap coba lagi.", "Error Penyimpanan", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tata letak tombol
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        leftPanel.setOpaque(false);
        leftPanel.add(tombolKembali);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,0)); // Beri gap antar tombol
        centerPanel.setOpaque(false);
        centerPanel.add(tombolBatalHome);
        centerPanel.add(tombolSimpanDraf); // Tambahkan tombolSimpanDraf di tengah

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0,0));
        rightPanel.setOpaque(false);
        rightPanel.add(tombolSelanjutnya);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    // ----- LOGIKA NAVIGASI & PEMBARUAN UI -----
    private void navigasiKeLangkahSelanjutnya() {
        kumpulkanDataLangkahSaatIniKeController();
        
        // Simpan data dari langkah saat ini SEBELUM pindah
        if (currentStepIndex == 0) { // Validasi langkah destinasi
            if (controller.getDestinasiTerpilih().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih minimal satu destinasi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (currentStepIndex == 1) { // Dari Tanggal
            if (tanggalMulaiChooser.getDate() == null || tanggalSelesaiChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Harap pilih tanggal mulai dan selesai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (tanggalSelesaiChooser.getDate().before(tanggalMulaiChooser.getDate())) {
                JOptionPane.showMessageDialog(this, "Tanggal selesai tidak boleh sebelum tanggal mulai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.setTanggal(tanggalMulaiChooser.getDate(), tanggalSelesaiChooser.getDate());
        } else if (currentStepIndex == 2) { // Dari Transportasi
            if (transportGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Harap pilih opsi transportasi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.setPilihanTransportasi(transportGroup.getSelection().getActionCommand());
        } else if (currentStepIndex == 3) { // Dari Akomodasi
             if (akomodasiGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Harap pilih preferensi akomodasi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.setPreferensiAkomodasi(akomodasiGroup.getSelection().getActionCommand());
            controller.setTermasukSarapan(sarapanCheckBox.isSelected());
            controller.setCatatanAkomodasi(catatanAkomodasiArea.getText().trim());
        } else if (currentStepIndex == 4) { // Dari Aktivitas
            controller.getJenisAktivitas().clear(); 
            for(JCheckBox cb : aktivitasCheckBoxes) {
                if (cb.isSelected()) {
                    controller.addJenisAktivitas(cb.getText());
                }
            }
            controller.setCatatanAktivitas(catatanAktivitasArea.getText().trim());
        }

        if (currentStepIndex < steps.length - 1) {
            currentStepIndex++;
            mainContentCardLayout.show(mainContentPanel, steps[currentStepIndex]);
            stepIndicatorPanel.setCurrentStep(currentStepIndex);
            if (currentStepIndex == 5) { 
                updateFinalStepPanelContent();
                tombolSelanjutnya.setText("Buat Trip Sekarang!");
                tombolSelanjutnya.setBackground(new Color(0,153,51));
                tombolSelanjutnya.setForeground(Color.WHITE);
            } else {
                tombolSelanjutnya.setText("Langkah Selanjutnya >");
                tombolSelanjutnya.setBackground(UIManager.getColor("Button.background"));
                tombolSelanjutnya.setForeground(UIManager.getColor("Button.foreground"));
            }
        } else if (currentStepIndex == 5) { 
            controller.setNamaTripCustom(namaTripField.getText().trim());
            controller.setJumlahPesertaCustom((int) jumlahPesertaSpinner.getValue());
            controller.setCatatanFinalUser(catatanFinalArea.getText().trim());

            if (controller.getNamaTripCustom().isEmpty()){
                 JOptionPane.showMessageDialog(this, "Nama trip tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                 namaTripField.requestFocus();
                 return;
            }

            boolean sukses = controller.simpanCustomTrip();
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Trip Kustom Anda berhasil dibuat dan disimpan sebagai draf!\nAnda akan diarahkan ke halaman utama.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                new HomeView().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan trip kustom. Harap periksa kembali semua isian atau koneksi database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        perbaruiStatusTombolNavigasi();
        perbaruiTampilanRingkasan();
    }
    
    private void navigasiKeLangkahSebelumnya() {
        kumpulkanDataLangkahSaatIniKeController();
        
        if (currentStepIndex == 1) controller.setTanggal(tanggalMulaiChooser.getDate(), tanggalSelesaiChooser.getDate());
        else if (currentStepIndex == 2 && transportGroup.getSelection() != null) controller.setPilihanTransportasi(transportGroup.getSelection().getActionCommand());
        else if (currentStepIndex == 3 && akomodasiGroup.getSelection() != null) {
            controller.setPreferensiAkomodasi(akomodasiGroup.getSelection().getActionCommand());
            controller.setTermasukSarapan(sarapanCheckBox.isSelected());
            controller.setCatatanAkomodasi(catatanAkomodasiArea.getText().trim());
        } else if (currentStepIndex == 4) {
             controller.getJenisAktivitas().clear();
            for(JCheckBox cb : aktivitasCheckBoxes) if (cb.isSelected()) controller.addJenisAktivitas(cb.getText());
            controller.setCatatanAktivitas(catatanAktivitasArea.getText().trim());
        } else if (currentStepIndex == 5) { // Dari panel Final kembali ke Aktivitas
            controller.setNamaTripCustom(namaTripField.getText().trim());
            controller.setJumlahPesertaCustom((int) jumlahPesertaSpinner.getValue());
            controller.setCatatanFinalUser(catatanFinalArea.getText().trim());
        }

        if (currentStepIndex > 0) {
            currentStepIndex--;
            mainContentCardLayout.show(mainContentPanel, steps[currentStepIndex]);
            stepIndicatorPanel.setCurrentStep(currentStepIndex);
            tombolSelanjutnya.setText("Langkah Selanjutnya >");
            tombolSelanjutnya.setBackground(UIManager.getColor("Button.background"));
            tombolSelanjutnya.setForeground(UIManager.getColor("Button.foreground"));
        }
        perbaruiStatusTombolNavigasi();
        perbaruiTampilanRingkasan();
    }
    
    private void perbaruiStatusTombolNavigasi() {
        tombolKembali.setEnabled(currentStepIndex > 0);
        // Tombol Selanjutnya selalu enable, tapi teksnya berubah di langkah akhir
        // tombolSelanjutnya.setEnabled(currentStepIndex < steps.length - 1); // Logika lama
    }

    private void perbaruiTampilanRingkasan() {
        modelRingkasan.clear();
        for(Destinasi d : controller.getDestinasiTerpilih()) { modelRingkasan.addElement(d); }
        
        BigDecimal totalBiaya = controller.hitungTotalBiaya();
        labelTotalBiaya.setText(formatRupiah.format(totalBiaya));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
        Date mulai = controller.getTanggalMulai();
        Date selesai = controller.getTanggalSelesai();
        if (mulai != null && selesai != null) {
            labelRingkasanTanggal.setText("Tanggal: " + sdf.format(mulai) + " - " + sdf.format(selesai));
        } else {
            labelRingkasanTanggal.setText("Tanggal: Belum dipilih");
        }
        
        labelRingkasanTransport.setText("Transportasi: " + (controller.getPilihanTransportasi() != null ? controller.getPilihanTransportasi() : "-"));
        
        String prefAkomodasi = controller.getPreferensiAkomodasi() != null ? controller.getPreferensiAkomodasi() : "-";
        if (controller.getPreferensiAkomodasi() != null && !prefAkomodasi.equals("Saya Akan Urus Sendiri Akomodasi")) {
             prefAkomodasi += controller.isTermasukSarapan() ? " (+Sarapan)" : "";
        }
        labelRingkasanAkomodasi.setText("Akomodasi: " + prefAkomodasi);
        
        List<String> aktivitas = controller.getJenisAktivitas();
        if (!aktivitas.isEmpty()) {
            labelRingkasanAktivitas.setText("Aktivitas: " + String.join(", ", aktivitas));
        } else {
            labelRingkasanAktivitas.setText("Aktivitas: Dipilih " + aktivitas.size());
        }
    }
    
    private void updateFinalStepPanelContent(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        finalDestinasiLabel.setText("<html><body style='width: 350px'>" + (modelRingkasan.isEmpty() ? "-" : formatListUntukHtml(modelRingkasan)) + "</body></html>");
        finalTanggalLabel.setText("<html><body style='width: 350px'>" + (controller.getTanggalMulai() != null ? sdf.format(controller.getTanggalMulai()) + " s/d " + sdf.format(controller.getTanggalSelesai()) : "-") + "</body></html>");
        finalTransportLabel.setText("<html><body style='width: 350px'>" + (controller.getPilihanTransportasi() != null ? controller.getPilihanTransportasi() : "-") + "</body></html>");
        
        String akomodasiStr = controller.getPreferensiAkomodasi() != null ? controller.getPreferensiAkomodasi() : "-";
        if(controller.getPreferensiAkomodasi() != null && !akomodasiStr.equals("Saya Akan Urus Sendiri Akomodasi")){
            akomodasiStr += (controller.isTermasukSarapan() ? " (Termasuk Sarapan)" : " (Tanpa Sarapan)");
            if(controller.getCatatanAkomodasi()!=null && !controller.getCatatanAkomodasi().isEmpty()){
                akomodasiStr += "<br><i>Catatan: " + controller.getCatatanAkomodasi() + "</i>";
            }
        }
        finalAkomodasiLabel.setText("<html><body style='width: 350px'>" + akomodasiStr + "</body></html>");
        
        String aktivitasStr = controller.getJenisAktivitas().isEmpty() ? "-" : String.join(", ", controller.getJenisAktivitas());
        if(!controller.getJenisAktivitas().isEmpty() && controller.getCatatanAktivitas()!=null && !controller.getCatatanAktivitas().isEmpty()){
            aktivitasStr += "<br><i>Catatan: " + controller.getCatatanAktivitas() + "</i>";
        }
        finalAktivitasLabel.setText("<html><body style='width: 350px'>" + aktivitasStr + "</body></html>");
        finalHargaLabelFinal.setText("Total Estimasi: " + formatRupiah.format(controller.hitungTotalBiaya()));
        
        // Set nilai yang mungkin sudah diisi sebelumnya dari controller
        namaTripField.setText(controller.getNamaTripCustom());
        jumlahPesertaSpinner.setValue(controller.getJumlahPesertaCustom());
        catatanFinalArea.setText(controller.getCatatanFinalUser() != null ? controller.getCatatanFinalUser() : "");

    }


    private String formatListUntukHtml(DefaultListModel<Destinasi> model) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < model.getSize(); i++) {
            sb.append(model.getElementAt(i).getNamaDestinasi());
            if (i < model.getSize() - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
    private void kumpulkanDataLangkahSaatIniKeController() {
    switch (currentStepIndex) {
        case 0: // Destinasi - Sudah otomatis terupdate di controller saat ditambah/hapus
            break;
        case 1: // Tanggal
            controller.setTanggal(tanggalMulaiChooser.getDate(), tanggalSelesaiChooser.getDate());
            break;
        case 2: // Transportasi
            if (transportGroup.getSelection() != null) {
                controller.setPilihanTransportasi(transportGroup.getSelection().getActionCommand());
            }
            break;
        case 3: // Akomodasi
            if (akomodasiGroup.getSelection() != null) {
                controller.setPreferensiAkomodasi(akomodasiGroup.getSelection().getActionCommand());
            }
            controller.setTermasukSarapan(sarapanCheckBox.isSelected());
            controller.setCatatanAkomodasi(catatanAkomodasiArea.getText().trim());
            break;
        case 4: // Aktivitas
            controller.getJenisAktivitas().clear();
            for (JCheckBox cb : aktivitasCheckBoxes) {
                if (cb.isSelected()) {
                    controller.addJenisAktivitas(cb.getText());
                }
            }
            controller.setCatatanAktivitas(catatanAktivitasArea.getText().trim());
            break;
        case 5: // Final
            controller.setNamaTripCustom(namaTripField.getText().trim());
            controller.setJumlahPesertaCustom((int) jumlahPesertaSpinner.getValue());
            controller.setCatatanFinalUser(catatanFinalArea.getText().trim());
            break;
    }
    perbaruiTampilanRingkasan(); // Selalu update ringkasan setelah data terkumpul
    if(currentStepIndex == 5) { // Jika di panel final, update juga kontennya
        updateFinalStepPanelContent();
    }
}

    // Helper method
    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), title, 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("SansSerif", Font.BOLD, 14))
        );
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    private JLabel createFinalInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }
    private JLabel createFinalValueLabel(String text) {
        JLabel label = new JLabel("<html><body style='width: 350px'>" + text + "</body></html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return label;
    }
    
}