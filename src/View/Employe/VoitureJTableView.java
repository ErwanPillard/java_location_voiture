package View.Employe;

import Controller.VoitureController;
import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Model.Voiture;
import View.listeners.EventListener;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.time.LocalDate;

public class VoitureJTableView extends JTable implements VoitureListener, EventListener {
    private TableModel model = new TableModel();
    private TableRowSorter<TableModel> sorter;

    private Object originalValue;
    public VoitureJTableView(){
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        VoitureController.getInstance().addUserListener(this);
        loadVoitures();
        cellEdit();
    }

    public void loadVoitures(){
        try {
            for (Voiture voiture : VoitureController.getInstance().allVoitures()) {
                model.insertRow(0, voiture.toArray());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        sorter = new TableRowSorter<>(model);
        setRowSorter(sorter); // Assignez le TableRowSorter à la JTable
    }

    public class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[] {"Immatriculation", "Mise en circulation", "Kilometrage", "Couleur", "Modele"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 2 || column == 3){
                return true;
            }
            return false;
        }

    };

    @Override
    public void cmdEdit() {

    }

    public void cellEdit(){

        // Ajouter un écouteur pour détecter la sélection de cellule
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = getSelectedRow();
                    int column = getSelectedColumn();
                    if (row != -1 && column != -1) {
                        originalValue = getValueAt(row, column);
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
                        switch (column){
                            case 2:
                                newValue = Double.parseDouble((String) newValue);
                                break;
                            case 3:
                                newValue = String.valueOf(newValue);
                                break;

                        }
                        VoitureController.getInstance().update(column, newValue,immatriculation);
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
        if (this.getSelectedRow() != -1) {
            int row = this.getSelectedRow();
            String immatriculation = (String) this.getValueAt(row, 0);
            try {
                VoitureController.getInstance().remove(immatriculation);
                model.removeRow(row);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cmdAdd() {
        VoitureFormView.toggle();
    }

    @Override
    public void voitureadd(MailEvent<Voiture> event){
        model.insertRow(0, event.getSource().toArray());
    }
}
