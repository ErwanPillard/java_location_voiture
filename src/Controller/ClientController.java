package Controller;


import Controller.listeners.ClientListener;
import Controller.listeners.VoitureListener;
import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Client;
import Model.Entreprise;
import Model.Particulier;
import Model.Voiture;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static BDD.init_bdd.*;
public class ClientController {

    private List<ClientListener> clientListeners = new ArrayList<ClientListener>();

    private static final ClientController instance = new ClientController();
    public static ClientController getInstance() {
        return instance;
    }

    public void addParticulier(Particulier particulier) throws SQLException{
        if (particulier !=null){
            particulier.add(particulier);
        }
    }


    /*public List<Particulier> allParticulier() throws SQLException {
        return Particulier.all();
    }*/

    public void addEntreprise(Entreprise entreprise) throws SQLException {
        if (entreprise != null){
            entreprise.add(entreprise);
        }
    }

    public   List<Client> allClients() throws SQLException {
        return Client.all();
    }
    public Client findByTelephone(String telephone) throws SQLException {
        return Client.findByTelephone(telephone);
    }


    public boolean emailExists(String email) throws SQLException {return Client.emailExists(email);}
    public synchronized void addUserListener(ClientListener l) {
        if (!clientListeners.contains(l)) {
            clientListeners.add(l);
        }
    }
}

