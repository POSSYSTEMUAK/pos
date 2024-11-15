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

import static javax.swing.GroupLayout.Alignment.CENTER;

public class Splashscreen extends JWindow
{

    public Splashscreen(int duration)
    {
        // Display the splash screen for the specified duration in milliseconds
        showSplash(duration);
    }

    private void showSplash(int duration)
    {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        // Set the splash screen size
        int width = 500;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Customize the splash screen content
        JLabel label = new JLabel(new ImageIcon("metro icon.png")); // Replace with your image path
        JLabel text = new JLabel("WELCOME TO METRO CASH AND CARRY", JLabel.CENTER);
        text.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        text.setForeground(Color.gray);
        text.setForeground(Color.blue);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.blue));
        progressBar.setForeground(Color.blue);

        //label.setHorizontalAlignment(SwingConstants.CENTER);



        /////////////////////////////////////////////////////////////////////




        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentX(Component.CENTER_ALIGNMENT);




        content.add(Box.createVerticalStrut(10));
        content.add(text);
        content.add(Box.createVerticalStrut(10));
        content.add(label);
        content.add(Box.createVerticalStrut(10));
        content.add(progressBar);



        // Simulate task and update progress bar
        for (int i = 0; i <= 100; i++) {
            progressBar.setValue(i); // Update progress bar value
            try {
                Thread.sleep(duration / 5000); // Wait for a short duration to simulate progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Wait for the specified duration
        try {
            Thread.sleep(1000); // Give some time before closing the splash screen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen
        setVisible(false);
        dispose();



        // Add a border around the splash screen
        content.setBorder(BorderFactory.createLineBorder(Color.blue, 5));

        // Display the splash screen
        setVisible(true);

        // Wait for the specified duration
        try
        {

            Thread.sleep(duration);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Close the splash screen
        setVisible(false);
        dispose();
    }



}
