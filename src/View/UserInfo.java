package View;

import Model.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInfo extends JFrame {
    private final JButton btnLogout;
    private final JButton btnRetour;

    public UserInfo() {
        setTitle("Informations de l'utilisateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Centre la fenêtre
        setLayout(new BorderLayout());

        SessionManager.getInstance();
        JLabel labelUserType = new JLabel("Type : " + SessionManager.userType()); // Affiche le type d'utilisateur
        JLabel labelEmail = new JLabel("Email : " + SessionManager.getCurrentUser().getEmail());

        btnLogout = new JButton("Déconnexion");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.getInstance().logOut();
                dispose(); // Ferme la fenêtre UserInfo
                new HomePage().setVisible(true); // Rouvre HomePage
            }
        });
        btnRetour = new JButton("Retour à la page principale");
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre UserInfo
                new HomePage().setVisible(true); // Rouvre HomePage
            }
        });

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(5, 1)); // Organise les labels verticalement
        panelInfo.add(labelUserType);
        panelInfo.add(labelEmail);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        btnRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(btnRetour);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Ajoute un espace vertical de 5 pixels entre les boutons
        southPanel.add(btnLogout);

        add(panelInfo, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ceci est appelé lorsque l'utilisateur clique sur la croix pour fermer la fenêtre
                ouvrirHomePage();
            }
        });
    }

    private void ouvrirHomePage() {
        // Cette méthode rend la HomePage visible à nouveau
        EventQueue.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}