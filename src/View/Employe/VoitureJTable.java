package View.Employe;

import Controller.ModeleController;
import Controller.VoitureController;
import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Dao.DatabaseManager;
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
import java.sql.*;
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
        VoitureController.getInstance().addVoitureListener(this);
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

    private class TableModel extends DefaultTableModel {

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
                            ParkAutoView.displayInfoVoiture(voiture.getImmatriculation());
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

    public void LoadResaNonConfirmée() {
        String query = "SELECT * FROM Reservation WHERE etat = 'non-confirmée'";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) this.getModel();
            model.setRowCount(0); // Effacer les données existantes

            while (rs.next()) {
                // Assumer que tu as des colonnes comme id, dateDebutReservation, etc.
                Object[] row = new Object[]{
                        rs.getInt("numReservation"),
                        rs.getDate("dateDebutReservation"),
                        rs.getDate("dateFinReservation"),
                        rs.getFloat("montant"),
                        rs.getString("etat"),
                        // Ajouter d'autres colonnes selon la structure de ta table
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des réservations", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


}
