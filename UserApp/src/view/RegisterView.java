/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatClientProperties;
import controller.AuthController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.User;

public class RegisterView extends JFrame {

    private JTextField namaField, emailField, teleponField;
    private JPasswordField passwordField, confirmPasswordField;
    private JTextArea alamatArea;
    private JButton registerButton;
    private AuthController authController;

    public RegisterView() {
        authController = new AuthController(this);
        
        setTitle("Sinar Jaya Travel - Registrasi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(createFormPanel());
        mainPanel.add(createImagePanel("assets/login_image.png"));

        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 20, 5);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridy++;
        panel.add(new JLabel("Nama Lengkap"), gbc);
        gbc.gridy++;
        namaField = new JTextField(20);
        namaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan nama lengkap Anda");
        namaField.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.add(namaField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Email"), gbc);
        gbc.gridy++;
        emailField = new JTextField(20);
        emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "contoh@email.com");
        emailField.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.add(emailField, gbc);
        
        gbc.gridy++;
        panel.add(new JLabel("Password"), gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true; arc: 15");
        panel.add(passwordField, gbc);
        
        gbc.gridy++;
        panel.add(new JLabel("Konfirmasi Password"), gbc);
        gbc.gridy++;
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true; arc: 15");
        panel.add(confirmPasswordField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Nomor Telepon"), gbc);
        gbc.gridy++;
        teleponField = new JTextField(20);
        teleponField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "08xxxxxxxxxx");
        teleponField.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.add(teleponField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Alamat"), gbc);
        gbc.gridy++;
        alamatArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(alamatArea);
        scrollPane.setBorder(null);
        panel.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 5, 5, 5);
        registerButton = new JButton("REGISTER");
        registerButton.setBackground(new Color(0, 102, 102));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.add(registerButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel backToLogin = new JLabel("<html>Already have an account? <font color='#006666'>Login</font></html>");
        backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(backToLogin, gbc);
        
        registerButton.addActionListener(e -> {
            User user = new User();
            user.setNamaLengkap(namaField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setPassword(new String(passwordField.getPassword()));
            user.setNoTelepon(teleponField.getText().trim());
            user.setAlamat(alamatArea.getText().trim());
            
            String confirmPassword = new String(confirmPasswordField.getPassword());
            authController.register(user, confirmPassword);
        });
        
        backToLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                authController.showLoginViewFromRegister();
            }
        });
        
        return panel;
    }

    private JPanel createImagePanel(String imagePath) {
        return new JPanel() {
            private final Image image = new ImageIcon(imagePath).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
}