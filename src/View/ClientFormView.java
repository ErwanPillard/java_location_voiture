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
    private JTextField ageField;
    private JTextField telephoneField;
    private JTextField numeroPermisField;
    private JTextField birthDateField;

    private JButton jbSave;
    private JButton jbCancel;

    public ClientFormView(ClientController clientController){
        createForms();
        createButtons();
        registerListeners(clientController);
        configure();
    }

    private void configure(){
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(this.getRootPane());
    }
    private void createForms(){
        JPanel jpForm = new JPanel(new GridLayout(2, 1, 0, 5));

        jpForm.setBorder(BorderFactory.createTitledBorder("Données personnelles"));

        jpForm.add(fieldset(new JLabel("Prénom :"),
                prenomField = new JTextField(30)));

        jpForm.add(fieldset(new JLabel("Nom :"),
                nomField = new JTextField(30)));

        this.add(jpForm, BorderLayout.CENTER);
    }


    private JPanel fieldset(JComponent...components){
        JPanel fieldset = new JPanel();
        for (JComponent component : components) {
            fieldset.add(component);
        }
        return fieldset;
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

        clientController.addClient(nom, prenom, email, mdp, age, telephone, birthDate, numeroPermis, "Particulier");

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
