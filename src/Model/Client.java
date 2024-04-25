package Model;

import Controller.ClientController;
import Dao.ClientDAO;
import Dao.ClientDAOImpl;

import java.sql.SQLException;

public class Client extends User {
    //private final boolean adhesion;
    private String telephone;
    private int id;


    public Client(String email, String motDePasse, String telephone) { //Client non adhérant
        super(email, motDePasse);
        this.telephone = telephone;
        //this.adhesion = false;
    }

    public Client(int id) { //Client non adhérant
        super(id);
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Méthode pour sauvegarder l'objet User dans la base de données
     * @throws SQLException
     **/
    public void save() throws SQLException {
        //ClientDAOImpl.add();
    }


    public static boolean emailExists(String email) throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.emailExists(email);
    }
}


