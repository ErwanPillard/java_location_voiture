package View;

import java.awt.*;
import Controller.*;
import Model.Client;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFormView extends JDialog{

    static ClientController clientController = new ClientController();
    static ClientFormView clientFormView = new ClientFormView(clientController);

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JPasswordField confirmeMdpField;
    private JTextField ageField;
    private JTextField telephoneField;
    private JTextField numeroPermisField;
    private JTextField birthDateField;
    private JComboBox<String> typeField;

    private JButton jbSave;
    private JButton jbCancel;

    // Déclarer une variable pour stocker la référence du dernier panneau ajouté
    private JPanel lastAddedPanel;

    public ClientFormView(ClientController clientController){
        createForms(String.valueOf(typeField));
        createButtons();
        registerListeners(clientController);
        configure();
    }

    private void configure(){
        setTitle("Création compte Particulier");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(this.getRootPane());
    }

    private void createForms(String type) {
        JPanel jpForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(5, 5, 5, 5);

        // Créer les formulaires pour Particulier et Entreprise une seule fois
        JPanel jpParticulierForms = createParticulierForms();
        JPanel jpEntrepriseForms = createEntrepriseForms();

        // Ajouter les panneaux des catégories à jpForm
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        jpForm.add(createConnexionForm(), gbcForm);

        // Ajouter les panneaux des formulaires Particulier et Entreprise à jpForm
        gbcForm.gridy++;
        jpForm.add(jpParticulierForms, gbcForm);
        jpForm.add(jpEntrepriseForms, gbcForm);

        // Masquer initialement le formulaire Entreprise
        jpEntrepriseForms.setVisible(false);

        // Ajouter un écouteur d'événement pour détecter les changements de sélection
        typeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeField.getSelectedItem();

                // Afficher ou cacher les formulaires en fonction du type sélectionné
                if (selectedType.equals("Particulier")) {
                    jpParticulierForms.setVisible(true);
                    jpEntrepriseForms.setVisible(false);
                } else if (selectedType.equals("Entreprise")) {
                    jpParticulierForms.setVisible(false);
                    jpEntrepriseForms.setVisible(true);
                }

                // Actualiser l'affichage pour refléter les modifications
                jpForm.revalidate();
                jpForm.repaint();
            }
        });

        this.add(jpForm, BorderLayout.CENTER);
    }


    private JPanel createConnexionForm(){
        // Catégorie 1 : Informations de connexion
        JPanel jpLoginInfo = new JPanel(new GridBagLayout());
        jpLoginInfo.setBorder(BorderFactory.createTitledBorder("Information de connexion"));
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.anchor = GridBagConstraints.WEST;
        gbcLogin.insets = new Insets(5, 5, 5, 5);
        addFormField(jpLoginInfo, gbcLogin, "Email :", emailField = new JTextField(20));
        addFormField(jpLoginInfo, gbcLogin, "Mot de Passe :", mdpField = new JPasswordField(20));
        addFormField(jpLoginInfo, gbcLogin, "Confirmez mot de passe :", confirmeMdpField = new JPasswordField(20));
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Particulier", "Entreprise"});
        addFormField(jpLoginInfo, gbcLogin, "Type :", typeField = comboBox);

        return jpLoginInfo;
    }
    private JPanel createParticulierForms() {
        JPanel jpPersonalInfo = new JPanel(new GridBagLayout());
        jpPersonalInfo.setBorder(BorderFactory.createTitledBorder("Données personnelles"));
        GridBagConstraints gbcPersonal = new GridBagConstraints();
        gbcPersonal.anchor = GridBagConstraints.WEST;
        gbcPersonal.insets = new Insets(5, 5, 5, 5);

        addFormField(jpPersonalInfo, gbcPersonal, "Prénom :", prenomField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Nom :", nomField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Age :", ageField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Numero de téléphone :", telephoneField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Numéro Permis de Conduire :", numeroPermisField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Date de naissance (dd-MM-yyyy):", birthDateField = new JTextField(20));

        return jpPersonalInfo;
    }
    private JPanel createEntrepriseForms() {
        JPanel jpPersonalInfo = new JPanel(new GridBagLayout());
        jpPersonalInfo.setBorder(BorderFactory.createTitledBorder("Données personnelles"));
        GridBagConstraints gbcPersonal = new GridBagConstraints();
        gbcPersonal.anchor = GridBagConstraints.WEST;
        gbcPersonal.insets = new Insets(5, 5, 5, 5);

        addFormField(jpPersonalInfo, gbcPersonal, "Nom :", prenomField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Siret :", nomField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Numero de téléphone :", telephoneField = new JTextField(20));

        return jpPersonalInfo;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void createButtons(){
        JPanel jpButtons = new JPanel();

        jpButtons.add(jbSave = new JButton("Sauvegarder"));
        jpButtons.add(jbCancel = new JButton("Annuler"));

        this.add(jpButtons, BorderLayout.SOUTH);
    }

    private void registerListeners(ClientController clientController) {
        jbSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                cmdSave(clientController);
            }
        });
        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdCancel();
            }
        });
    }

    private void cmdSave(ClientController clientController){

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String mdp = String.valueOf(mdpField.getPassword());
        int age = Integer.parseInt(ageField.getText());
        String telephone = telephoneField.getText();
        String numeroPermis = numeroPermisField.getText();
        String dateString = birthDateField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(dateString, formatter);

        clientController.addClientPariculier(nom, prenom, email, mdp, age, telephone, birthDate, numeroPermis, "Particulier");
        JOptionPane.showMessageDialog(this, "Utilisateur enregistré avec succès", "", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void cmdCancel(){
        dispose();
    }

    private void clearForm(JTextComponent... jtcomponets){
        for (JTextComponent component : jtcomponets) {
            component.setText("");
        }
    }
    @Override
    public void dispose(){
        super.dispose();
        clearForm(nomField, prenomField);
    }

    // Méthode pour vérifier le format de l'email
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void toggle(){
        clientFormView.setVisible(!clientFormView.isVisible());
    }
}
