/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatClientProperties;
import controller.ProfileController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.User;

public class ProfileView extends JFrame {

    private ProfileController controller;
    private String loggedInUserEmail;

    private JLabel profileImageLabel;
    private JButton ubahFotoButton;
    private JTextField namaLengkapField;
    private JTextField emailField;
    private JTextField noTeleponField;
    private JTextArea alamatArea;
    private JPasswordField oldPasswordField, newPasswordField, confirmNewPasswordField;

    private String currentImagePath = null;
    private String newSelectedImagePath = null;

    private final int IMAGE_DISPLAY_SIZE = 180; // Ukuran tampilan foto profil

    public ProfileView(String emailPenggunaLogin) {
        this.loggedInUserEmail = emailPenggunaLogin;
        this.controller = new ProfileController();

        setTitle("Profil Pengguna - Sinar Jaya Travel");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 10));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(UIManager.getColor("control"));

        JLabel headerLabel = new JLabel("Pengaturan Akun Anda", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setBorder(new EmptyBorder(0,0,20,0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel topContentPanel = new JPanel(new BorderLayout(25, 0));
        topContentPanel.setOpaque(false);
        topContentPanel.add(createProfilePhotoPanel(), BorderLayout.WEST);
        topContentPanel.add(createProfileFormPanel(), BorderLayout.CENTER);

        JPanel bottomContentPanel = new JPanel(new BorderLayout());
        bottomContentPanel.setOpaque(false);
        bottomContentPanel.add(createChangePasswordPanel(), BorderLayout.CENTER);
        
        JPanel formAndChangePasswordPanel = new JPanel();
        formAndChangePasswordPanel.setLayout(new BoxLayout(formAndChangePasswordPanel, BoxLayout.Y_AXIS));
        formAndChangePasswordPanel.setOpaque(false);
        formAndChangePasswordPanel.add(topContentPanel);
        formAndChangePasswordPanel.add(Box.createRigidArea(new Dimension(0,25)));
        formAndChangePasswordPanel.add(bottomContentPanel);

        mainPanel.add(new JScrollPane(formAndChangePasswordPanel), BorderLayout.CENTER);

        JPanel buttonNavPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonNavPanel.setOpaque(false);
        JButton backButton = new JButton("Kembali ke Beranda");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setPreferredSize(new Dimension(180, 40));
        backButton.addActionListener(e -> {
            new HomeView(this.loggedInUserEmail).setVisible(true);
            this.dispose();
        });
        buttonNavPanel.add(backButton);
        mainPanel.add(buttonNavPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadProfileData(); // Panggil setelah semua komponen UI diinisialisasi
    }

        // Di dalam kelas ProfileView.java
    // Ganti seluruh method createProfilePhotoPanel dengan versi baru ini
    private JPanel createProfilePhotoPanel() {
        JPanel photoSectionPanel = new JPanel();
        photoSectionPanel.setLayout(new BoxLayout(photoSectionPanel, BoxLayout.Y_AXIS));
        photoSectionPanel.setOpaque(false); // Agar background utama terlihat jika ada
        photoSectionPanel.setPreferredSize(new Dimension(IMAGE_DISPLAY_SIZE + 40, 0)); 
        photoSectionPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        photoSectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Pusatkan panel ini jika parentnya BoxLayout X


        profileImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Jika tidak ada ikon, gambar placeholder atau teks
                if (getIcon() == null) {
                    super.paintComponent(g); 
                    Color placeholderTextColor = UIManager.getColor("Label.disabledForeground");
                    if (placeholderTextColor == null) {
                        placeholderTextColor = Color.GRAY; // Warna default jika tidak ada di tema
                    }
                    g.setColor(placeholderTextColor);
                    // --------------------------
                    
                    String placeholderText = "Foto";
                    FontMetrics fm = g.getFontMetrics();
                    int textWidth = fm.stringWidth(placeholderText);
                    int textHeight = fm.getAscent();
                    g.drawString(placeholderText, (getWidth() - textWidth) / 2, (getHeight() - textHeight) / 2 + fm.getAscent());
                    
                    // Gambar border lingkaran manual jika tidak ada ikon
                    Graphics2D g2ForBorder = (Graphics2D) g.create();
                    g2ForBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color outerBorderColor = UIManager.getColor("Component.borderColor");
                    if (outerBorderColor == null) {
                        outerBorderColor = Color.LIGHT_GRAY;
                    }
                    g2ForBorder.setColor(outerBorderColor);
                    g2ForBorder.drawOval(0, 0, getWidth()-1, getHeight()-1); // -1 agar border pas
                    g2ForBorder.dispose();
                    return;
                }

                // Jika ada ikon, gambar dengan kliping lingkaran
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Area kliping berbentuk lingkaran
                Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
                g2.clip(circle);
                
                // Gambar ikon (foto) ke dalam area yang sudah diklip
                getIcon().paintIcon(this, g2, 0, 0);
                
                // Opsional: Tambahkan border di dalam lingkaran setelah gambar
                // Color innerBorderColor = UIManager.getColor("Component.focusColor"); // Contoh warna
                // if (innerBorderColor != null) {
                //     g2.setColor(innerBorderColor);
                //     g2.drawOval(0, 0, getWidth() -1, getHeight() -1); // -1 agar border pas
                // }
                
                g2.dispose();
            }
        };
        profileImageLabel.setPreferredSize(new Dimension(IMAGE_DISPLAY_SIZE, IMAGE_DISPLAY_SIZE));
        profileImageLabel.setMinimumSize(new Dimension(IMAGE_DISPLAY_SIZE, IMAGE_DISPLAY_SIZE));
        profileImageLabel.setMaximumSize(new Dimension(IMAGE_DISPLAY_SIZE, IMAGE_DISPLAY_SIZE));
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        profileImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Pusatkan JLabel dalam photoSectionPanel
        // Kita tidak menggunakan setBorder() di sini karena custom painting sudah menangani tampilan bulat.
        // Jika Anda tetap ingin border kotak di luar lingkaran (jarang diinginkan):
        // Color borderColor = UIManager.getColor("Component.borderColor");
        // if (borderColor == null) borderColor = Color.LIGHT_GRAY;
        // profileImageLabel.setBorder(BorderFactory.createLineBorder(borderColor, 1));


        ubahFotoButton = new JButton("Ganti Foto");
        ubahFotoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Pusatkan tombol
        ubahFotoButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        ubahFotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ubahFotoButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15;"); 

        ubahFotoButton.addActionListener(e -> pilihFotoProfil());

        photoSectionPanel.add(Box.createVerticalGlue()); // Dorong konten ke tengah
        photoSectionPanel.add(profileImageLabel);
        photoSectionPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        photoSectionPanel.add(ubahFotoButton);
        photoSectionPanel.add(Box.createVerticalGlue());

        return photoSectionPanel;
    }
    
    private void pilihFotoProfil() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Foto Profil Baru");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Gambar (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            newSelectedImagePath = selectedFile.getAbsolutePath();
            displayImage(newSelectedImagePath); 
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    Image scaledImage = originalImage.getScaledInstance(IMAGE_DISPLAY_SIZE, IMAGE_DISPLAY_SIZE, Image.SCALE_SMOOTH);
                    profileImageLabel.setIcon(new ImageIcon(scaledImage));
                    profileImageLabel.setText(""); // Hapus teks jika gambar berhasil dimuat
                } else {
                    System.err.println("File gambar tidak ditemukan di path: " + imagePath);
                    loadDefaultImage(); 
                }
            } catch (Exception ex) {
                System.err.println("Error memuat gambar: " + ex.getMessage());
                loadDefaultImage();
            }
        } else {
            loadDefaultImage();
        }
    }

    private void loadDefaultImage() {
        try {
            ImageIcon defaultIcon = new ImageIcon(new ImageIcon("assets/default_profile.png").getImage().getScaledInstance(IMAGE_DISPLAY_SIZE, IMAGE_DISPLAY_SIZE, Image.SCALE_SMOOTH));
            profileImageLabel.setIcon(defaultIcon);
            profileImageLabel.setText("");
        } catch (Exception e) {
            profileImageLabel.setIcon(null); 
            profileImageLabel.setText("Foto Error");
            System.err.println("Error memuat gambar default: " + e.getMessage());
        }
    }

    private JPanel createProfileFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        
        Color borderColor = UIManager.getColor("Component.borderColor");
        if (borderColor == null) borderColor = Color.LIGHT_GRAY; 
        
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(borderColor), 
            "Data Pribadi",
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("SansSerif", Font.BOLD, 18), 
            UIManager.getColor("Label.foreground") 
        ));
        panel.setBackground(UIManager.getColor("Panel.background"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        Dimension fieldDimension = new Dimension(0, 35);

        gbc.gridy = 0; gbc.gridx = 0;
        JLabel namaLabel = new JLabel("Nama Lengkap:");
        namaLabel.setFont(labelFont);
        panel.add(namaLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        namaLengkapField = new JTextField(25);
        namaLengkapField.setFont(fieldFont);
        namaLengkapField.setPreferredSize(fieldDimension);
        panel.add(namaLengkapField, gbc);
        gbc.weightx = 0;

        gbc.gridy++; gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(25);
        emailField.setFont(fieldFont);
        emailField.setEditable(false);
        emailField.setBackground(UIManager.getColor("TextField.disabledBackground"));
        emailField.setPreferredSize(fieldDimension);
        panel.add(emailField, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel teleponLabel = new JLabel("Nomor Telepon:");
        teleponLabel.setFont(labelFont);
        panel.add(teleponLabel, gbc);
        gbc.gridx = 1;
        noTeleponField = new JTextField(25);
        noTeleponField.setFont(fieldFont);
        noTeleponField.setPreferredSize(fieldDimension);
        panel.add(noTeleponField, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel alamatLabel = new JLabel("Alamat:");
        alamatLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(alamatLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        alamatArea = new JTextArea(4, 0); 
        alamatArea.setFont(fieldFont);
        alamatArea.setLineWrap(true);
        alamatArea.setWrapStyleWord(true);
        JScrollPane scrollAlamat = new JScrollPane(alamatArea);
        scrollAlamat.setMinimumSize(new Dimension(100, 80)); 
        panel.add(scrollAlamat, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.EAST; 
        gbc.fill = GridBagConstraints.NONE; 
        JButton saveProfileButton = new JButton("Simpan Perubahan Profil");
        saveProfileButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveProfileButton.setBackground(new Color(0,102,102));
        saveProfileButton.setForeground(Color.WHITE);
        saveProfileButton.setPreferredSize(new Dimension(220, 40));
        saveProfileButton.putClientProperty(FlatClientProperties.STYLE, "arc: 10;");
        saveProfileButton.addActionListener(e -> simpanPerubahanProfilAksi());
        panel.add(saveProfileButton, gbc);

        return panel;
    }
    
    private void simpanPerubahanProfilAksi() {
        String pathGambarUntukDisimpan = (newSelectedImagePath != null && !newSelectedImagePath.isEmpty()) ? newSelectedImagePath : currentImagePath;
        
        boolean sukses = controller.saveProfileChanges(
            namaLengkapField.getText(),
            noTeleponField.getText(),
            alamatArea.getText(),
            pathGambarUntukDisimpan
        );
        
        if(sukses) {
            currentImagePath = pathGambarUntukDisimpan; 
            newSelectedImagePath = null; 
        }
    }

    private JPanel createChangePasswordPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        
        Color borderColor = UIManager.getColor("Component.borderColor");
        if (borderColor == null) borderColor = Color.LIGHT_GRAY;

        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(borderColor),
            "Keamanan Akun", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 18), 
            UIManager.getColor("Label.foreground")
        ));
        panel.setBackground(UIManager.getColor("Panel.background"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        Dimension fieldDimension = new Dimension(0, 35);

        gbc.gridy = 0; gbc.gridx = 0;
        JLabel oldPassLabel = new JLabel("Password Lama:");
        oldPassLabel.setFont(labelFont);
        panel.add(oldPassLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        oldPasswordField = new JPasswordField();
        oldPasswordField.setFont(fieldFont);
        oldPasswordField.setPreferredSize(fieldDimension);
        panel.add(oldPasswordField, gbc);
        gbc.weightx = 0;

        gbc.gridy++; gbc.gridx = 0;
        JLabel newPassLabel = new JLabel("Password Baru:");
        newPassLabel.setFont(labelFont);
        panel.add(newPassLabel, gbc);
        gbc.gridx = 1;
        newPasswordField = new JPasswordField();
        newPasswordField.setFont(fieldFont);
        newPasswordField.setPreferredSize(fieldDimension);
        panel.add(newPasswordField, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel confirmPassLabel = new JLabel("Konfirmasi Password Baru:");
        confirmPassLabel.setFont(labelFont);
        panel.add(confirmPassLabel, gbc);
        gbc.gridx = 1;
        confirmNewPasswordField = new JPasswordField();
        confirmNewPasswordField.setFont(fieldFont);
        confirmNewPasswordField.setPreferredSize(fieldDimension);
        panel.add(confirmNewPasswordField, gbc);
        
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JButton changePasswordButton = new JButton("Ubah Password");
        changePasswordButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        changePasswordButton.setPreferredSize(new Dimension(180, 40));
        changePasswordButton.putClientProperty(FlatClientProperties.STYLE, "arc: 10;");
        changePasswordButton.addActionListener(e -> controller.changePassword(
            new String(oldPasswordField.getPassword()),
            new String(newPasswordField.getPassword()),
            new String(confirmNewPasswordField.getPassword())
        ));
        panel.add(changePasswordButton, gbc);

        return panel;
    }

    private void loadProfileData() {
        User user = controller.loadUserProfile(loggedInUserEmail);
        if (user != null) {
            namaLengkapField.setText(user.getNamaLengkap());
            emailField.setText(user.getEmail());
            noTeleponField.setText(user.getNoTelepon() != null ? user.getNoTelepon() : "");
            alamatArea.setText(user.getAlamat() != null ? user.getAlamat() : "");
            currentImagePath = user.getGambarPath(); 
            newSelectedImagePath = null; 
            displayImage(currentImagePath);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memuat data profil. Anda akan dikembalikan ke Beranda.", "Error", JOptionPane.ERROR_MESSAGE);
            new HomeView(loggedInUserEmail).setVisible(true);
            this.dispose();
        }
    }
}