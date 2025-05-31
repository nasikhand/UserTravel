/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ForgotPasswordView extends JFrame {

    public ForgotPasswordView() {
        setTitle("Lupa Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Reset Password Anda", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        
        JLabel subtitleLabel = new JLabel("Masukkan email Anda untuk menerima instruksi.");
        JTextField emailField = new JTextField(25);
        emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email terdaftar");
        
        JButton sendButton = new JButton("Kirim Instruksi");
        sendButton.setBackground(new Color(0, 102, 102));
        sendButton.setForeground(Color.WHITE);
        
        contentPanel.add(subtitleLabel);
        contentPanel.add(new JLabel(" "));
        contentPanel.add(emailField);
        contentPanel.add(new JLabel(" "));
        contentPanel.add(sendButton);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel backToLoginLink = new JLabel("<html><font color='#006666'>Kembali ke Halaman Login</font></html>");
        backToLoginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomPanel.add(backToLoginLink);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        sendButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                "Fitur ini belum diimplementasikan.\nInstruksi akan dikirim ke email jika terdaftar.", 
                "Informasi", 
                JOptionPane.INFORMATION_MESSAGE));

        backToLoginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
    }
}