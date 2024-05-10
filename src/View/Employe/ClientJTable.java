package View.Employe;

import Controller.ClientController;
import Controller.listeners.ClientListener;
import Controller.listeners.MailEvent;
import Model.Client;
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
import java.util.ArrayList;

public class ClientJTable extends JTable implements ClientListener, EventListener {
    private TableModel model = new TableModel();
    private static TableRowSorter<TableModel> sorter;

    private Object originalValue;

    public ClientJTable() {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        ClientController.getInstance().addUserListener(this);// ajoute user au tab pas utile
        updateTable(loadAll());// verifi si les celluels sont editable
        cellEdit();
        sorter = new TableRowSorter<>(model);
        setRowSorter(sorter); // Assignez le TableRowSorter à la JTable
    }


    void updateTable(ArrayList<Client> clients){
        // Effacez le contenu actuel de votre JTable
        clearTable();
        // Ajoutez les nouvelles voitures à votre JTable
        for (Client client : clients) {
            model.insertRow(0, client.toArray());
        }
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.fireTableDataChanged();
    }
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }


    public ArrayList<Client> loadAll(){
        ArrayList<Client> clients = new ArrayList<>();
        try {
            clients = (ArrayList<Client>) ClientController.getInstance().allClients();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return clients;
    }


    public class TableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public TableModel() {
            super(new Object[][]{}, new String[]{"Nom","Prénom", "N°Téléphone", "Date de Naissance"});
        }

        @Override
        public boolean isCellEditable(int row, int column) {// permet de modif les cellules
            if
            (column == 0 || column == 1 ) {

                //(column == 0 || column == 1 || column == 5 || column == 6 || column == 7 || column == 12 || column == 14) {
                return false;
            }
            return true;

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
                            Client client = ClientController.getInstance().findByTelephone((String) getValueAt(row, 0));
                            //Particulier particulier = ModeleController.getModeleById(voiture.getModele_id());
                            //ParkAutoView.displayImageVoiture(client.getTelephone());
                            ClientBaseView.displayModele(client);
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
                    /*if (!newValue.equals(originalValue)) {
                        try {
                            //update(column, newValue, immatriculation);
                            System.out.println("La cellule à la ligne " + row + ", colonne " + column + " a été modifiée.");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }*/
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                // Gérer l'annulation de l'édition de cellule si nécessaire
            }
        });
    }

    @Override
    public void clientadd(MailEvent<Voiture> event){model.insertRow(0, event.getSource().toArray());}
    // ca devrai etre un Cient

    @Override
    public void cmdEdit() {

    }

    @Override
    public void cmdRemove() {

    }

    @Override
    public void cmdAdd() {

    }
        /*public void update(int column, Object value, String tel) throws SQLException {
            Client client = Client.findByTelephone(tel);
            switch (column) {
                case 2:
                    //client.setNbKilometre(Double.parseDouble((String) value));
                    break;
                case 3:
                    //client.setCouleur((String) value);
                    break;
            }
            ClientController.getInstance().update(client);
        }*/
}