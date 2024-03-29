package Model;

public class Client{
    private int age;
    private String telephone;
    private double numeroAdhesion;
    private boolean adhesion;
    private TypeAdhesion typeAdhesion;


    public Client(int age, String telephone){ //Client non adhérant
        this.age = age;
        this.telephone = telephone;
        this.adhesion = false;
    }

    public Client(int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion){ //Client adhérant
        this.age = age;
        this.telephone = telephone;
        this.adhesion = true;
        this.numeroAdhesion = numeroAdhesion;
        this.typeAdhesion = typeAdhesion;
    }
}
