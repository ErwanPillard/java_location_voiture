package Controller;


import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Client;
import Model.Entreprise;
import Model.Particulier;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

import static BDD.init_bdd.*;
public class ClientController {
    private static final ClientController instance = new ClientController();
    public static ClientController getInstance() {
        return instance;
    }

    public void addParticulier(Particulier particulier) throws SQLException{
        if (particulier !=null){
            particulier.add(particulier);
        }
    }

    public void addEntreprise(Entreprise entreprise) throws SQLException {
        if (entreprise != null){
            entreprise.add(entreprise);
        }
    }

    public boolean emailExists(String email) throws SQLException {return Client.emailExists(email);}

}