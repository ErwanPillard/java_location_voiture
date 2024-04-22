package View;

public class Car {
        private String immatriculation;
        private String dateMiseEnCirculation;
        private int kilometrage;
        private String couleur;
        private String modele;
        private String imagePath;

        public Car(String immatriculation, String dateMiseEnCirculation, int kilometrage, String couleur, String modele, String imagePath) {
            this.immatriculation = immatriculation;
            this.dateMiseEnCirculation = dateMiseEnCirculation;
            this.kilometrage = kilometrage;
            this.couleur = couleur;
            this.modele = modele;
            this.imagePath = imagePath;
        }

        public String getImmatriculation() {
            return immatriculation;
        }

        public String getDateMiseEnCirculation() {
            return dateMiseEnCirculation;
        }

        public int getKilometrage() {
            return kilometrage;
        }

        public String getCouleur() {
            return couleur;
        }

        public String getModele() {
            return modele;
        }

        public String getImagePath() {
            return imagePath;
        }
    }


