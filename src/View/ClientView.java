package View;

import Controller.ClientController;
import Model.Particulier;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientView {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JTextField ageField;
    private JTextField telephoneField;
    private JTextField numeroPermisField;
    private JFormattedTextField birthDateField;


    public ClientView(ClientController clientController) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);

        // Center JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1)); // Une seule colonne, lignes dynamiques
        jFrame.add(mainPanel);

        JPanel nomPanel = new JPanel(new BorderLayout());
        nomPanel.add(new JLabel("Nom: "), BorderLayout.WEST);
        nomField = new JTextField(20);
        nomPanel.add(nomField, BorderLayout.CENTER);
        mainPanel.add(nomPanel);

        JPanel prenomPanel = new JPanel(new BorderLayout());
        prenomPanel.add(new JLabel("Prenom: "), BorderLayout.WEST);
        prenomField = new JTextField(20);
        prenomPanel.add(prenomField, BorderLayout.CENTER);
        mainPanel.add(prenomPanel);

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.add(new JLabel("Email: "), BorderLayout.WEST);
        emailField = new JTextField(20);
        emailPanel.add(emailField, BorderLayout.CENTER);
        mainPanel.add(emailPanel);

        JPanel mdpPanel = new JPanel(new BorderLayout());
        mdpPanel.add(new JLabel("Mot de passe: "), BorderLayout.WEST);
        mdpField = new JPasswordField(20);
        mdpPanel.add(mdpField, BorderLayout.CENTER);
        mainPanel.add(mdpPanel);

        JPanel agePanel = new JPanel(new BorderLayout());
        agePanel.add(new JLabel("Age: "), BorderLayout.WEST);
        ageField = new JTextField(20);
        agePanel.add(ageField, BorderLayout.CENTER);
        mainPanel.add(agePanel);

        JPanel telephonePanel = new JPanel(new BorderLayout());
        telephonePanel.add(new JLabel("Telephone: "), BorderLayout.WEST);
        telephoneField = new JTextField(20);
        telephonePanel.add(telephoneField, BorderLayout.CENTER);
        mainPanel.add(telephonePanel);

        JPanel birthDatePanel = new JPanel(new BorderLayout());
        birthDatePanel.add(new JLabel("Date de naissance (dd/mm/YYYY): "), BorderLayout.WEST);

        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####"); // Définir le format de la saisie
            birthDateField = new JFormattedTextField(formatter);
            birthDateField.setColumns(10); // Définir la largeur du champ
            birthDatePanel.add(birthDateField, BorderLayout.CENTER);
            mainPanel.add(birthDatePanel);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JPanel nuermoPermisPanel = new JPanel(new BorderLayout());
        nuermoPermisPanel.add(new JLabel("Numéro De Permis: "), BorderLayout.WEST);
        numeroPermisField = new JTextField(20);
        nuermoPermisPanel.add(numeroPermisField, BorderLayout.CENTER);
        mainPanel.add(telephonePanel);


        // Bien faire le setSize à la fin
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton userSetButton = new JButton("Valider");
        jFrame.add(userSetButton, BorderLayout.SOUTH);

        userSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String mdp = String.valueOf(mdpField.getPassword());
                int age = Integer.parseInt(ageField.getText());
                String telephone = telephoneField.getText();
                String numeroPermis = numeroPermisField.getText();

                String dateString = birthDateField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime birthDate = LocalDateTime.parse(dateString, formatter);

                // Vérifier que les champs requis ne sont pas vides
                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()|| telephone.isEmpty() || String.valueOf(age).isEmpty() || dateString.isEmpty() || numeroPermis.isEmpty()) {
                    JOptionPane.showMessageDialog(jFrame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidEmail(email)) { // Vérification de l'email
                    JOptionPane.showMessageDialog(jFrame, "Veuillez entrer une adresse email valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    clientController.addClient(nom, prenom, email, mdp, age, telephone, birthDate, numeroPermis, "Particulier");
                    // Effacer les champs après soumission réussie (si nécessaire)
                    /*nomField.setText("");
                    prenomField.setText("");
                    emailField.setText("");
                    mdpField.setText("");
                    ageField.setText("");
                    telephoneField.setText("");*/

                }
            }
        });
    }

    // Méthode pour vérifier le format de l'email
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
