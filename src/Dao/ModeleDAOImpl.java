package Dao;

import Model.*;

import java.sql.*;
import java.util.ArrayList;


public class ModeleDAOImpl implements ModeleDAO{
    @Override
    public void add(Modele modele) throws SQLException {
        String queryModele = "INSERT INTO Modele (marque, nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, categorie, attelage, boiteVitesse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection() ;
             PreparedStatement modeleStatement = connection.prepareStatement(queryModele, Statement.RETURN_GENERATED_KEYS)) {
            // Insertion des données dans la table Modele
            modeleStatement.setString(1, modele.getMarque());
            modeleStatement.setString(2, modele.getNom());
            modeleStatement.setInt(3, modele.getNbPlace());
            modeleStatement.setInt(4, modele.getNbPorte());
            modeleStatement.setFloat(5, modele.getTailleCoffre());
            modeleStatement.setString(6, modele.getCaracteristiques());
            modeleStatement.setInt(7, modele.getPrixJournalier());
            modeleStatement.setString(8, modele.getCategorie().getNomCategorie());
            modeleStatement.setString(9, modele.isAttelage() ? "Oui" : "Non");
            modeleStatement.setString(10, modele.getBoiteVitesse().getTypeBoite());
            modeleStatement.executeUpdate();
            // Récupération de l'ID généré pour le modele inséré
            ResultSet generatedKeys = modeleStatement.getGeneratedKeys();
            int modeleId = -1;
            if (generatedKeys.next()) {
                modeleId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID du modele généré.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String[] all() throws SQLException {
        // Déclaration d'une liste pour stocker les noms des modèles
        ArrayList<String> modeleList = new ArrayList<>();

        // Requête SQL pour sélectionner les noms des modèles
        String queryModele = "SELECT nom FROM Modele;";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryModele)) {

            // Parcourir les résultats et ajouter les noms à la liste
            while (resultSet.next()) {
                String modeleNom = resultSet.getString("nom");
                modeleList.add(modeleNom);
            }
        }

        // Convertir la liste en tableau de chaînes de caractères
        String[] modeleDispo = modeleList.toArray(new String[0]);

        return modeleDispo;
    }

    public int getIdByName(String modelName) throws SQLException {
        int id = -1; // Valeur par défaut si aucun ID n'est trouvé

        // Requête SQL pour sélectionner l'ID du modèle par son nom
        String queryModele = "SELECT id FROM Modele WHERE nom=?";

        // Utilisation de try-with-resources pour s'assurer que les ressources sont fermées correctement
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryModele)) {

            // Définir le paramètre du nom du modèle dans la requête SQL
            statement.setString(1, modelName);

            // Exécuter la requête SQL
            try (ResultSet resultSet = statement.executeQuery()) {
                // Si un résultat est retourné, récupérez l'ID
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        }

        // Retourner l'ID du modèle
        return id;
    }

    public String getNameById(int id) throws SQLException{
        String nom = null;
        // Requête SQL pour sélectionner l'ID du modèle par son nom
        String queryModele = "SELECT nom FROM Modele WHERE id=?";

        // Utilisation de try-with-resources pour s'assurer que les ressources sont fermées correctement
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryModele)) {

            // Définir le paramètre du nom du modèle dans la requête SQL
            statement.setInt(1, id);

            // Exécuter la requête SQL
            try (ResultSet resultSet = statement.executeQuery()) {
                // Si un résultat est retourné, récupérez l'ID
                if (resultSet.next()) {
                    nom = resultSet.getString("nom");
                }
            }
        }
        // Retourner l'ID du modèle
        return nom;
    }

    public Modele createModele(ResultSet rset) throws SQLException {
        String categorieValue = rset.getString("categorie");
        Categorie categorie = Categorie.valueOf(categorieValue.toUpperCase());

        String attelageValue = rset.getString("attelage");
        boolean attelage = "Oui".equalsIgnoreCase(attelageValue);

        String boiteVitesseValue = rset.getString("boiteVitesse");
        BoiteVitesse boiteVitesse = BoiteVitesse.valueOf(boiteVitesseValue.toUpperCase());

        Modele modele = new Modele(
                rset.getInt("id"),
                rset.getString("marque"),
                rset.getString("nom"),
                rset.getInt("nbPlaces"),
                rset.getInt("nbPortes"),
                rset.getFloat("tailleCoffre"),
                rset.getString("caracteristique"),
                rset.getInt("prixJournalier"),
                rset.getFloat("noteSatisfaction"),
                categorie,
                attelage,
                boiteVitesse
        );
        return modele;
    }

    public void update(Modele modele) throws SQLException {
        String query = "UPDATE Modele SET marque = ?, nom = ?, nbPlaces = ?, nbPortes = ?, tailleCoffre = ?, caracteristique = ?, prixJournalier = ?, noteSatisfaction = ?, categorie = ?, attelage = ?, boiteVitesse = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Mettre à jour les valeurs des champs
            pstmt.setString(1, modele.getMarque());
            pstmt.setString(2, modele.getNom());
            pstmt.setInt(3, modele.getNbPlace());
            pstmt.setInt(4, modele.getNbPorte());
            pstmt.setFloat(5, modele.getTailleCoffre());
            pstmt.setString(6, modele.getCaracteristiques());
            pstmt.setInt(7, modele.getPrixJournalier());
            pstmt.setFloat(8, modele.getNoteSatisfaction());
            pstmt.setString(9, modele.getCategorie().toString());
            pstmt.setString(10, modele.isAttelage() ? "Oui" : "Non");
            pstmt.setString(11, modele.getBoiteVitesse().toString());
            pstmt.setInt(12, modele.getId()); // Assurez-vous d'avoir l'ID du modèle à mettre à jour

            // Exécuter la requête SQL
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public Modele getModeleById(int id) throws SQLException{
        Connection c = DatabaseManager.getConnection();

        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Modele WHERE id = ?");
        pstmt.setString(1, String.valueOf(id));

        Modele modele = null;
        ResultSet rset = pstmt.executeQuery();

        while (rset.next()) {
            modele = createModele(rset);
        }

        pstmt.close();
        c.close();

        return modele;
    }


}
