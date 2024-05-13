package View;

import javax.swing.*;
import java.awt.*;

public class ConnexionUtilisateur extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public ConnexionUtilisateur() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));

        formPanel.add(new JLabel("Email :"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        loginButton = new JButton("Connexion");
        loginButton.setBackground(new Color(0x377e21)); // Couleur Verte
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        buttonPanel.add(loginButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
