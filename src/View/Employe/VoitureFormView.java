package View.Employe;

import Controller.ClientController;
import Controller.ModeleController;
import Controller.VoitureController;
import Model.Voiture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static utils.Util.addFormField;
import static utils.Util.clearForm;

public class VoitureFormView extends JDialog {

    private static final VoitureFormView instance = new VoitureFormView();
    public static VoitureFormView getInstance() {
        return instance;
    }

    private boolean editMode = false;

    private JTextField immatriculationField;
    private JTextField dateMiseEnCirculationField;
    private JTextField nbKilometreField;
    private JTextField couleurField;
    private JComboBox<String> modeleField;

    private JButton jbSave;
    private JButton jbCancel;

    public VoitureFormView() {
        createForm();
        createButtons();
        registerListeners();
        configure();
    }

    public void createForm() {
        JPanel jpForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(5, 5, 5, 5);

        addFormField(jpForm, gbcForm, "Immatriculation",immatriculationField = new JTextField(20));
        addFormField(jpForm, gbcForm, "Date de mise en circulation (yyyy-MM-dd)",dateMiseEnCirculationField = new JTextField(20));
        addFormField(jpForm, gbcForm, "Kilométrage",nbKilometreField = new JTextField(20));
        addFormField(jpForm, gbcForm, "Couleur",couleurField = new JTextField(20));

        String[] modeleDispo = null;

        try {
            modeleDispo = ModeleController.getInstance().allModele();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Aucun modèle n'existe.", "Erreur", JOptionPane.ERROR_MESSAGE); // Afficher une alerte
        }
        if (modeleDispo != null && modeleDispo.length > 0) {
            JComboBox<String> comboBox = new JComboBox<>(modeleDispo);
            addFormField(jpForm, gbcForm, "Modèle :", modeleField = comboBox);
        } else {
            // Aucun modèle n'existe, afficher un message
            JLabel label = new JLabel("Aucun modèle n'existe.");
            addFormField(jpForm, gbcForm, "", label);
        }

        this.add(jpForm, BorderLayout.CENTER);
    }

    public void createButtons(){
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

    private void configure() {
        setTitle("Ajout de voiture");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(this.getRootPane());
    }

    private void cmdSave() {
        String immatriculation = immatriculationField.getText();

        try {
            if (VoitureController.getInstance().immatExists(immatriculation)) {
                JOptionPane.showMessageDialog(null, "L'immatriculation du véhicule existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String dateString = dateMiseEnCirculationField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateMiseEnCirculation = LocalDate.parse(dateString, formatter);

        Double nbKilometre = Double.valueOf(nbKilometreField.getText());
        String couleur = couleurField.getText();
        String modele = (String) modeleField.getSelectedItem();
        int modele_id = 0;

        try {
            modele_id = ModeleController.getInstance().getIdByName(modele);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        try {
            Voiture voiture = new Voiture(dateMiseEnCirculation, immatriculation, couleur, nbKilometre, modele_id);
            VoitureController.getInstance().addVoiture(voiture);

            clearForm(immatriculationField, dateMiseEnCirculationField, nbKilometreField, couleurField);

            JOptionPane.showMessageDialog(this, "Voiture ajouté avec succès");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void cmdCancel() {
        dispose();
    }


    @Override
    public void dispose() {
        super.dispose();
    }
    public static void toggle() {
        instance.setVisible(!instance.isVisible());
    }

}
