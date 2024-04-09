package Controller;

import Dao.UserConnection;

import Model.SessionManager;
import Model.User;

import View.ConnexionUtilisateur;

import javax.swing.*;

public class UserConnectionController {
    private ConnexionUtilisateur connexionUtilisateurView;
    private UserConnection userConnectionDao;
    private JDialog loginDialog;

    public UserConnectionController(ConnexionUtilisateur connexionUtilisateurView, UserConnection userConnectionDao) {
        this.connexionUtilisateurView = connexionUtilisateurView;
        this.userConnectionDao = userConnectionDao;
        initController();
    }
    public void showLoginDialog(JFrame parentFrame) {
        loginDialog = new JDialog(parentFrame, "Connexion", true);
        loginDialog.setContentPane(connexionUtilisateurView);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(parentFrame);
        loginDialog.setVisible(true);
    }

    private void initController() {
        connexionUtilisateurView.getLoginButton().addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String username = connexionUtilisateurView.getUsername();
        String password = connexionUtilisateurView.getPassword();

        User user = userConnectionDao.connect(username, password);
        if (user != null) {
            SessionManager.getInstance().logIn(user);
            JOptionPane.showMessageDialog(null, "Connexion réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            loginDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la connexion. Veuillez vérifier vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
