package View.Employe;

import Controller.OffreReductionController;
import Controller.VoitureController;
import Model.OffreReduction;
import Model.TypeAdhesion;
import Model.Voiture;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OffreReductionForm extends JDialog {
    private static final OffreReductionForm instance = new OffreReductionForm();
    public static OffreReductionForm getInstance() {
        return instance;
    }

    private JButton submitButton;

    private JTextField nomField;
    private JTextArea descriptionArea;
    private JFormattedTextField dateDebutField;
    private JFormattedTextField dateFinField;
    private JTextField pourcentageReductionField;
    private JComboBox<String> typeAdhesionComboBox;

    public OffreReductionForm() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        panel.add(nomLabel);
        panel.add(nomField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea();
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        panel.add(descriptionLabel);
        panel.add(descriptionScrollPane);

        JLabel dateDebutLabel = new JLabel("Date de début (DD-MM-YYYY):");
        dateDebutField = new JFormattedTextField(createFormatter("##-##-####"));
        panel.add(dateDebutLabel);
        panel.add(dateDebutField);

        JLabel dateFinLabel = new JLabel("Date de fin (DD-MM-YYYY):");
        dateFinField = new JFormattedTextField(createFormatter("##-##-####"));
        panel.add(dateFinLabel);
        panel.add(dateFinField);

        JLabel pourcentageReductionLabel = new JLabel("Pourcentage de réduction:");
        pourcentageReductionField = new JTextField();
        panel.add(pourcentageReductionLabel);
        panel.add(pourcentageReductionField);

        JLabel typeAdhesionLabel = new JLabel("Type d'adhésion:");
        String[] typesAdhesion = {"NOUVEAUCLIENT", "BRONZE", "ARGENT", "OR"};
        typeAdhesionComboBox = new JComboBox<>(typesAdhesion);
        panel.add(typeAdhesionLabel);
        panel.add(typeAdhesionComboBox);

        submitButton = new JButton("Soumettre");

        panel.add(submitButton);

        getContentPane().add(panel);

        registerListeners();
    }

    public void registerListeners(){
        submitButton.addActionListener(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            // Récupérer les valeurs saisies
            String nom = nomField.getText();
            String description = descriptionArea.getText();
            LocalDate dateDebut = LocalDate.parse(dateDebutField.getText(), formatter);
            LocalDate dateFin = LocalDate.parse(dateFinField.getText(), formatter);
            float pourcentageReduction = Float.parseFloat(pourcentageReductionField.getText());
            String typeAdhesion = (String) typeAdhesionComboBox.getSelectedItem();

            OffreReduction offreReduction = new OffreReduction(nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion);
            try {
                OffreReductionController.getInstance().add(offreReduction);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

    private MaskFormatter createFormatter(String pattern) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(pattern);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    public static void toggle() {
        instance.setVisible(!instance.isVisible());
    }
}

