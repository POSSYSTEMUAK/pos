package com.mycompany.pos;


import javax.swing.*;
import java.awt.*;

public class Splashscreen extends JWindow
{

    public Splashscreen(int duration)
    {
        showSplash(duration);
    }

    private void showSplash(int duration)
    {

        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        //SPLASH SCREEN SIZE N POSITION:
        int width = 500;
        int height = 250;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);


        JLabel label = new JLabel(new ImageIcon("metro icon.png")); // Replace with your image path
        JLabel text = new JLabel("WELCOME TO METRO CASH AND CARRY", JLabel.CENTER);
        text.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        text.setForeground(Color.blue);


        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.blue));
        progressBar.setForeground(Color.blue);


        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(10));
        content.add(text);
        content.add(Box.createVerticalStrut(10));
        content.add(label);
        content.add(Box.createVerticalStrut(10));
        content.add(progressBar);

        // SETTING UP THE BORDER AROUND SPLASH SCREEN:
        content.setBorder(BorderFactory.createLineBorder(Color.blue, 5));


        setVisible(true);


        int updateInterval = duration / 100;
        for (int i = 0; i <= 100; i++)
        {
            progressBar.setValue(i);
            try
            {
                Thread.sleep(updateInterval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }


        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        setVisible(false);
        dispose();
    }



}
