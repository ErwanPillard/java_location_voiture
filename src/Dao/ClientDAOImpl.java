package Dao;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDAOImpl implements ClientDAO {

    @Override
    public void addParticulier(Particulier particulier) throws SQLException {
        String queryUser = "INSERT INTO User (email, motDePasse) VALUES (?, ?)";
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryParticulier = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement particulierStatement = connection.prepareStatement(queryParticulier)) {
            // Insertion des données dans la table User
            userStatement.setString(1, particulier.getEmail());
            userStatement.setString(2, particulier.getMotDePasse());
            userStatement.executeUpdate();

            // Récupération de l'ID généré pour l'utilisateur inséré
            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID de l'utilisateur généré.");
            }
            // Insertion des données dans la table Client
            clientStatement.setInt(1, userId);
            clientStatement.setString(2, particulier.getTelephone());
            clientStatement.executeUpdate();

            particulierStatement.setInt(1, userId);
            particulierStatement.setString(2, particulier.getNom());
            particulierStatement.setString(3, particulier.getPrenom());
            particulierStatement.setString(4, particulier.getNumeroPermis());

            particulierStatement.setDate(5, Date.valueOf(particulier.getBirthDate()));
            particulierStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<User> allUserClient() throws SQLException {
        ArrayList<User> clients = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM User JOIN Client ON User.id = Client.id;");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            clients.add(createUserAsClient(rset));
        }

        pstmt.close();
        c.close();

        return clients;
    }

    public List<Particulier> allParticuliers() throws SQLException {
        ArrayList<Particulier> particuliers = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT P.id, P.nom, P.prenom, P.numeroPermis, P.birthDate, C.telephone, U.email FROM Particulier P JOIN Client C ON P.id = C.id JOIN User U ON U.id = C.id");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            particuliers.add(createParticuliers(rset));
        }

        pstmt.close();
        c.close();

        return particuliers;
    }

    public List<Entreprise> allEntreprises() throws SQLException {
        ArrayList<Entreprise> entreprises = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT E.id, E.nom, E.numeroSiret, C.telephone, U.email FROM Entreprise E JOIN Client C ON E.id = C.id JOIN User U ON U.id = C.id");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            entreprises.add(createEntreprises(rset));
        }

        pstmt.close();
        c.close();

        return entreprises;
    }

    public Particulier createParticuliers(ResultSet rset) throws SQLException {
        Particulier particulier = new Particulier(rset.getString("nom"), rset.getString("prenom"), rset.getString("email"),rset.getString("telephone"),rset.getString("numeroPermis"), rset.getDate("birthDate").toLocalDate());
        return particulier;
    }

    public Entreprise createEntreprises(ResultSet rset) throws SQLException {
        Entreprise entreprise = new Entreprise(rset.getString("nom"), rset.getString("email"), rset.getString("telephone"),rset.getString("numeroSiret"));
        return entreprise;
    }

    @Override
    public void addEntreprise(Entreprise entreprise) throws SQLException {
        String queryUser = "INSERT INTO User (email, motDePasse) VALUES (?, ?)";
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryEntreprise = "INSERT INTO Entreprise (id, nom, numeroSiret) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement entrepriseStatement = connection.prepareStatement(queryEntreprise)) {
            // Insertion des données dans la table User
            userStatement.setString(1, entreprise.getEmail());
            userStatement.setString(2, entreprise.getMotDePasse());
            userStatement.executeUpdate();

            // Récupération de l'ID généré pour l'utilisateur inséré
            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID de l'utilisateur généré.");
            }

            // Insertion des données dans la table Client
            clientStatement.setInt(1, userId);
            clientStatement.setString(2, entreprise.getTelephone());
            clientStatement.executeUpdate();

            entrepriseStatement.setInt(1, userId);
            entrepriseStatement.setString(2, entreprise.getNom());
            entrepriseStatement.setString(3, entreprise.getNumSiret());
            entrepriseStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(email) FROM User WHERE email = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Vérifie si le compteur est supérieur à 0
                }
            }
        }
        return false; // Par défaut, considère que l'email n'existe pas
    }

    public Client findByTelephone(String telephone) throws SQLException {
        Connection c = DatabaseManager.getConnection();

        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Voiture WHERE immatriculation = ?");
        pstmt.setString(1, telephone);

        Client client = null;
        ResultSet rset = pstmt.executeQuery();

        /*while (rset.next()) {
            client = createClient(rset);
        }*/

        pstmt.close();
        c.close();

        return client;
    }

    public Client getClientById(int id) throws SQLException{
        Connection c = DatabaseManager.getConnection();

        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Client WHERE id = ?");
        pstmt.setString(1, String.valueOf(id));

        Client client = null;
        ResultSet rset = pstmt.executeQuery();

        client = new Client(id, rset.getString("telephone"));

        pstmt.close();
        c.close();

        return client;
    }

    public User createUserAsClient(ResultSet rset) throws SQLException {
        User user = new User(rset.getString("email"));
        return user;
    }

   /* @Override
    public List<Client> all() throws SQLException {
        // Déclaration d'une liste pour stocker les noms des modèles
        ArrayList<String> clientList = new ArrayList<>();

        // Requête SQL pour sélectionner les noms des modèles
        String queryClient= "SELECT email FROM Client;";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryClient)) {

            // Parcourir les résultats et ajouter les noms à la liste
            while (resultSet.next()) {
                String clientEmail = resultSet.getString("email");
                clientList.add(clientEmail);
            }
        }

        // Convertir la liste en tableau de chaînes de caractères
        String[] client = clientList.toArray(new String[0]);

        return client;
    }*/

}