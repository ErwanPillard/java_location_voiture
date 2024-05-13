package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashingExample {

    public static void main(String[] args) {
        // Mot de passe en clair
        String password = "azerty";

        // Hachage du mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Affichage du mot de passe haché (à des fins de débogage, ne pas afficher dans un environnement de production)
        System.out.println("Mot de passe haché : " + hashedPassword);

        // Vous pouvez maintenant utiliser le mot de passe haché pour l'enregistrer dans la base de données
        // par exemple, en l'insérant dans la colonne motDePasse de la table User.
    }
}
