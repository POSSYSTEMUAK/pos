package com.mycompany.pos;

import javax.swing.*;
import java.awt.*;

public class dataEntryOperatorLoginFrame extends JFrame {
    public dataEntryOperatorLoginFrame() {
        // Set up the frame
        setTitle("Data Entry Operator Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); // Adjust size to accommodate all elements
        setLayout(new BorderLayout()); // Main layout for the frame

        // Create a main container panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // Horizontal layout

        // Create the left panel for the login form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout()); // Center elements within
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        // Create title and subtitle
        JLabel titleLabel = new JLabel("Data Entry Operator");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Login");
        subtitleLabel.setFont(new Font("Poppins", Font.PLAIN, 26));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create input fields with rounded borders
        JTextField userField = new JTextField(15);
        userField.setPreferredSize(new Dimension(200, 40));
        userField.setText("username");
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JPasswordField passField = new JPasswordField(15);
        passField.setPreferredSize(new Dimension(200, 40));
        passField.setText("password");
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Create login button with styling
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(190, 40));
        loginButton.setBackground(new Color(100, 149, 237)); // Cornflower blue background
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.getCursor();

        // "Back to home screen" label
        JLabel backLabel = new JLabel("Back to home screen");
        backLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        backLabel.setForeground(Color.BLUE);
        backLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the left panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        leftPanel.add(titleLabel, gbc);
        gbc.gridy++;
        leftPanel.add(subtitleLabel, gbc);
        gbc.gridy++;
        leftPanel.add(userField, gbc);
        gbc.gridy++;
        leftPanel.add(passField, gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        leftPanel.add(loginButton, gbc);
        gbc.gridy++;
        leftPanel.add(backLabel, gbc);

        // Create the right panel to display an image
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // Load and add the image from the specified path
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("C://Users//Muhammad Khizer//IdeaProjects//SCD_TERM_PROJECT//images//pic2.png");

        // Resize the image to fit the right panel's size
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        rightPanel.add(imageLabel, BorderLayout.CENTER);

        // Create a thin black separator
        JPanel separator = new JPanel();
        separator.setBackground(Color.BLACK);
        separator.setPreferredSize(new Dimension(1, 5)); // 1 pixel wide, full height of the frame

        // Add left panel, separator, and right panel to the main panel
        mainPanel.add(leftPanel);
        mainPanel.add(separator);
        mainPanel.add(rightPanel);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Set frame to be visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the frame creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new dataEntryOperatorLoginFrame());
    }
}
