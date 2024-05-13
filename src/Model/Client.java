package Model;

import Dao.*;

import java.sql.SQLException;
import java.util.List;

public class Client extends User {
    //private final boolean adhesion;
    private String telephone;
    private int id;


    public Client(String email, String motDePasse, String telephone) { //Client non adhérant
        super(email, motDePasse);
        this.telephone = telephone;
    }

    public Client(String email, String telephone) { //Client non adhérant
        super(email);
        this.telephone = telephone;
    }



    public Client(int id) { //Client non adhérant
        super(id);
        this.id = id;
    }

    public Client(int id, String telephone) { //Client non adhérant
        super(id);
        this.id = id;
        this.telephone = telephone;
    }

    public static List<User> allUserClient() throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.allUserClient();
    }

    public static List<Particulier> allParticuliers() throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.allParticuliers();
    }


    public static Client findByTelephone(String telephone) throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.findByTelephone(telephone);
    }

    public static Client getClientById(int id) throws SQLException{
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getClientById(id);
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


