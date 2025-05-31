/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.toedter.calendar.JDateChooser; // Anda perlu menambahkan library JCalendar
import controller.BookingController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
    private final int jumlahPenumpang;
    private final BookingController controller;
    private final List<JPanel> formPenumpangPanels = new ArrayList<>();

    public BookingView(PaketPerjalanan paket, int jumlahPenumpang) {
        this.paket = paket;
        this.jumlahPenumpang = jumlahPenumpang;
        this.controller = new BookingController();
        
        setTitle("Lengkapi Pemesanan Anda");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        mainPanel.add(createPassengerFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createSummaryPanel(), BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private JScrollPane createPassengerFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Data Penumpang");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title);
        
        // Buat form untuk setiap penumpang
        for (int i = 1; i <= jumlahPenumpang; i++) {
            JPanel formPanel = createSinglePassengerForm(i);
            formPenumpangPanels.add(formPanel); // Simpan referensi ke panel
            panel.add(formPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel createSinglePassengerForm(int passengerNumber) {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Penumpang " + passengerNumber));
        
        // Tambahkan komponen ke dalam panel dengan nama unik untuk bisa diambil datanya nanti
        formPanel.add(new JLabel("Nama Lengkap:"));
        JTextField namaField = new JTextField();
        namaField.setName("nama");
        formPanel.add(namaField);
        
        formPanel.add(new JLabel("Jenis Kelamin:"));
        JRadioButton pria = new JRadioButton("Pria");
        pria.setActionCommand("pria");
        JRadioButton wanita = new JRadioButton("Wanita");
        wanita.setActionCommand("wanita");
        ButtonGroup group = new ButtonGroup();
        group.add(pria);
        group.add(wanita);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(pria);
        genderPanel.add(wanita);
        genderPanel.setName("gender");
        formPanel.add(genderPanel);
        
        formPanel.add(new JLabel("Tanggal Lahir:"));
        JDateChooser tglLahir = new JDateChooser();
        tglLahir.setName("tanggalLahir");
        formPanel.add(tglLahir);

        formPanel.add(new JLabel("Nomor Telepon:"));
        JTextField teleponField = new JTextField();
        teleponField.setName("telepon");
        formPanel.add(teleponField);
        
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        emailField.setName("email");
        formPanel.add(emailField);
        
        return formPanel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createTitledBorder(null, "Ringkasan Trip", TitledBorder.CENTER, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 16)));
        
        // Info Trip
        JPanel tripInfoPanel = new JPanel();
        tripInfoPanel.setLayout(new BoxLayout(tripInfoPanel, BoxLayout.Y_AXIS));
        tripInfoPanel.add(new JLabel(paket.getNamaPaket())).setFont(new Font("SansSerif", Font.BOLD, 18));
        tripInfoPanel.add(new JLabel(new Date().toString())); // Tanggal contoh
        tripInfoPanel.add(new JLabel(jumlahPenumpang + " Penumpang"));

        // Info Harga
        JPanel pricePanel = new JPanel(new BorderLayout());
        BigDecimal totalHarga = paket.getHarga().multiply(new BigDecimal(jumlahPenumpang));
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        JLabel hargaLabel = new JLabel(formatRupiah.format(totalHarga));
        hargaLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        pricePanel.add(new JLabel("Total Harga:"), BorderLayout.NORTH);
        pricePanel.add(hargaLabel, BorderLayout.CENTER);
        
        // Panel Bawah (Checkbox & Tombol)
        JPanel bottomPanel = new JPanel(new BorderLayout(10,10));
        JCheckBox termsCheckbox = new JCheckBox("Saya menerima Syarat dan Ketentuan.");
        JButton confirmButton = new JButton("Konfirmasi & Pesan");
        confirmButton.setBackground(new Color(0, 102, 102));
        confirmButton.setForeground(Color.WHITE);
        
        confirmButton.addActionListener(e -> {
            if (!termsCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Anda harus menyetujui Syarat dan Ketentuan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Kumpulkan data dari semua form penumpang
            List<Penumpang> penumpangList = new ArrayList<>();
            for (JPanel form : formPenumpangPanels) {
                Penumpang p = new Penumpang();
                // Loop melalui komponen untuk mendapatkan data
                for(Component comp : form.getComponents()) {
                    if(comp.getName() != null) {
                        switch(comp.getName()) {
                            case "nama": p.setNamaPenumpang(((JTextField)comp).getText()); break;
                            case "telepon": p.setNomorTelepon(((JTextField)comp).getText()); break;
                            case "email": p.setEmail(((JTextField)comp).getText()); break;
                            case "tanggalLahir": p.setTanggalLahir(((JDateChooser)comp).getDate()); break;
                            case "gender":
                                JPanel genderPanel = (JPanel) comp;
                                for(Component radio : genderPanel.getComponents()){
                                    JRadioButton r = (JRadioButton) radio;
                                    if(r.isSelected()){
                                        p.setJenisKelamin(r.getActionCommand());
                                    }
                                }
                                break;
                        }
                    }
                }
                penumpangList.add(p);
            }
            controller.processBooking(paket, penumpangList);
        });

        bottomPanel.add(termsCheckbox, BorderLayout.NORTH);
        bottomPanel.add(confirmButton, BorderLayout.SOUTH);
        
        panel.add(tripInfoPanel, BorderLayout.NORTH);
        panel.add(pricePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
}
