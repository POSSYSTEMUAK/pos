/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 * @author talat
 */
public class Pos {

    public static void main(String[] args)  throws IOException
    {
       new LoginForm();
       //new BranchManager();
         new Splashscreen(5000);

    }
}
