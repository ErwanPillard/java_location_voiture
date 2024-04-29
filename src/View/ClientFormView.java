package View;

import Controller.ClientController;
import Model.Entreprise;
import Model.Particulier;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Util.addFormField;

public class ClientFormView extends JDialog {

    static ClientFormView clientFormView = new ClientFormView();

    private JTextField nomEntrepriseField;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JPasswordField confirmeMdpField;
    private JTextField telephoneField;
    private JTextField numeroPermisField;
    private JTextField birthDateField;
    private JTextField numSiret;
    private JComboBox<String> typeField;

    private JButton jbSave;
    private JButton jbCancel;

    // Déclarer une variable pour stocker la référence du dernier panneau ajouté
    private JPanel lastAddedPanel;

    public ClientFormView() {
        createForms(String.valueOf(typeField));
        createButtons();
        registerListeners();
        configure();
    }

    public static void toggle() {
        clientFormView.setVisible(!clientFormView.isVisible());
    }

    private void configure() {
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
                    clearForm(nomField, prenomField, telephoneField, numeroPermisField, birthDateField, numSiret, nomEntrepriseField);
                } else if (selectedType.equals("Entreprise")) {
                    jpParticulierForms.setVisible(false);
                    jpEntrepriseForms.setVisible(true);
                    clearForm(nomField, prenomField, telephoneField, numeroPermisField, birthDateField, numSiret, nomEntrepriseField);
                }

                // Actualiser l'affichage pour refléter les modifications
                jpForm.revalidate();
                jpForm.repaint();
            }
        });

        this.add(jpForm, BorderLayout.CENTER);
    }

    private JPanel createConnexionForm() {
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

        addFormField(jpPersonalInfo, gbcPersonal, "Nom :", nomEntrepriseField = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Siret :", numSiret = new JTextField(20));
        addFormField(jpPersonalInfo, gbcPersonal, "Numero de téléphone :", telephoneField = new JTextField(20));

        return jpPersonalInfo;
    }

    private void createButtons() {
        JPanel jpButtons = new JPanel();

        jpButtons.add(jbSave = new JButton("Sauvegarder"));
        jpButtons.add(jbCancel = new JButton("Annuler"));

        this.add(jpButtons, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        jbSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdSave();
            }
        });
        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdCancel();
            }
        });
    }

    private void cmdSave() {

        String selectedItem = (String) typeField.getSelectedItem();

        if (selectedItem.equals("Particulier")) {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();

            // Partie de récupération et vérification des données du formulaire
            try {
                if (ClientController.getInstance().emailExists(email)) {
                    JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Annule l'opération
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur de base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return; // Annule l'opération
            }

            String mdp = String.valueOf(mdpField.getPassword());
            String hashed = BCrypt.hashpw(mdp, BCrypt.gensalt(12)); // Hashage du mot de passe
            String telephone = telephoneField.getText();
            String numeroPermis = numeroPermisField.getText();
            String dateString = birthDateField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(dateString, formatter);
                if (birthDate.isAfter(LocalDate.now()) || birthDate.isEqual(LocalDate.now()) || birthDate.getMonthValue() < 1 || birthDate.getMonthValue() > 12) {
                    JOptionPane.showMessageDialog(this, "La date de naissance est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Annule l'opération
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Format de la date de naissance invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Annule l'opération
            }

            try {
                Particulier particulier = new Particulier(nom, prenom, email, hashed, telephone, numeroPermis, birthDate);
                ClientController.getInstance().addParticulier(particulier);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du particulier: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } else if (selectedItem.equals("Entreprise")) {
            String nomE = nomEntrepriseField.getText();

            String email = emailField.getText();
            try {
                if (ClientController.getInstance().emailExists(email)) {
                    JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Annule l'opération
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            String mdp = String.valueOf(mdpField.getPassword());
            String hashed = BCrypt.hashpw(mdp, BCrypt.gensalt(12)); // Hashage du mot de passe
            String telephone = telephoneField.getText();
            String siret = numSiret.getText();

            try {
                Entreprise entreprise = new Entreprise(nomE, email, hashed, telephone, siret);
                ClientController.getInstance().addEntreprise(entreprise);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Problème ajout client", "", JOptionPane.INFORMATION_MESSAGE);
        }
        JOptionPane.showMessageDialog(this, "Utilisateur enregistré avec succès", "", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void cmdCancel() {
        dispose();
    }

    private void clearForm(JTextComponent... jtcomponets) {
        for (JTextComponent component : jtcomponets) {
            if (component != null) {
                component.setText("");
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        clearForm(nomField, prenomField, telephoneField, numeroPermisField, birthDateField, numSiret, nomEntrepriseField);
    }

    // Méthode pour vérifier le format de l'email
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}