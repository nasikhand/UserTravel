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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private AuthController authController;

    public LoginView() {
        authController = new AuthController(this);
        
        setTitle("Sinar Jaya Travel - Login");
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
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel logoLabel = new JLabel("SINAR JAYA");
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        logoLabel.setForeground(new Color(0, 102, 102));
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 30, 5);
        panel.add(logoLabel, gbc);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy++;
        
        JLabel titleLabel = new JLabel("Welcome back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        JLabel subtitleLabel = new JLabel("Thank you for getting back, please login to your account.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.insets = new Insets(5, 5, 20, 5);
        panel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy++;
        panel.add(new JLabel("Email Address"), gbc);

        gbc.gridy++;
        emailField = new JTextField(20);
        emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "coolname@example.com");
        emailField.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.add(emailField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Password"), gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "••••••••••••");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true; arc: 15");
        panel.add(passwordField, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setFont(new Font("SansSerif", Font.PLAIN, 12));
        rememberMe.setBackground(Color.WHITE);
        panel.add(rememberMe, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel forgotPassword = new JLabel("Forgot password");
        forgotPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgotPassword.setForeground(new Color(0, 102, 102));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new ForgotPasswordView().setVisible(true);
                dispose(); 
            }
        });
        panel.add(forgotPassword, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(0, 102, 102));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        buttonPanel.add(loginButton);
        
        registerButton = new JButton("SIGN UP");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.putClientProperty(FlatClientProperties.STYLE, "arc: 15; border: 2,2,2,2, #006666, 2");
        buttonPanel.add(registerButton);
        
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            authController.login(emailField.getText(), new String(passwordField.getPassword()));
        });
        
        registerButton.addActionListener(e -> authController.showRegisterViewFromLogin());
        
        return panel;
    }
    
    public JPanel createImagePanel(String imagePath) {
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