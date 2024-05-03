package View;

import View.Employe.ParkAutoView;

import View.layouts.MenuOver;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {
    //TEST pour interface employe
    public static final Dimension PREFERREDSIZE = new Dimension(900, 500);

    public MainJFrame() {
        JPanel jpBody = new JPanel();
        jpBody.setLayout(new BorderLayout());

        new ParkAutoView(jpBody);

        this.setJMenuBar(new MenuOver(jpBody));
        this.setContentPane(jpBody);
    }

    public static void employeInterface() {
        //Create and set up the window.
        JFrame frame = new MainJFrame();
        frame.setMinimumSize(PREFERREDSIZE);
        frame.setPreferredSize(PREFERREDSIZE);

        //Center the frame
        frame.setLocationRelativeTo(null);

        // Maximiser la fenêtre pour la mettre en plein écran
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //Test interface employé
    public static void main(String[] args) {
        MainJFrame.employeInterface();
    }

}
