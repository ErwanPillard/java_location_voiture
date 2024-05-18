package View.Employe;

import Controller.OffreReductionController;
import Controller.listeners.MailEvent;
import Controller.listeners.OffreReductionListener;
import Model.OffreReduction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;

public class OffreReductionJTable extends JTable implements OffreReductionListener {
    private TableModel model = new TableModel();
    private static TableRowSorter<TableModel> sorter;

    public OffreReductionJTable() {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        OffreReductionController.getInstance().addOffreListener(this);
        updateTable(loadAll());
    }

    void updateTable(ArrayList<OffreReduction> offreReductions){
        // Effacez le contenu actuel de votre JTable
        clearTable();
        // Ajoutez les nouvelles voitures à votre JTable
        for (OffreReduction offreReduction : offreReductions) {
            model.insertRow(0, offreReduction.toArray());
        }
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.fireTableDataChanged();
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }

    public ArrayList<OffreReduction> loadAll(){
        ArrayList<OffreReduction> offres = new ArrayList<>();
        try {
            offres = (ArrayList<OffreReduction>) OffreReductionController.getInstance().allOffresReduction();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return offres;
    }

    @Override
    public void offreadd(MailEvent<OffreReduction> event) {model.insertRow(0, event.getSource().toArray());}

    private class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[]{"Nom", "Description", "Date de début", "Date de fin", "Réduction", "Type d'adhesion"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
}
