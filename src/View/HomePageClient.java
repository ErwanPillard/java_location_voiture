package View;

import Model.Voiture;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Controller.VoitureController;

public class HomePageClient extends JFrame {
    public HomePageClient() {
        super("Page de location");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        JLabel appTitle = new JLabel("CARECE", SwingConstants.CENTER);
        JLabel pageTitle = new JLabel("VOTRE LOCATION", SwingConstants.CENTER);

        appTitle.setFont(new Font("Arial", Font.BOLD, 40)); // Taille de police ajustée
        pageTitle.setFont(new Font("Arial", Font.BOLD, 30)); // Taille de police ajustée
        appTitle.setForeground(new Color(0, 128, 0)); // Couleur du texte en vert
        pageTitle.setForeground(new Color(0, 128, 0)); // Couleur du texte en vert
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre le titre dans le northPanel
        pageTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre le titre dans le northPanel

        northPanel.add(Box.createVerticalGlue());
        northPanel.add(appTitle);
        northPanel.add(pageTitle);
        northPanel.add(Box.createVerticalGlue());

        // Barre de recherche
        JPanel searchPanel = createSearchPanel();

        // Liste du nordPanel
        northPanel.add(appTitle); // Ajoute le titre au northPanel
        northPanel.add(pageTitle); // Ajoute le titre au northPanel
        northPanel.add(searchPanel); // Ajoute la barre de recherche au northPanel

        // Ajoute le northPanel contenant le titre et la barre de recherche en haut du mainPanel
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Affichage des voitures
        JPanel carPanel = createCarPanel();
        mainPanel.add(carPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createCarPanel() {
        JPanel carPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 voitures par ligne, espacement de 10 pixels
        carPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10 pixels autour du panel

        // Liste fictive de voitures
        List<Voiture> voitures = new ArrayList<>();

        try{
            voitures = VoitureController.getInstance().allVoitures();

        }catch (SQLException e){
            e.printStackTrace();
        }

       /* voitures.add(new Car("1234 ABC", "01/01/2022", 5000, "Grise", "Modèle A", "/Pictures/Berline.png"));
        voitures.add(new Car("5678 XYZ", "15/02/2023", 3000, "Noire", "Modèle B", "/Pictures/Citadine.png"));
        voitures.add(new Car("9876 DEF", "10/05/2021", 8000, "Kaki", "Modèle C", "/Pictures/Familliale.png"));
        voitures.add(new Car("1234 XYZ", "15/07/2023", 6000, "Noire", "Modèle B", "/Pictures/SUV.png"));
        voitures.add(new Car("5854 DEF", "10/10/2021", 9000, "Kaki", "Modèle C", "/Pictures/Utillitaire.png"));*/

        // Affichage des voitures
        for (Voiture voiture : voitures) {
            JPanel carInfoPanel = new JPanel(new BorderLayout());
            carInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            byte[] image = Voiture.getImageByImmatriculation(voiture.getImmatriculation());

            // Chargement de l'image de la voiture
            ImageIcon carImage = null;
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(image);
                Image img = ImageIO.read(bais);
                carImage = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            JLabel carImageLabel = new JLabel(carImage);
            carInfoPanel.add(carImageLabel, BorderLayout.NORTH);


            // Ajout des autres informations de la voiture
            JPanel carDetailsPanel = new JPanel(new GridLayout(0, 1));
            carDetailsPanel.add(new JLabel("Immatriculation: " + voiture.getImmatriculation()));
            carDetailsPanel.add(new JLabel("Date de mise en circulation: " + voiture.getDateMiseCirculation()));
            carDetailsPanel.add(new JLabel("Kilométrage: " + voiture.getNbKilometre()));
            carDetailsPanel.add(new JLabel("Couleur: " + voiture.getCouleur()));
            carDetailsPanel.add(new JLabel("Modèle: " + voiture.getModele_id()));

            carInfoPanel.add(carDetailsPanel, BorderLayout.CENTER);

            carPanel.add(carInfoPanel);
        }

        return carPanel;
    }


    private JPanel createSearchPanel() {
        // Panel de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(0xF8F8F8)); // Couleur de fond gris clair
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE); // Assure que le fond est blanc

        // Composants de la barre de recherche
        JTextField tfPickUpDate = new JTextField("sam. 6/4", 10);
        JTextField tfDropOffDate = new JTextField("sam. 13/4", 10);
        JButton btnSearch = new JButton("Rechercher");

        // Personnalisation des composants

        tfPickUpDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        tfDropOffDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        btnSearch.setBackground(new Color(0xFF5733)); // Couleur rouge/orange
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setOpaque(true);

        // Ajout des composants au panel
        searchPanel.add(new JLabel("Quand voulez-vous louer votre voiture ?"));
        searchPanel.add(tfPickUpDate);
        searchPanel.add(tfDropOffDate);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        return searchPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePageClient::new);
    }
}
