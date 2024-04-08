package Dao;

public interface UserConnexion {
    /**
     * Tente de connecter un utilisateur avec le nom d'utilisateur et le mot de passe fournis.
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return true si la connexion est réussie, false sinon.
     */
    boolean connect(String username, String password);
}
