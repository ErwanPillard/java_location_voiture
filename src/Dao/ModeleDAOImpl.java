package Dao;

import Model.Modele;
import Model.Particulier;

import java.sql.*;
import java.util.ArrayList;


public class ModeleDAOImpl implements ModeleDAO{
    @Override
    public void add(Modele modele) throws SQLException {
        String queryModele = "INSERT INTO Modele (nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage, boiteVitesse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection() ;
             PreparedStatement modeleStatement = connection.prepareStatement(queryModele, Statement.RETURN_GENERATED_KEYS)) {
            // Insertion des données dans la table Modele
            modeleStatement.setString(1, modele.getNom());
            modeleStatement.setInt(2, modele.getNbPlace());
            modeleStatement.setInt(3, modele.getNbPorte());
            modeleStatement.setFloat(4, modele.getTailleCoffre());
            modeleStatement.setString(5, modele.getCaracteristiques());
            modeleStatement.setInt(6, modele.getPrixJournalier());
            modeleStatement.setFloat(7, modele.getNoteSatisfaction());
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


}
