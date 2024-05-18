package View.Employe.BaseClient;

import Controller.ClientController;
import Controller.listeners.ClientListener;
import Controller.listeners.MailEvent;
import Model.Particulier;
import Model.Voiture;
import View.listeners.EventListener;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticulierJTable extends JTable {
    private TableModel model = new TableModel();
    private static TableRowSorter<TableModel> sorter;

    private Object originalValue;

    public ParticulierJTable() {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        updateTable(loadAllParticulier());// verifi si les celluels sont editable

        sorter = new TableRowSorter<>(model);
        setRowSorter(sorter); // Assignez le TableRowSorter à la JTable
    }

    static void search(String searchText) {
        RowFilter<ParticulierJTable.TableModel, Object> rf = null;
        try {
            // Créez un filtre en fonction de la chaîne de recherche
            rf = RowFilter.regexFilter("(?i)" + searchText); // (?i) pour ignorer la casse
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf); // Appliquez le filtre au TableRowSorter
    }

    void updateTable(ArrayList<Particulier> particuliers){
        // Effacez le contenu actuel de votre JTable
        clearTable();
        // Ajoutez les nouvelles voitures à votre JTable

        for (Particulier particulier : particuliers) {
            model.insertRow(0, particulier.toArray());
        }
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.fireTableDataChanged();
    }
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }


    public ArrayList<Particulier> loadAllParticulier(){
        ArrayList<Particulier> particuliers = new ArrayList<>();
        try {
            particuliers = (ArrayList<Particulier>) ClientController.getInstance().allParticuliers();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return particuliers;
    }


    public class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[]{"Email", "Nom","Prénom", "N°Téléphone", "Date de Naissance", "Numero Permis"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {// permet de modif les cellules
            return false;
        }

    }
}