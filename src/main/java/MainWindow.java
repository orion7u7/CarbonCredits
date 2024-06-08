import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainWindow extends JFrame {
    private ImagePanel imagePanel;
    private JComboBox<ShapeType> shapeComboBox;
    private JComboBox<AreaType> areaTypeComboBox;
    private ColorSelector colorSelector;
    private JButton loadButton, saveButton, calculateButton;
    private JTextField areaTextField;

    public enum AreaType {
        TOTAL_AREA("Total Area"), FOREST_AREA("Forest Area");

        private final String displayName;

        AreaType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public MainWindow() {
        // Configuración inicial
        setTitle("Carbon Credits Evaluator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        setLocationRelativeTo(null);

        // Inicialización de componentes
        imagePanel = new ImagePanel();
        shapeComboBox = new JComboBox<>(ShapeType.values());
        areaTypeComboBox = new JComboBox<>(AreaType.values());
        colorSelector = new ColorSelector();
        loadButton = new JButton("Load Image");
        saveButton = new JButton("Save Shapes");
        calculateButton = new JButton("Calculate Area");
        areaTextField = new JTextField(10);
        areaTextField.setEditable(false);

        // Configuración de layout y adición de componentes
        setLayout(new BorderLayout(10, 10));

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        controlPanel.add(new JLabel("Area Type:"));
        controlPanel.add(areaTypeComboBox);
        controlPanel.add(new JLabel("Shape:"));
        controlPanel.add(shapeComboBox);
        controlPanel.add(new JLabel("Color:"));
        controlPanel.add(colorSelector);
        controlPanel.add(loadButton);
        controlPanel.add(saveButton);
        controlPanel.add(calculateButton);
        controlPanel.add(new JLabel("Native Forest Area:"));
        controlPanel.add(areaTextField);

        add(controlPanel, BorderLayout.SOUTH);

        // Configuración de action listeners
        loadButton.addActionListener(e -> loadImage());
        saveButton.addActionListener(e -> saveShapes());
        calculateButton.addActionListener(e -> calculateArea());
        shapeComboBox.addActionListener(e -> imagePanel.setCurrentShapeType((ShapeType) shapeComboBox.getSelectedItem()));
        areaTypeComboBox.addActionListener(e -> imagePanel.setCurrentAreaType((AreaType) areaTypeComboBox.getSelectedItem()));
        colorSelector.addActionListener(e -> imagePanel.setCurrentColor(colorSelector.getSelectedColor()));

        // Finalización de la configuración
        pack();
        setVisible(true);
    }

    // Métodos para manejar acciones de botones
    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                if (img == null) {
                    throw new IOException("Failed to load image");
                }
                imagePanel.setImage(img);
                pack();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveShapes() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".json")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".json");
            }
            FileHandler.saveShapes((List<Shape>)imagePanel.getAllShapes(), selectedFile.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Shapes saved successfully!");
        }
    }

    private void calculateArea() {
        Shape totalArea = imagePanel.getShapeByType(AreaType.TOTAL_AREA);
        Shape forestArea = imagePanel.getShapeByType(AreaType.FOREST_AREA);

        if (totalArea == null || forestArea == null) {
            JOptionPane.showMessageDialog(this, "Please define both Total Area and Forest Area.");
            return;
        }

        double percentage = (forestArea.getArea() / totalArea.getArea()) * 100;
        areaTextField.setText(String.format("%.2f%%", percentage));
    }


    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}