package View.Employe;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomImageRenderer extends DefaultTableCellRenderer {
    private String imagePath;

    public CustomImageRenderer(String imagePath) {
        super();
        this.imagePath = imagePath;
        setHorizontalAlignment(JLabel.CENTER); // Center align the image
        setVerticalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();

        // Check if the value is an ImageIcon
        if (value instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) value;
            Image image = imageIcon.getImage().getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(image));
        }

        // Set background and foreground colors based on selection
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }

        return label;
    }
}
