package View.Employe.Button;

import Model.Voiture;
import View.Employe.VoitureFormView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class CustomButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private Object value;

    public CustomButtonEditor(String immatriculation) {
        button = new JButton("Show Picture");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showImageJFrame(immatriculation);
                fireEditingStopped();
            }
        });
    }

    private void showImageJFrame(String immat) {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        byte[] image = Voiture.getImageByImmatriculation(immat);

        if (image != null) { // Vérifier si le tableau de bytes n'est pas null
            ImageIcon carImage = null;
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(image);
                Image img = ImageIO.read(bais);
                carImage = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            JLabel carImageLabel = new JLabel(carImage);
            frame.add(carImageLabel, BorderLayout.NORTH);
        } else {
            JLabel noImageLabel = new JLabel("No image available");
            frame.add(noImageLabel, BorderLayout.NORTH);
        }

        JButton editButton = new JButton("Change image");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File selectedFile = VoitureFormView.getInstance().selectImage();
                if (selectedFile != null) {
                    try {
                        Voiture.addImage(immat, selectedFile);
                        frame.dispose();
                        showImageJFrame(immat); // Réouvrir la frame avec la nouvelle image
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(editButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Configurez le bouton en fonction de la valeur actuelle de la cellule
        this.value = value;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        // Renvoyer la valeur de la cellule après l'édition
        return value;
    }
}
