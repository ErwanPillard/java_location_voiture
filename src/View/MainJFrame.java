package View;

import View.layouts.MenuOver;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {
    public MainJFrame() {
        JPanel jpBody = new JPanel();
        jpBody.setLayout(new BorderLayout());

        this.setJMenuBar(new MenuOver(jpBody));
        this.setContentPane(jpBody);
    }

    //TEST pour interface employe
    public static final Dimension PREFERREDSIZE = new Dimension(600,500);
    public static void employeInterface() {
        //Create and set up the window.
        JFrame frame = new MainJFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(PREFERREDSIZE);
        frame.setPreferredSize(PREFERREDSIZE);

        //Center the frame
        frame.setLocationRelativeTo(null);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //Test interface employé
    public static void main(String[] args) {
        MainJFrame.employeInterface();
    }

}
