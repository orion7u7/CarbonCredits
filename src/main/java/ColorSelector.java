import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ColorSelector extends JPanel {
    private JComboBox<String> colorComboBox;

    public ColorSelector() {
        colorComboBox = new JComboBox<>(new String[]{"Red", "Green", "Blue"});
        add(colorComboBox);
    }

    public Color getSelectedColor() {
        String colorName = (String) colorComboBox.getSelectedItem();
        switch (colorName) {
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            default: return Color.BLACK;
        }
    }

    public void addActionListener(ActionListener listener) {
        colorComboBox.addActionListener(listener);
    }
}