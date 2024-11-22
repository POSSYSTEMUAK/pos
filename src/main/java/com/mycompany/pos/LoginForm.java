package com.mycompany.pos;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.border.Border;



public class LoginForm extends JFrame
{
    public LoginForm() throws IOException
    {
        setTitle("METRO POINT OF SALES SYSTEM");
        setLayout(null);
        setIconImage(new ImageIcon("metro icon.png").getImage());    // ICON IMAGE:

        // SETTING INITIAL PANELS PROPERTIES:
        JPanel f1Panel = new JPanel();
        JPanel f2Panel = new JPanel();

        f1Panel.setBorder(new LineBorder(Color.black, 1));
        f1Panel.setBackground(Color.getHSBColor(15, 231, 65));
        f1Panel.setBounds(0, 0, 600, 800);

        f2Panel.setLayout(new BorderLayout());
        f2Panel.setBorder(new LineBorder(Color.black, 0));
        f2Panel.setBackground(Color.WHITE);
        f2Panel.setBounds(600, 0, 950, 800);



        // POS SYSTEM IMAGE AT ROLE-BASED LOGIN INTERFACE:
        BufferedImage posImage = ImageIO.read(new File("pos_Img.png"));
        ImageIcon icon = new ImageIcon(posImage);

        // ADJUST IMAGE TO FIT PANEL:
        Image scaledImage = icon.getImage().getScaledInstance(f2Panel.getWidth(), f2Panel.getHeight(), Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(scaledImage));

        JLabel chooseEntitylabel=new JLabel("WHO ARE YOU:");
        chooseEntitylabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseEntitylabel.setVerticalAlignment(SwingConstants.TOP);
        chooseEntitylabel.setForeground(Color.BLACK);
        chooseEntitylabel.setLayout(null);
        chooseEntitylabel.setBorder(new LineBorder(Color.black,2));

        // PANEL F1PANEL SETTING AND ALLIGNING:
        JButton superAdminBtn = new JButton("SUPER ADMIN");
        superAdminBtn.setBounds(40, 30, 20, 5);
        superAdminBtn.setBorder(new RoundedBorder(10));
        superAdminBtn.setBackground(new Color(153,102,0));


        JButton branchManagerBtn = new JButton("BRANCH MANAGER");
        branchManagerBtn.setBounds(40, 30, 20, 5);
        branchManagerBtn.setBorder(new RoundedBorder(10));
        branchManagerBtn.setBackground(new Color(153,102,0));


        JButton dataEntryOperatorBtn = new JButton("DATA ENTRY OPERATOR");
        dataEntryOperatorBtn.setBounds(40, 30, 20, 5);
        dataEntryOperatorBtn.setBorder(new RoundedBorder(10));
        dataEntryOperatorBtn.setBackground(new Color(153,102,0));


        JButton cashierBtn = new JButton("CASHIER");
        cashierBtn.setBounds(40, 30, 20, 5);
        cashierBtn.setBorder(new RoundedBorder(10));
        cashierBtn.setBackground(new Color(153,102,0));




        GroupLayout groupLayout = new GroupLayout(f1Panel);
        f1Panel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.linkSize(SwingConstants.VERTICAL, superAdminBtn,branchManagerBtn,dataEntryOperatorBtn,cashierBtn);
        groupLayout.linkSize(SwingConstants.HORIZONTAL, superAdminBtn,branchManagerBtn,dataEntryOperatorBtn,cashierBtn);


        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(CENTER)
                                .addComponent(chooseEntitylabel)
                                .addComponent(superAdminBtn,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(branchManagerBtn,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dataEntryOperatorBtn,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cashierBtn,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Set Vertical Group (Arrange components vertically)
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGap(150)
                        .addComponent(chooseEntitylabel)
                        .addGap(70)
                        .addComponent(superAdminBtn)
                        .addGap(70)
                        .addComponent(branchManagerBtn)
                        .addGap(70)
                        .addComponent(dataEntryOperatorBtn)
                        .addGap(70)
                        .addComponent(cashierBtn)
                        .addGap(150)
        );




        ////// ADDING TO FRAME:

        f1Panel.add(chooseEntitylabel);
        f1Panel.add(superAdminBtn);
        f1Panel.add(branchManagerBtn);
        f1Panel.add(dataEntryOperatorBtn);
        f1Panel.add(cashierBtn);

        f2Panel.add(picLabel, BorderLayout.CENTER);  // Center the image in the panel
        add(f1Panel);
        add(f2Panel);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make the window maximized
        setVisible(true);
    }


}
