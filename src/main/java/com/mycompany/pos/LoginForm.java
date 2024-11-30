package com.mycompany.pos;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginForm extends JFrame
{

    public LoginForm() throws IOException
    {
        setTitle("METRO POINT OF SALES SYSTEM");
        setIconImage(new ImageIcon("metro icon.png").getImage());  // ICON IMAGE:

        //  GRIDBAG LAYOUT TO CENTER ELEMENTS:
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.black, 1));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());


        BufferedImage posImage = ImageIO.read(new File("metro icon.png"));
        ImageIcon icon = new ImageIcon(posImage);
        JLabel image = new JLabel(icon);


        int imageWidth = posImage.getWidth();
        int imageHeight = posImage.getHeight();


        int buttonWidth = (int)(imageWidth * 0.8);                    // BUTTONS WIDTH RELATIVE TO IMAGE WIDTH:
        int buttonHeight = 40;                                       // SETTING FIXED HEIGHT FOR ALL BUTTONS:


        Color yellowColor = new Color(255, 204, 0);  // Yellow color (RGB: 255, 204, 0)


        JButton superAdminBtn = new JButton("SUPER ADMIN");
        superAdminBtn.setBackground(yellowColor);
        superAdminBtn.setForeground(Color.BLACK);
        superAdminBtn.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        JButton branchManagerBtn = new JButton("BRANCH MANAGER");
        branchManagerBtn.setBackground(yellowColor);
        branchManagerBtn.setForeground(Color.BLACK);
        branchManagerBtn.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        JButton dataEntryOperatorBtn = new JButton("DATA ENTRY OPERATOR");
        dataEntryOperatorBtn.setBackground(yellowColor);
        dataEntryOperatorBtn.setForeground(Color.BLACK);
        dataEntryOperatorBtn.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        JButton cashierBtn = new JButton("CASHIER");
        cashierBtn.setBackground(yellowColor);
        cashierBtn.setForeground(Color.BLACK);
        cashierBtn.setPreferredSize(new Dimension(buttonWidth, buttonHeight));



        // GRIDBAG CONSTRAINTS TO CENTER ALIGNMENT OF ELEMENTS:
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;


        gbc.gridy = 0;
        panel.add(image, gbc);


        gbc.insets = new Insets(20, 0, 20, 0);     // 20 PX EQUAL GAPS UP AND DOWN BETWEEN BUTTONS:

        gbc.gridy++;
        panel.add(superAdminBtn, gbc);

        gbc.gridy++;
        panel.add(branchManagerBtn, gbc);

        gbc.gridy++;
        panel.add(dataEntryOperatorBtn, gbc);

        gbc.gridy++;
        panel.add(cashierBtn, gbc);




        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


}
