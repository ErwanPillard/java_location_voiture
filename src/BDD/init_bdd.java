package BDD;

import Dao.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static View.HomePage.readImageAsBytes;

public class init_bdd {
    private static final String[] COLORS = {"Rouge", "Bleu", "Vert", "Noir", "Blanc", "Gris", "Jaune", "Orange", "Violet", "Rose"};
    private static final String DATE_MISE_EN_CIRCULATION = "2024-01-01";
    private static final String IMAGE_CITADINE = "/Pictures/Citadine.png";
    private static final String IMAGE_FAMILIALE = "/Pictures/Familiale.png";
    private static final String IMAGE_UTILITAIRE = "/Pictures/Utilitaire.png";
    private static final String IMAGE_BERLINE = "/Pictures/Berline.png";
    private static final String IMAGE_SUV = "/Pictures/SUV.png";

    public static void insertUsers(Connection connection) throws SQLException {
        String userSql = "INSERT INTO User (id, email, motDePasse) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(userSql)) {
            for (int i = 1; i <= 30; i++) {
                String hashed = BCrypt.hashpw(String.valueOf(i), BCrypt.gensalt(12)); // Hashage du mot de passe
                stmt.setInt(1, i); // id
                stmt.setString(2, "" + i); // Email
                stmt.setString(3, hashed); // Mot de passe
                stmt.executeUpdate();
            }
        }
    }

    public static void insertClient(Connection connection) throws SQLException {
        String clientSql = "INSERT INTO Client (id, telephone) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(clientSql)) {
            for (int i = 1; i <= 20; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "06112233" + i); // telephone
                stmt.executeUpdate();
            }
        }
    }

    public static void insertParticuliers(Connection connection) throws SQLException {
        String particulierSql = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(particulierSql)) {
            for (int i = 1; i <= 10; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "prenom" + i); // prenom
                stmt.setString(4, "Permis" + i); // numero de permis
                stmt.setDate(5, new java.sql.Date(new java.util.Date().getTime())); // date de naissance
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEntreprises(Connection connection) throws SQLException {
        String entrepriseSql = "INSERT INTO Entreprise (id, nom, numeroSiret) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(entrepriseSql)) {
            for (int i = 11; i <= 20; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "Siret" + i); // numero de siret
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEmployes(Connection connection) throws SQLException {
        String employeSql = "INSERT INTO Employe (id, nom, prenom, fonction) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(employeSql)) {
            for (int i = 21; i <= 30; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "prenom" + i); // prenom
                stmt.setString(4, "Fonction" + i); // fonction
                stmt.executeUpdate();
            }
        }
    }

    public static void insertModeles(Connection connection) throws SQLException {
        String modeleSql = "INSERT INTO Modele (id, marque, nom, nbPLaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage, boiteVitesse) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Random rand = new Random();
        String[] categories = {"Citadine", "Berline", "SUV", "Familiale", "Utilitaire"};

        try (PreparedStatement stmt = connection.prepareStatement(modeleSql)) {
            for (int i = 1; i <= 5; i++) {
                int nbPlaces = 4 + rand.nextInt(2); // Aléatoire entre 4 et 5
                int nbPortes = 3 + rand.nextInt(3); // Aléatoire entre 3 et 5
                float tailleCoffre = 300.0f + rand.nextFloat() * (500.0f - 300.0f); // Aléatoire entre 300.0 et 500.0
                int prixJournalier = 30*i; // Prix journalier
                Integer noteSatisfaction = rand.nextBoolean() ? rand.nextInt(5) + 1 : null; // Aléatoire entre 1 et 5 ou null
                String categorie = categories[rand.nextInt(categories.length)]; // Aléatoire parmi 'Citadine', 'Berline', 'SUV', 'Familiale', 'Utilitaire'
                String attelage = rand.nextBoolean() ? "Oui" : "Non"; // Aléatoire entre 'Oui' et 'Non'

                stmt.setInt(1, i); // id
                stmt.setString(2, "Marque" + i); // Marque
                if (i == 1) {
                    stmt.setString(3, "Citadine"); // nom
                } else if (i == 2) {
                    stmt.setString(3, "Familliale"); // nom
                } else if (i == 3) {
                    stmt.setString(3, "Utilitaire"); // nom
                } else if (i == 4) {
                    stmt.setString(3, "Berline"); // nom
                } else if (i == 5) {
                    stmt.setString(3, "SUV"); // nom
                }
                stmt.setInt(4, nbPlaces); // nombre de places
                stmt.setInt(5, nbPortes); // nombre de portes
                stmt.setFloat(6, tailleCoffre); // taille du coffre en litres
                stmt.setString(7, "Caracteristique" + i); // caractéristique
                stmt.setInt(8, prixJournalier); // prix journalier
                if (noteSatisfaction != null) {
                    stmt.setInt(9, noteSatisfaction); // note de satisfaction
                } else {
                    stmt.setNull(9, Types.INTEGER); // Si noteSatisfaction est null, on insère un NULL dans la base de données
                }
                stmt.setString(10, categorie); // catégorie
                stmt.setString(11, attelage); // attelage
                stmt.setString(12, "Automatique"); // boite de vitesse
                stmt.executeUpdate();
            }
        }
    }

    public static void insertReservation(Connection connection) throws SQLException {
        Random rand = new Random();

        // Utilisation de PreparedStatement pour les requêtes nécessitant des fonctions RAND
        PreparedStatement fetchClientStmt = connection.prepareStatement("SELECT id FROM Client ORDER BY RAND() LIMIT 60");
        PreparedStatement fetchVoituresStmt = connection.prepareStatement("SELECT v.immatriculation, m.prixJournalier FROM Voiture v JOIN Modele m ON v.modele_id = m.id ORDER BY RAND() LIMIT 60");

        String reservationSql = "INSERT INTO Reservation (dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?)";

        // Démarre explicitement une transaction
        connection.setAutoCommit(false);

        try (
                ResultSet rsVoitures = fetchVoituresStmt.executeQuery();
                ResultSet rsClients = fetchClientStmt.executeQuery();
                PreparedStatement insertStmt = connection.prepareStatement(reservationSql)
        ) {
            while (rsVoitures.next() && rsClients.next()) {
                String immatriculation = rsVoitures.getString("immatriculation");
                int prixJournalier = rsVoitures.getInt("prixJournalier");
                int idClient = rsClients.getInt("id");

                insertStmt.setDate(1, java.sql.Date.valueOf("2024-04-27")); // dateDebutReservation
                insertStmt.setDate(2, java.sql.Date.valueOf("2024-04-29")); // dateFinReservation
                insertStmt.setFloat(3, prixJournalier * 2); // montant
                String etat = rand.nextBoolean() ? "Confirmée" : "Non-confirmée"; // état
                insertStmt.setString(4, etat);
                insertStmt.setString(5, immatriculation); // immatriculation
                insertStmt.setInt(6, idClient); // id_client

                insertStmt.executeUpdate(); // Exécute la requête d'insertion
            }
            connection.commit(); // Valide les modifications
        } catch (SQLException e) {
            connection.rollback(); // Annule la transaction en cas d'erreur
            e.printStackTrace(); // Imprime l'erreur SQL
            throw e; // Relance l'exception
        } finally {
            connection.setAutoCommit(true); // Rétablit le mode auto-commit
        }
    }

    public static void insertFacture(Connection connection) throws SQLException {
        Random rand = new Random();

        // Requête pour récupérer des données aléatoires de voitures et modèles
        String fetchVoituresANDModeleSql = "SELECT v.immatriculation, m.prixJournalier FROM Voiture v JOIN Modele m ON v.modele_id = m.id ORDER BY RAND() LIMIT 60";
        String factureSql = "INSERT INTO Facture (dateEmission, dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false); // Désactiver l'auto-commit pour gérer manuellement la transaction

            try (Statement fetchStmt = connection.createStatement();
                 ResultSet rs = fetchStmt.executeQuery(fetchVoituresANDModeleSql);
                 PreparedStatement insertStmt = connection.prepareStatement(factureSql, Statement.RETURN_GENERATED_KEYS)) {

                while (rs.next()) {
                    String immatriculation = rs.getString("immatriculation");
                    int prixJournalier = rs.getInt("prixJournalier");
                    int idClient = rand.nextInt(100) + 1;  // Exemple d'ID client généré aléatoirement pour la démo

                    insertStmt.setDate(1, Date.valueOf("2024-04-29")); // dateEmission
                    insertStmt.setDate(2, Date.valueOf("2024-04-27")); // dateDebutReservation
                    insertStmt.setDate(3, Date.valueOf("2024-04-29")); // dateFinReservation
                    insertStmt.setInt(4, prixJournalier * 2); // montant
                    String etat = rand.nextBoolean() ? "Payé" : "Non-payé"; // état aléatoire
                    insertStmt.setString(5, etat);
                    insertStmt.setString(6, immatriculation);
                    insertStmt.setInt(7, idClient); // Simulé ici; idéalement, récupérer depuis une source valide

                    insertStmt.executeUpdate();

                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        long factureId = generatedKeys.getLong(1);
                        //System.out.println("Facture ajoutée avec succès, ID: " + factureId);
                    }
                }
                connection.commit(); // Commit de la transaction si tout est correct
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
                connection.rollback(); // Rollback en cas d'erreur
                throw e; // Relancer l'exception pour informer d'une erreur
            } finally {
                connection.setAutoCommit(true); // Réactiver l'auto-commit
            }
        } catch (SQLException e) {
            System.err.println("Problème de connexion ou de transaction : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static byte[] readImageAsBytes(String resourcePath) throws IOException {
        // Utilisation de getResourceAsStream pour charger l'image depuis le classpath
        try (InputStream is = init_bdd.class.getResourceAsStream(resourcePath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (is == null) {
                throw new IOException("Le fichier image n'existe pas dans le classpath : " + resourcePath);
            }

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    public static void insertCitadine(Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, image, modele_id) VALUES (?, ?, ?, ?, ?, ?)";

        byte[] imageBytes = readImageAsBytes(IMAGE_CITADINE);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Random random = new Random();

            for (int i = 1; i <= 10; i++) {
                String immatriculation = String.format("AA-%03d-AA", i); // Changer le format pour éviter les conflits
                int kilometrage = 100 + random.nextInt(901);  // Génère un nombre entre 100 et 1000
                String couleur = COLORS[random.nextInt(COLORS.length)];

                pstmt.setString(1, immatriculation);
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(DATE_MISE_EN_CIRCULATION)));
                pstmt.setInt(3, kilometrage);
                pstmt.setString(4, couleur);
                pstmt.setBytes(5, imageBytes);
                pstmt.setInt(6, 1);  // Assure-toi que l'ID du modèle pour "citadine" est correct

                pstmt.addBatch();  // Ajoute cette insertion au batch
            }

            pstmt.executeBatch();  // Exécute toutes les insertions en une seule fois
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rélancer l'exception après l'avoir loggée
        }
    }

    public static void insertFamiliale(Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, image, modele_id) VALUES (?, ?, ?, ?, ?, ?)";

        byte[] imageBytes = readImageAsBytes(IMAGE_FAMILIALE);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Random random = new Random();

            for (int i = 1; i <= 10; i++) {
                String immatriculation = String.format("BB-%03d-BB", i); // Changer le format pour éviter les conflits
                int kilometrage = 100 + random.nextInt(901);  // Génère un nombre entre 100 et 1000
                String couleur = COLORS[random.nextInt(COLORS.length)];

                pstmt.setString(1, immatriculation);
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(DATE_MISE_EN_CIRCULATION)));
                pstmt.setInt(3, kilometrage);
                pstmt.setString(4, couleur);
                pstmt.setBytes(5, imageBytes);
                pstmt.setInt(6, 2);  // Assure-toi que l'ID du modèle pour "familiale" est correct

                pstmt.addBatch();  // Ajoute cette insertion au batch
            }

            pstmt.executeBatch();  // Exécute toutes les insertions en une seule fois
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rélancer l'exception après l'avoir loggée
        }
    }

    public static void insertUtilitaire(Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, image, modele_id) VALUES (?, ?, ?, ?, ?, ?)";

        byte[] imageBytes = readImageAsBytes(IMAGE_UTILITAIRE);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Random random = new Random();

            for (int i = 1; i <= 10; i++) {
                String immatriculation = String.format("CC-%03d-CC", i); // Changer le format pour éviter les conflits
                int kilometrage = 100 + random.nextInt(901);  // Génère un nombre entre 100 et 1000
                String couleur = COLORS[random.nextInt(COLORS.length)];

                pstmt.setString(1, immatriculation);
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(DATE_MISE_EN_CIRCULATION)));
                pstmt.setInt(3, kilometrage);
                pstmt.setString(4, couleur);
                pstmt.setBytes(5, imageBytes);
                pstmt.setInt(6, 3);  // Assure-toi que l'ID du modèle pour "utilitaire" est correct

                pstmt.addBatch();  // Ajoute cette insertion au batch
            }

            pstmt.executeBatch();  // Exécute toutes les insertions en une seule fois
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rélancer l'exception après l'avoir loggée
        }
    }

    public static void insertBerline(Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, image, modele_id) VALUES (?, ?, ?, ?, ?, ?)";

        byte[] imageBytes = readImageAsBytes(IMAGE_BERLINE);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Random random = new Random();

            for (int i = 1; i <= 10; i++) {
                String immatriculation = String.format("DD-%03d-DD", i); // Changer le format pour éviter les conflits
                int kilometrage = 100 + random.nextInt(901);  // Génère un nombre entre 100 et 1000
                String couleur = COLORS[random.nextInt(COLORS.length)];

                pstmt.setString(1, immatriculation);
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(DATE_MISE_EN_CIRCULATION)));
                pstmt.setInt(3, kilometrage);
                pstmt.setString(4, couleur);
                pstmt.setBytes(5, imageBytes);
                pstmt.setInt(6, 4);  // Assure-toi que l'ID du modèle pour "berline" est correct

                pstmt.addBatch();  // Ajoute cette insertion au batch
            }

            pstmt.executeBatch();  // Exécute toutes les insertions en une seule fois
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rélancer l'exception après l'avoir loggée
        }
    }

    public static void insertSUV(Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, image, modele_id) VALUES (?, ?, ?, ?, ?, ?)";

        byte[] imageBytes = readImageAsBytes(IMAGE_SUV);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Random random = new Random();

            for (int i = 1; i <= 10; i++) {
                String immatriculation = String.format("EE-%03d-EE", i); // Changer le format pour éviter les conflits
                int kilometrage = 100 + random.nextInt(901);  // Génère un nombre entre 100 et 1000
                String couleur = COLORS[random.nextInt(COLORS.length)];

                pstmt.setString(1, immatriculation);
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(DATE_MISE_EN_CIRCULATION)));
                pstmt.setInt(3, kilometrage);
                pstmt.setString(4, couleur);
                pstmt.setBytes(5, imageBytes);
                pstmt.setInt(6, 5);  // Assure-toi que l'ID du modèle pour "SUV" est correct

                pstmt.addBatch();  // Ajoute cette insertion au batch
            }

            pstmt.executeBatch();  // Exécute toutes les insertions en une seule fois
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rélancer l'exception après l'avoir loggée
        }
    }

    public static void clearData(Connection connection) throws SQLException {
        connection.prepareStatement("DELETE FROM Particulier").executeUpdate();
        connection.prepareStatement("DELETE FROM Entreprise").executeUpdate();
        connection.prepareStatement("DELETE FROM Employe").executeUpdate();
        connection.prepareStatement("DELETE FROM Voiture").executeUpdate();
        connection.prepareStatement("DELETE FROM Modele").executeUpdate();
        connection.prepareStatement("DELETE FROM Facture").executeUpdate();
        connection.prepareStatement("DELETE FROM Reservation").executeUpdate();
        //connection.prepareStatement("DELETE FROM User").executeUpdate(); // Fais attention avec cette ligne
    }
}
