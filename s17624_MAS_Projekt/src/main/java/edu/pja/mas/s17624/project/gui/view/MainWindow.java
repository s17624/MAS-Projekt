package edu.pja.mas.s17624.project.gui.view;

import edu.pja.mas.s17624.project.model.Person;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame
{

    public MainWindow()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        Dimension defDim = new Dimension(screenWidth / 2, (int) (screenHeight / 1.5));

        setPreferredSize(defDim);
        setLocation(screenWidth / 4, screenHeight / 4);
        pack();
    }
}
