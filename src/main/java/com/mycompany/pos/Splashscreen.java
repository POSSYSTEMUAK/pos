/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author talat
 */
import javax.swing.*;
import java.awt.*;

public class Splashscreen extends JWindow {

    public Splashscreen(int duration) {
        // Display the splash screen for the specified duration in milliseconds
        showSplash(duration);
    }

    private void showSplash(int duration) {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        // Set the splash screen size
        int width = 400;
        int height = 250;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Customize the splash screen content
        JLabel label = new JLabel(new ImageIcon("path/to/your/image.png")); // Replace with your image path
        JLabel text = new JLabel("Welcome to My Application", JLabel.CENTER);
        text.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        text.setForeground(Color.blue);

        content.add(label, BorderLayout.CENTER);
        content.add(text, BorderLayout.SOUTH);

        // Add a border around the splash screen
        content.setBorder(BorderFactory.createLineBorder(Color.blue, 5));

        // Display the splash screen
        setVisible(true);

        // Wait for the specified duration
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen
        setVisible(false);
        dispose();
    }

    public static void main(String[] args) {
        // Display splash screen for 3 seconds
        new Splashscreen(3000);

        // Launch the main application
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
