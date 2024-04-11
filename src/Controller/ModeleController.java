package Controller;

import Dao.ModeleDAO;
import Dao.ModeleDAOImpl;
import Model.Categorie;
import Model.Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static BDD.init_bdd.*;

public class ModeleController {
    public void addModele(String nom, int nbPlace, int  nbPorte, float tailleCoffre, String caracteristique, int prixJournalier, int noteSatisfaction, Categorie categorie, boolean attelage){

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            System.out.println("on passe en addModele");
            Modele modele = new Modele(nom, nbPlace, nbPorte,tailleCoffre, caracteristique, prixJournalier,noteSatisfaction, categorie, attelage);
            ModeleDAO modeleDAO = new ModeleDAOImpl(connection);
            modeleDAO.add(modele);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
