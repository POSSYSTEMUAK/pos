package com.mycompany.pos;

import javax.swing.*;
import java.awt.*;

public class ResizableWindow extends JFrame
{
    public ResizableWindow()
    {

        setTitle("Resizable Window Example");

        // GETTING SCREEN RESOLUTION:
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // SETTING THE WINDOW SIZE window TO % OF SCREEN RESOLUTION:
        int width = (int) (screenSize.width * 0.7);  // 70% of screen width
        int height = (int) (screenSize.height * 0.7);  // 70% of screen height
        setSize(width, height);

        // SET WINDOW LOCATION AT SCREEN CENTER:
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
