package View.Employe;

import Controller.VoitureController;
import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Model.Voiture;
import View.listeners.EventListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class VoitureJTableView extends JTable implements VoitureListener, EventListener {
    private TableModel model = new TableModel();
    public VoitureJTableView(){
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        VoitureController.getInstance().addUserListener(this);
        loadVoitures();
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
    }

    private class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[] {"Immatriculation", "Mise en circulation", "Kilometrage", "Couleur", "Modele"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }

    @Override
    public void cmdEdit() {
        System.out.println(this.getSelectedRow());
        throw new UnsupportedOperationException("Edition non implémentée.");
    }

    @Override
    public void cmdRemove() {

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
