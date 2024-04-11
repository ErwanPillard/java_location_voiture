package Dao;

import Model.Modele;
import Model.Particulier;

import java.sql.*;


public class ModeleDAOImpl implements ModeleDAO{
    private Connection connection;

    public ModeleDAOImpl(Connection connection){this.connection = connection;}


    @Override
    public void add(Modele modele) throws SQLException {
        String queryModele = "INSERT INTO Modele (nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage) VALUES (?, ?, ?, ?,?,?,?,?,?)";

        try (PreparedStatement modeleStatement = connection.prepareStatement(queryModele, Statement.RETURN_GENERATED_KEYS)) {
            // Insertion des données dans la table Modele
            modeleStatement.setString(1, modele.getNom());
            modeleStatement.setInt(2, modele.getNbPlace());
            modeleStatement.setInt(3, modele.getNbPorte());
            modeleStatement.setFloat(4, modele.getTailleCoffre());
            modeleStatement.setString(5, modele.getCaracteristiques());
            modeleStatement.setInt(6, modele.getPrixJournalier());
            modeleStatement.setFloat(7, modele.getNoteSatisfaction());
            modeleStatement.setString(8, modele.getCategorie().getNomCategorie());
            modeleStatement.setBoolean(9, modele.isAttelage());
            modeleStatement.executeUpdate();
                System.out.println("on après caractère ");
            // Récupération de l'ID généré pour le modele inséré
            ResultSet generatedKeys = modeleStatement.getGeneratedKeys();
            int modeleId = -1;
            if (generatedKeys.next()) {
                modeleId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID du modele généré.");
            }
            System.out.println("Le modele a été ajouté a la BDD");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
