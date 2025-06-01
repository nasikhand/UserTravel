/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory; // Tambahkan import ini
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager; // Tambahkan import ini

public class StepIndicatorPanel extends JPanel {
    private final String[] steps;
    private int currentStep = 0;

    public StepIndicatorPanel(String[] steps) {
        this.steps = steps;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false); 

        for (int i = 0; i < steps.length; i++) {
            JLabel stepLabel = new JLabel(steps[i]);
            stepLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            stepLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
            
            if (i == 0) {
                stepLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                stepLabel.setForeground(UIManager.getColor("Label.foreground")); // Warna teks aktif
            } else {
                stepLabel.setForeground(UIManager.getColor("Label.disabledForeground")); // Warna teks non-aktif
            }
            add(stepLabel);
        }
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        for(int i=0; i < getComponentCount(); i++){
            Component comp = getComponent(i);
            if(comp instanceof JLabel){
                if(i == step){
                    comp.setFont(new Font("SansSerif", Font.BOLD, 14));
                    comp.setForeground(UIManager.getColor("Label.foreground"));
                } else {
                    comp.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    comp.setForeground(UIManager.getColor("Label.disabledForeground"));
                }
            }
        }
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int circleDiameter = 16;
        int circleX = 10;
        
        // Warna garis dan lingkaran non-aktif dari UIManager
        Color lineColor = UIManager.getColor("Separator.foreground"); 
        Color inactiveCircleColor = UIManager.getColor("Component.borderColor");
        Color activeCircleColor = UIManager.getColor("Component.focusColor"); // Atau "ProgressBar.foreground"

        if (lineColor == null) lineColor = Color.LIGHT_GRAY;
        if (inactiveCircleColor == null) inactiveCircleColor = Color.LIGHT_GRAY;
        if (activeCircleColor == null) activeCircleColor = new Color(0, 102, 102);

        // Gambar garis vertikal
        if (getComponentCount() > 0) { // Pastikan ada komponen sebelum menggambar
            int firstY = getComponent(0).getY() + getComponent(0).getHeight() / 2;
            int lastY = getComponent(steps.length - 1).getY() + getComponent(steps.length - 1).getHeight() / 2;
            g2d.setColor(lineColor);
            g2d.drawLine(circleX + circleDiameter / 2, firstY, circleX + circleDiameter / 2, lastY);
        }

        for (int i = 0; i < steps.length; i++) {
            if (i < getComponentCount()) { // Pastikan komponen ada
                Component c = getComponent(i);
                int circleY = c.getY() + c.getHeight() / 2 - circleDiameter / 2;
                
                if (i == currentStep) {
                    g2d.setColor(activeCircleColor);
                } else {
                    g2d.setColor(inactiveCircleColor);
                }
                g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);
            }
        }
    }
}