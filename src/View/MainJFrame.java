package View;

import View.Employe.VoitureJTableView;
import View.layouts.MenuOver;
import View.layouts.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainJFrame extends JFrame {
    private JPanel panel;
    private JRadioButton particulierRadioButton;
    private JRadioButton entrepriseRadioButton;
    private ButtonGroup radioButtonGroup;
    private JLabel label1;
    private JLabel label2;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField siretField;
    private JTextField nomEntrepriseField;
    private JButton suivantButton;

    public MainJFrame() {
        JPanel jpBody = new JPanel();
        jpBody.setLayout(new BorderLayout());
        JScrollPane jspList = new JScrollPane();
        VoitureJTableView jTableList = new VoitureJTableView();
        jspList.setViewportView(jTableList);

        jpBody.add(new Options(jTableList), BorderLayout.SOUTH);
        jpBody.add(jspList, BorderLayout.CENTER);
        this.setJMenuBar(new MenuOver(jTableList));
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
