package View;

import Controller.ConnexionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConnexionView {
    private JTextField loginField;
    private JTextField passwordField;

    public ConnexionView(ConnexionController connexionController){
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);

        // Center JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1)); // Une seule colonne, lignes dynamiques
        jFrame.add(mainPanel);

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.add(new JLabel("Login: "), BorderLayout.WEST);
        loginField = new JTextField(20);
        loginPanel.add(loginField, BorderLayout.CENTER);
        mainPanel.add(loginPanel);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(new JLabel("Mot de Passe: "), BorderLayout.WEST);
        passwordField = new JTextField(20);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);

        // Bien faire le setSize à la fin
        jFrame.setSize(300, 300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton loginButton = new JButton("Se Connecter");
        jFrame.add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = passwordField.getText();

            }
        });

    }

}
