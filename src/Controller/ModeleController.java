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

    public void addModele(Modele modele) throws SQLException{
        if (modele != null){
            modele.add(modele);
        }
    }

    public String[] allModele() throws SQLException{
        return Modele.all();
    }

    public int getIdByName(String modelName) throws SQLException{
        return Modele.getIdByName(modelName);
    }

    public static Modele getModeleById(int id) throws SQLException{
        return Modele.getModeleById(id);
    }

    public String getNameById(int id) throws SQLException{
        return Modele.getNameById(id);
    }

    public void upate(Modele modele) throws SQLException{
        Modele.update(modele);
    }
}
