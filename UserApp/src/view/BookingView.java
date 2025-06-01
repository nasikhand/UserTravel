/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.toedter.calendar.JDateChooser; // Pastikan JCalendar library sudah ada
import controller.BookingController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import model.PaketPerjalanan;
import model.Penumpang;

public class BookingView extends JFrame {

    private final PaketPerjalanan paket;
    private final int jumlahPenumpangInput; // Nama diubah agar lebih jelas
    private final BookingController bookingController; // Diubah menjadi bookingController
    private final List<JPanel> listPanelFormPenumpang = new ArrayList<>(); // Nama diubah
    private final NumberFormat formatRupiah;

    public BookingView(PaketPerjalanan paket, int jumlahPenumpang) {
        this.paket = paket;
        this.jumlahPenumpangInput = jumlahPenumpang;
        this.bookingController = new BookingController(); // Inisialisasi controller
        this.formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        
        setTitle("Lengkapi Detail Pemesanan Paket");
        setSize(1050, 700); // Sedikit lebih lebar
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 15)); // Tambah gap horizontal
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245)); // Background abu-abu muda
        
        mainPanel.add(createPanelFormPenumpang(), BorderLayout.CENTER);
        mainPanel.add(createPanelRingkasan(), BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private JScrollPane createPanelFormPenumpang() {
        JPanel panelKonten = new JPanel();
        panelKonten.setLayout(new BoxLayout(panelKonten, BoxLayout.Y_AXIS));
        panelKonten.setBackground(Color.WHITE);
        panelKonten.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(15,15,15,15)
        ));
        
        JLabel title = new JLabel("Isi Data Penumpang");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelKonten.add(title);
        panelKonten.add(Box.createRigidArea(new Dimension(0, 15)));
        
        listPanelFormPenumpang.clear();
        for (int i = 1; i <= jumlahPenumpangInput; i++) {
            JPanel singleFormPanel = createFormPenumpangTunggal(i);
            listPanelFormPenumpang.add(singleFormPanel); 
            panelKonten.add(singleFormPanel);
            panelKonten.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        
        JScrollPane scrollPane = new JScrollPane(panelKonten);
        scrollPane.setBorder(null); // Hapus border default scrollpane
        return scrollPane;
    }

    private JPanel createFormPenumpangTunggal(int nomorPenumpang) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Transparan agar background panelKonten terlihat
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Penumpang " + nomorPenumpang,
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("SansSerif", Font.BOLD, 14)
        ));
        
        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        // Nama Lengkap
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel namaLabel = new JLabel("Nama Lengkap:");
        namaLabel.setFont(labelFont);
        formPanel.add(namaLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; // Agar field bisa expand
        JTextField namaField = new JTextField();
        namaField.setName("namaPenumpang");
        namaField.setFont(fieldFont);
        formPanel.add(namaField, gbc);
        gbc.weightx = 0; // Reset

        // Jenis Kelamin
        gbc.gridy++; gbc.gridx = 0;
        JLabel genderLabel = new JLabel("Jenis Kelamin:");
        genderLabel.setFont(labelFont);
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        JRadioButton priaRadio = new JRadioButton("Pria");
        priaRadio.setFont(labelFont); priaRadio.setOpaque(false);
        priaRadio.setActionCommand("pria");
        JRadioButton wanitaRadio = new JRadioButton("Wanita");
        wanitaRadio.setFont(labelFont); wanitaRadio.setOpaque(false);
        wanitaRadio.setActionCommand("wanita");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(priaRadio);
        genderGroup.add(wanitaRadio);
        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelGender.setOpaque(false);
        panelGender.add(priaRadio);
        panelGender.add(wanitaRadio);
        panelGender.setName("grupJenisKelamin"); // Untuk identifikasi ButtonGroup
        formPanel.add(panelGender, gbc);
        
        // Tanggal Lahir
        gbc.gridy++; gbc.gridx = 0;
        JLabel tglLahirLabel = new JLabel("Tanggal Lahir:");
        tglLahirLabel.setFont(labelFont);
        formPanel.add(tglLahirLabel, gbc);
        gbc.gridx = 1;
        JDateChooser tglLahirChooser = new JDateChooser();
        tglLahirChooser.setFont(fieldFont);
        tglLahirChooser.setDateFormatString("dd MMMM yyyy");
        tglLahirChooser.setName("tanggalLahirPenumpang");
        formPanel.add(tglLahirChooser, gbc);

        // Nomor Telepon
        gbc.gridy++; gbc.gridx = 0;
        JLabel teleponLabel = new JLabel("Nomor Telepon:");
        teleponLabel.setFont(labelFont);
        formPanel.add(teleponLabel, gbc);
        gbc.gridx = 1;
        JTextField teleponField = new JTextField();
        teleponField.setName("nomorTeleponPenumpang");
        teleponField.setFont(fieldFont);
        formPanel.add(teleponField, gbc);
        
        // Email
        gbc.gridy++; gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email (Opsional):");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField();
        emailField.setName("emailPenumpang");
        emailField.setFont(fieldFont);
        formPanel.add(emailField, gbc);
        
        return formPanel;
    }
    
    private JPanel createPanelRingkasan() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setPreferredSize(new Dimension(320, 0)); // Lebar panel ringkasan
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(15,15,15,15)
        ));
        
        JLabel title = new JLabel("Ringkasan Pemesanan");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(0,0,10,0));
        
        // Info Trip
        JPanel panelInfoTrip = new JPanel();
        panelInfoTrip.setLayout(new BoxLayout(panelInfoTrip, BoxLayout.Y_AXIS));
        panelInfoTrip.setOpaque(false);
        
        JLabel namaPaketLabel = new JLabel("<html><body style='width:250px'><b>Paket:</b> " + paket.getNamaPaket() + "</body></html>");
        namaPaketLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelInfoTrip.add(namaPaketLabel);
        panelInfoTrip.add(Box.createRigidArea(new Dimension(0,5)));
        
        JLabel jumlahPenumpangLabel = new JLabel("<b>Jumlah Penumpang:</b> " + jumlahPenumpangInput + " orang");
        jumlahPenumpangLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelInfoTrip.add(jumlahPenumpangLabel);
        panelInfoTrip.add(Box.createRigidArea(new Dimension(0,15)));

        // Info Harga
        JPanel panelHarga = new JPanel(new BorderLayout());
        panelHarga.setOpaque(false);
        BigDecimal totalHarga = paket.getHarga().multiply(new BigDecimal(jumlahPenumpangInput));
        
        JLabel labelTotal = new JLabel("Total Pembayaran:");
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        JLabel nilaiHargaLabel = new JLabel(formatRupiah.format(totalHarga));
        nilaiHargaLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        nilaiHargaLabel.setForeground(new Color(0, 102, 102));
        
        panelHarga.add(labelTotal, BorderLayout.NORTH);
        panelHarga.add(nilaiHargaLabel, BorderLayout.CENTER);
        
        // Panel Bawah (Checkbox & Tombol)
        JPanel panelBawah = new JPanel(new BorderLayout(10,10));
        panelBawah.setOpaque(false);
        JCheckBox termsCheckbox = new JCheckBox("Saya menyetujui Syarat dan Ketentuan yang berlaku.");
        termsCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        termsCheckbox.setOpaque(false);

        JButton tombolKonfirmasi = new JButton("Konfirmasi & Pesan Sekarang");
        tombolKonfirmasi.setFont(new Font("SansSerif", Font.BOLD, 14));
        tombolKonfirmasi.setBackground(new Color(0, 102, 102));
        tombolKonfirmasi.setForeground(Color.WHITE);
        tombolKonfirmasi.setPreferredSize(new Dimension(0, 45)); // Tinggi tombol
        
        tombolKonfirmasi.addActionListener(e -> {
            if (!termsCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Anda harus menyetujui Syarat dan Ketentuan untuk melanjutkan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Penumpang> daftarPenumpang = new ArrayList<>();
            for (JPanel panelForm : listPanelFormPenumpang) {
                Penumpang p = new Penumpang();
                // Loop untuk mengambil data dari setiap field di panelForm
                for(Component comp : panelForm.getComponents()) {
                    if(comp.getName() != null) {
                        switch(comp.getName()) {
                            case "namaPenumpang": p.setNamaPenumpang(((JTextField)comp).getText().trim()); break;
                            case "nomorTeleponPenumpang": p.setNomorTelepon(((JTextField)comp).getText().trim()); break;
                            case "emailPenumpang": p.setEmail(((JTextField)comp).getText().trim()); break;
                            case "tanggalLahirPenumpang": p.setTanggalLahir(((JDateChooser)comp).getDate()); break;
                            case "grupJenisKelamin": // Handle ButtonGroup
                                JPanel panelRadio = (JPanel) comp;
                                for(Component radioComp : panelRadio.getComponents()){
                                    if(radioComp instanceof JRadioButton){
                                        JRadioButton rb = (JRadioButton) radioComp;
                                        if(rb.isSelected()){
                                            p.setJenisKelamin(rb.getActionCommand());
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
                // Validasi dasar per penumpang
                if (p.getNamaPenumpang().isEmpty() || p.getJenisKelamin() == null || p.getTanggalLahir() == null) {
                     JOptionPane.showMessageDialog(this, "Harap lengkapi Nama, Jenis Kelamin, dan Tanggal Lahir untuk semua penumpang.", "Data Tidak Lengkap", JOptionPane.WARNING_MESSAGE);
                     return; // Hentikan proses jika ada data wajib yang kosong
                }
                daftarPenumpang.add(p);
            }
            bookingController.processPaketBooking(paket, jumlahPenumpangInput, daftarPenumpang, this);
        });

        panelBawah.add(termsCheckbox, BorderLayout.NORTH);
        panelBawah.add(tombolKonfirmasi, BorderLayout.CENTER);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(panelInfoTrip, BorderLayout.CENTER);
        
        JPanel southContainer = new JPanel(new BorderLayout(0,15)); // Container untuk harga dan tombol
        southContainer.setOpaque(false);
        southContainer.add(panelHarga, BorderLayout.NORTH);
        southContainer.add(panelBawah, BorderLayout.SOUTH);
        
        panel.add(southContainer, BorderLayout.SOUTH);

        return panel;
    }
}