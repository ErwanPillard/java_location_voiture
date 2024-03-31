package View;

import javax.swing.*;
import java.awt.*;

public class addModele extends JFrame {
    public addModele(String title) {
        setTitle(title);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);k

        // Création de la zone de texte
        JTextArea zoneDeTexte = new JTextArea();
        zoneDeTexte.setLineWrap(true); // Permet le retour automatique à la ligne
        zoneDeTexte.setWrapStyleWord(true); // Assure le retour automatique à la ligne en fonction des mots
        zoneDeTexte.setBackground(Color.LIGHT_GRAY); // Définit la couleur de fond de la zone de texte en gris
        zoneDeTexte.getText();



        // Ajout de la zone de texte à un JScrollPane (pour permettre le défilement si nécessaire)
        JScrollPane scrollPane = new JScrollPane(zoneDeTexte);

        getContentPane().add(scrollPane, BorderLayout.CENTER); // Ajout du JScrollPane à la fenêtre

        setVisible(true); // Déplacez cet appel après l'ajout de tous les composants
    }

    public static void main(String[] args) {
        new addModele("Ajout de Modèle");
    }
}
