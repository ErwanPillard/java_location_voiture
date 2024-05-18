package View.Employe.BaseClient;

import Controller.ClientController;
import Controller.listeners.ClientListener;
import Controller.listeners.MailEvent;
import Model.Entreprise;
import Model.Particulier;
import Model.Voiture;
import View.listeners.EventListener;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;

public class EntrepriseJTable extends JTable {
    private TableModel model = new TableModel();
    private static TableRowSorter<TableModel> sorter;

    private Object originalValue;

    public EntrepriseJTable() {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        updateTable(loadAllEntreprises());// verifi si les celluels sont editable

        sorter = new TableRowSorter<>(model);
        setRowSorter(sorter); // Assignez le TableRowSorter à la JTable
    }

    static void search(String searchText) {
        RowFilter<TableModel, Object> rf = null;
        try {
            // Créez un filtre en fonction de la chaîne de recherche
            rf = RowFilter.regexFilter("(?i)" + searchText); // (?i) pour ignorer la casse
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf); // Appliquez le filtre au TableRowSorter
    }


    void updateTable(ArrayList<Entreprise> entreprises){
        // Effacez le contenu actuel de votre JTable
        clearTable();
        // Ajoutez les nouvelles voitures à votre JTable

        for (Entreprise entreprise : entreprises) {
            model.insertRow(0, entreprise.toArray());
        }
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.fireTableDataChanged();
    }
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }


    public ArrayList<Entreprise> loadAllEntreprises(){
        ArrayList<Entreprise> entreprises = new ArrayList<>();
        try {
            entreprises = (ArrayList<Entreprise>) ClientController.getInstance().allEntreprises();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return entreprises;
    }


    public class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[]{"Nom", "Email", "N°Téléphone", "Numero Siret"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {// permet de modif les cellules
            return false;
        }

    }
}