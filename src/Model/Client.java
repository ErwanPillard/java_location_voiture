package Model;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Client extends User{
    private int age;
    private String telephone;
    private double numeroAdhesion;
    private final boolean adhesion;
    private TypeAdhesion typeAdhesion;


    public Client(String email, String motDePasse, int age, String telephone){ //Client non adhérant
        super(email, motDePasse);
        this.age = age;
        this.telephone = telephone;
        this.adhesion = false;
    }

    public Client(String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion){ //Client adhérant
        super(email, motDePasse);
        this.age = age;
        this.telephone = telephone;
        this.adhesion = true;
        this.numeroAdhesion = numeroAdhesion;
        this.typeAdhesion = typeAdhesion;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
     */
    public void save() throws SQLException{
        //ClientDAOImpl.add();
    }
}


