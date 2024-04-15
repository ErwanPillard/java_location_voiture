package Controller;

import Dao.ModeleDAO;
import Dao.ModeleDAOImpl;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static BDD.init_bdd.*;

public class ModeleController {

    private static final ModeleController instance = new ModeleController();
    public static ModeleController getInstance() {
        return instance;
    }

    public void addModele(String nom, int nbPlace, int  nbPorte, float tailleCoffre, String caracteristique, int prixJournalier, int noteSatisfaction, Categorie categorie, boolean attelage, BoiteVitesse boiteVitesse){

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            Modele modele = new Modele(nom, nbPlace, nbPorte,tailleCoffre, caracteristique, prixJournalier,noteSatisfaction, categorie, attelage, boiteVitesse);
            ModeleDAO modeleDAO = new ModeleDAOImpl(connection);
            modeleDAO.add(modele);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String[] allModele() throws SQLException{
        return Modele.all();
    }

    public int getIdByName(String modelName) throws SQLException{
        return Modele.getIdByName(modelName);
    }
}
