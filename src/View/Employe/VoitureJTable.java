package View.Employe;

import Controller.ModeleController;
import Controller.VoitureController;
import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;
import Model.Voiture;
import View.Employe.Button.CustomButtonEditor;
import View.Employe.Button.CustomButtonRenderer;
import View.listeners.EventListener;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class VoitureJTable extends JTable implements VoitureListener, EventListener {
    private TableModel model = new TableModel();
    private static TableRowSorter<TableModel> sorter;

    private Object originalValue;

    public VoitureJTable() {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        VoitureController.getInstance().addUserListener(this);
        updateTable(loadAll());
        cellEdit();
        imageSelector();

        // Création du TableRowSorter et association avec le modèle de tableau
        sorter = new TableRowSorter<>(model);
        setRowSorter(sorter); // Assignez le TableRowSorter à la JTable
    }

    void updateTable(ArrayList<Voiture> voitures){
        // Effacez le contenu actuel de votre JTable
        clearTable();
        // Ajoutez les nouvelles voitures à votre JTable
        for (Voiture voiture : voitures) {
            model.insertRow(0, voiture.toArray());
        }
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.fireTableDataChanged();
    }

    static void search(String searchText) {
        System.out.println(searchText);
        RowFilter<VoitureJTable.TableModel, Object> rf = null;
        try {
            // Créez un filtre en fonction de la chaîne de recherche
            rf = RowFilter.regexFilter("(?i)" + searchText); // (?i) pour ignorer la casse
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf); // Appliquez le filtre au TableRowSorter
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }


    public ArrayList<Voiture> loadAll(){
        ArrayList<Voiture> voitures = new ArrayList<>();
        try {
            voitures = (ArrayList<Voiture>) VoitureController.getInstance().allVoitures();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return voitures;
    }

    public class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[]{"Immatriculation", "Mise en circulation", "Kilometrage", "Couleur", ""});
            //super(new Object[][]{}, new String[]{"Immatriculation", "Mise en circulation", "Kilometrage", "Couleur", "Marque", "Nom", "Nb Places", "Nb Portes", "Taille Coffre", "Caracteristique", "Prix Journalier", "Note Satisfaction", "Categorie", "Attelage", "Boite Vitesse", ""});
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 0 || column == 1 || column == 5 || column == 6 || column == 7 || column == 12 || column == 14) {
                return false;
            }
            return true;

        }

    }

    @Override
    public void cmdEdit() {
        //generateModels();
        generateCars();
    }

    // Method to generate random data for models
    public Modele generateRandomModele() {
        Random random = new Random();
        String[] marques = {"Toyota", "Honda", "Ford", "Chevrolet", "Volkswagen"};
        String marque = marques[random.nextInt(marques.length)];
        String[] noms = {"Corolla", "Civic", "F-150", "Silverado", "Golf"};
        String nom = noms[random.nextInt(noms.length)];
        int nbPlace = random.nextInt(5) + 1; // Random number of seats between 1 and 5
        int nbPorte = random.nextInt(4) + 2; // Random number of doors between 2 and 5
        float tailleCoffre = random.nextFloat() * 100; // Random trunk size between 0 and 100
        String caracteristique = "Random characteristics"; // You can customize this
        int prixJournalier = random.nextInt(200) + 50; // Random daily price between 50 and 250
        float noteSatisfaction = 0; // Start with 0
        boolean attelage = random.nextBoolean();
        BoiteVitesse boiteVitesse = BoiteVitesse.values()[random.nextInt(BoiteVitesse.values().length)]; // Random gearbox type
        Categorie categorieSelectionnee = Categorie.values()[random.nextInt(Categorie.values().length)]; // Random category
        return new Modele(marque, nom, nbPlace, nbPorte, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorieSelectionnee, attelage, boiteVitesse);
    }

    // Method to generate random data for cars
    public Voiture generateRandomCar() {
        Random random = new Random();
        String immatriculation = "RandomImmat" + random.nextInt(1000); // Generate a random license plate
        LocalDate dateMiseEnCirculation = LocalDate.now().minusDays(random.nextInt(365)); // Random date within the last year
        String[] colors = {"Red", "Blue", "White", "Black", "Silver"};
        String couleur = colors[random.nextInt(colors.length)];
        double nbKilometre = random.nextDouble() * 100000; // Random number of kilometers up to 100,000
        int modele_id = random.nextInt(10) + 1;; // You need to get the model ID from the database, or you can assign it randomly if you want

        return new Voiture(dateMiseEnCirculation, immatriculation, couleur, nbKilometre, modele_id);
    }

    // Method to generate 100 cars
    public void generateCars() {
        for (int i = 0; i < 100; i++) {
            Voiture car = generateRandomCar();
            try {
                VoitureController.getInstance().addVoiture(car);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to generate 10 models
    public void generateModels() {
        for (int i = 0; i < 10; i++) {
            Modele modele = generateRandomModele();
            try {
                ModeleController.getInstance().addModele(modele);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cellEdit() {
        // Ajouter un écouteur pour détecter la sélection de cellule
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = getSelectedRow();
                    int column = getSelectedColumn();
                    if (row != -1 && column != -1) {
                        originalValue = getValueAt(row, column);
                        try {
                            Voiture voiture = VoitureController.getInstance().findByImmat((String) getValueAt(row, 0));
                            Modele modele = ModeleController.getModeleById(voiture.getModele_id());
                            ParkAutoView.displayImageVoiture(voiture.getImmatriculation());
                            ParkAutoView.displayModele(modele);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        // Ajouter un écouteur pour détecter l'arrêt d'édition de cellule
        this.getDefaultEditor(Object.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                int row = getSelectedRow();
                int column = getSelectedColumn();
                Object newValue = getValueAt(row, column);
                String immatriculation = (String) getValueAt(row, 0);
                if (!newValue.equals(originalValue)) {
                    try {
                        update(column, newValue, immatriculation);
                        System.out.println("La cellule à la ligne " + row + ", colonne " + column + " a été modifiée.");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                // Gérer l'annulation de l'édition de cellule si nécessaire
            }
        });
    }

    @Override
    public void cmdRemove() {
        int row = this.getSelectedRow();
        if (row != -1) {
            String immatriculation = (String) this.getValueAt(row, 0);

            // Demander confirmation avant de procéder à la suppression
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer la voiture avec l'immatriculation " + immatriculation + " ?",
                    "Confirmer la suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    VoitureController.getInstance().remove(immatriculation);
                    ((DefaultTableModel) this.getModel()).removeRow(row);
                    JOptionPane.showMessageDialog(this, "Voiture supprimée avec succès.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucune voiture sélectionnée pour la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void cmdAdd() {
        VoitureFormView.toggle();
    }

    @Override
    public void voitureadd(MailEvent<Voiture> event) {model.insertRow(0, event.getSource().toArray());}

    public void imageSelector() {
        TableColumn column = getColumnModel().getColumn(4);
        column.setCellRenderer(new CustomButtonRenderer());
        column.setCellEditor(new CustomButtonEditor(null));
    }

    @Override
    public Component prepareEditor(TableCellEditor editor, int row, int column) {
        Component c = super.prepareEditor(editor, row, column);
        if (editor instanceof CustomButtonEditor) {
            CustomButtonEditor customEditor = new CustomButtonEditor((String) getValueAt(row, 0));
            return customEditor.getTableCellEditorComponent(this, getValueAt(row, column), true, row, column);
        }
        return c;
    }

    public void update(int column, Object value, String immat) throws SQLException {
        Voiture voiture = Voiture.findByImmat(immat);
        switch (column) {
            case 2:
                voiture.setNbKilometre(Double.parseDouble((String) value));
                break;
            case 3:
                voiture.setCouleur((String) value);
                break;
        }
        VoitureController.getInstance().update(voiture);
    }
}
