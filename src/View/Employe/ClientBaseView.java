package View.Employe;

import View.layouts.Options;

import javax.swing.*;
import java.awt.*;

public class ClientBaseView {

    public ClientBaseView(JPanel jpBody){
        createView(jpBody);
    }
    public void createView(JPanel jpBody) {
        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();

        // Ajoutez le JPanel principal à jpBody
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage
    }
}
