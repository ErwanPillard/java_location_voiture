package View.Employe;

import Model.TypeAdhesion;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OffreReductionForm extends JFrame {

    private JTextField nomField;
    private JTextArea descriptionArea;
    private JTextField dateDebutField;
    private JTextField dateFinField;
    private JTextField pourcentageReductionField;
    private JComboBox<String> typeAdhesionComboBox;

    public OffreReductionForm() {
        super("Nouvelle Offre de Réduction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        dateDebutField = new JTextField(20);
        panel.add(dateDebutLabel);
        panel.add(dateDebutField);

        JLabel dateFinLabel = new JLabel("Date de fin (DD-MM-YYYY):");
        dateFinField = new JTextField();
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

        JButton submitButton = new JButton("Soumettre");
        submitButton.addActionListener(e -> {
            // Récupérer les valeurs saisies
            String nom = nomField.getText();
            String description = descriptionArea.getText();
            LocalDate dateDebut = LocalDate.parse(dateDebutField.getText());
            LocalDate dateFin = LocalDate.parse(dateFinField.getText());
            float pourcentageReduction = Float.parseFloat(pourcentageReductionField.getText());
            TypeAdhesion typeAdhesion = (TypeAdhesion) typeAdhesionComboBox.getSelectedItem();

        });
        panel.add(submitButton);

        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OffreReductionForm formulaire = new OffreReductionForm();
            formulaire.setVisible(true);
        });
    }
}

