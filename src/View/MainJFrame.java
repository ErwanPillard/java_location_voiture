package View;

import javax.swing.*;
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
        setTitle("Création de compte client");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        add(panel);
        placeComponents();

        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(particulierRadioButton);
        radioButtonGroup.add(entrepriseRadioButton);

        particulierRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label1.setText("Nom :");
                label2.setText("Prénom :");
                nomField.setVisible(true);
                prenomField.setVisible(true);
                siretField.setVisible(false);
                nomEntrepriseField.setVisible(false);
            }
        });

        entrepriseRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label1.setText("Siret :");
                label2.setText("Nom de l'entreprise :");
                nomField.setVisible(false);
                prenomField.setVisible(false);
                siretField.setVisible(true);
                nomEntrepriseField.setVisible(true);
            }
        });

        suivantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (particulierRadioButton.isSelected()) {
                    // Traitement pour un particulier
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String email = emailField.getText();
                    // Créer le compte pour un particulier
                } else if (entrepriseRadioButton.isSelected()) {
                    // Traitement pour une entreprise
                    String siret = siretField.getText();
                    String nomEntreprise = nomEntrepriseField.getText();
                    String email = emailField.getText();
                    // Créer le compte pour une entreprise
                }
                // Suite du traitement ou redirection vers le prochain formulaire, etc.
            }
        });
    }

    private void placeComponents() {
        panel.setLayout(null);

        particulierRadioButton = new JRadioButton("Particulier");
        particulierRadioButton.setBounds(20, 20, 100, 30);
        panel.add(particulierRadioButton);

        entrepriseRadioButton = new JRadioButton("Entreprise");
        entrepriseRadioButton.setBounds(130, 20, 100, 30);
        panel.add(entrepriseRadioButton);

        label1 = new JLabel("Nom :");
        label1.setBounds(20, 70, 80, 25);
        panel.add(label1);

        nomField = new JTextField(20);
        nomField.setBounds(100, 70, 200, 25);
        panel.add(nomField);

        label2 = new JLabel("Prénom :");
        label2.setBounds(20, 100, 80, 25);
        panel.add(label2);

        prenomField = new JTextField(20);
        prenomField.setBounds(100, 100, 200, 25);
        panel.add(prenomField);

        emailField = new JTextField(20);
        emailField.setBounds(100, 130, 200, 25);
        panel.add(emailField);

        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setBounds(20, 130, 80, 25);
        panel.add(emailLabel);

        siretField = new JTextField(20);
        siretField.setBounds(100, 70, 200, 25);
        siretField.setVisible(false);
        panel.add(siretField);

        nomEntrepriseField = new JTextField(20);
        nomEntrepriseField.setBounds(100, 100, 200, 25);
        nomEntrepriseField.setVisible(false);
        panel.add(nomEntrepriseField);

        suivantButton = new JButton("Suivant");
        suivantButton.setBounds(150, 200, 100, 25);
        panel.add(suivantButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainJFrame form = new MainJFrame();
                form.setVisible(true);
            }
        });
    }
}
