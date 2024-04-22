package View.Employe.Button;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomButtonRenderer extends JButton implements TableCellRenderer {

    public CustomButtonRenderer() {
        // Rendre le bouton opaque pour qu'il soit visible
        setOpaque(true);
        // Aligner le texte au centre du bouton
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Personnaliser l'apparence du bouton en fonction des besoins
        setText("Show Picture"); // Texte du bouton
        // Couleur de fond du bouton en fonction de la sélection de la cellule
        setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        // Couleur du texte du bouton en fonction de la sélection de la cellule
        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        // Retourner le bouton personnalisé pour affichage dans la cellule
        return this;
    }
}
