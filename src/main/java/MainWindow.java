import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainWindow extends JFrame {
    private ImagePanel imagePanel;
    private JComboBox<ShapeType> shapeComboBox;
    private JComboBox<AreaType> areaTypeComboBox;
    private ColorSelector colorSelector;
    private JButton loadButton, saveButton, calculateButton, clearButton, loadShapesButton;
    private JTextField areaTextField;
    private JTextField areaIdTextField;
    private JButton sendEvaluationButton;
    private JComboBox<AreaEvaluada> areaComboBox;
    private JButton loadAreaButton;
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
        setPreferredSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);

        // Inicialización de componentes
        imagePanel = new ImagePanel();
        shapeComboBox = new JComboBox<>(ShapeType.values());
        areaTypeComboBox = new JComboBox<>(AreaType.values());
        colorSelector = new ColorSelector();
        loadButton = new JButton("Load Image");
        saveButton = new JButton("Save Shapes");
        calculateButton = new JButton("Calculate Area");
        clearButton = new JButton("Clear Shapes");
        loadShapesButton = new JButton("Load Shapes");
        areaTextField = new JTextField(10);
        areaTextField.setEditable(false);
        areaIdTextField = new JTextField(10);
        sendEvaluationButton = new JButton("Send Evaluation");
        areaComboBox = new JComboBox<>();
        loadAreaButton = new JButton("Load Area");
        // Configuración de layout y adición de componentes
        setLayout(new BorderLayout(10, 10));

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 50, 1));

        controlPanel.add(new JLabel("Area Type:"));
        controlPanel.add(areaTypeComboBox);
        controlPanel.add(new JLabel("Shape:"));
        controlPanel.add(shapeComboBox);
        controlPanel.add(new JLabel("Color:"));
        controlPanel.add(colorSelector);
        controlPanel.add(loadButton);
        controlPanel.add(saveButton);
        controlPanel.add(calculateButton);
        controlPanel.add(clearButton);
        controlPanel.add(loadShapesButton);
        controlPanel.add(new JLabel("Native Forest Area:"));
        controlPanel.add(areaTextField);
        controlPanel.add(new JLabel("Area ID:"));
        controlPanel.add(areaIdTextField);
        controlPanel.add(sendEvaluationButton);
        controlPanel.add(new JLabel("Select Area:"));
        controlPanel.add(areaComboBox);
        controlPanel.add(loadAreaButton);



        add(controlPanel, BorderLayout.SOUTH);

        // Configuración de action listeners
        loadButton.addActionListener(e -> loadImage());
        saveButton.addActionListener(e -> saveShapes());
        calculateButton.addActionListener(e -> calculateArea());
        clearButton.addActionListener(e -> clearShapes());
        loadShapesButton.addActionListener(e -> loadShapes());
        shapeComboBox.addActionListener(e -> imagePanel.setCurrentShapeType((ShapeType) shapeComboBox.getSelectedItem()));
        areaTypeComboBox.addActionListener(e -> imagePanel.setCurrentAreaType((AreaType) areaTypeComboBox.getSelectedItem()));
        colorSelector.addActionListener(e -> imagePanel.setCurrentColor(colorSelector.getSelectedColor()));
        sendEvaluationButton.addActionListener(e -> sendEvaluation());
        loadAreaButton.addActionListener(e -> loadSelectedArea());

        // Finalización de la configuración
        pack();
        setVisible(true);
    }

    private void clearShapes() {
        imagePanel.clearShapes();
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
            FileHandler.saveShapes(imagePanel.getAllShapes(), selectedFile.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Shapes saved successfully!");
        }
    }

    private void loadShapes() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            List<Shape> loadedShapes = FileHandler.loadShapes(selectedFile.getAbsolutePath());
            if (loadedShapes == null) {
                JOptionPane.showMessageDialog(this, "Error loading shapes", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                imagePanel.clearShapes();
                for (Shape shape : loadedShapes) {
                    if (shape.getAreaType().equals("Total Area")) {
                        imagePanel.setShapeForAreaType(AreaType.TOTAL_AREA, shape);
                    } else {
                        imagePanel.setShapeForAreaType(AreaType.FOREST_AREA, shape);
                    }
                }
            }
            imagePanel.repaint();
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
    private void sendEvaluation() {
        try {
            Long areaId = Long.parseLong(areaIdTextField.getText());
            List<Shape> shapes = imagePanel.getAllShapes();

            String response = ApiClient.sendEvaluation(areaId, shapes);
            JOptionPane.showMessageDialog(this, "Evaluation sent successfully! Response: " + response);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Area ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error sending evaluation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadAreasEvaluadas() {
        try {
            List<AreaEvaluada> areas = ApiClient.getAreasEvaluadas();
            areaComboBox.removeAllItems();
            for (AreaEvaluada area : areas) {
                areaComboBox.addItem(area);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading areas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadSelectedArea() {
        AreaEvaluada selectedArea = (AreaEvaluada) areaComboBox.getSelectedItem();
        if (selectedArea == null) {
            JOptionPane.showMessageDialog(this, "Please select an area first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            AreaEvaluada fullArea = ApiClient.getAreaById(selectedArea.getId());
            updateUIWithAreaData(fullArea);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading area: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateUIWithAreaData(AreaEvaluada area) {
        // Actualizar los campos de la UI con los datos del área
        areaIdTextField.setText(String.valueOf(area.getId()));

        // Actualizar las formas en el imagePanel
        imagePanel.clearShapes();
        if (area.getAreaTotal() > 0) {
            Shape totalAreaShape = createShapeFromArea(area.getAreaTotal(), Color.RED);
            imagePanel.setShapeForAreaType(AreaType.TOTAL_AREA, totalAreaShape);
        }
        if (area.getAreaBosqueNativo() > 0) {
            Shape forestAreaShape = createShapeFromArea(area.getAreaBosqueNativo(), Color.GREEN);
            imagePanel.setShapeForAreaType(AreaType.FOREST_AREA, forestAreaShape);
        }
        imagePanel.repaint();

        // Actualizar otros campos según sea necesario
        // Por ejemplo:
        // propietarioTextField.setText(area.getPropietario().getNombre());
        // evaluadorTextField.setText(area.getEvaluador().getNombre());
    }
    private Shape createShapeFromArea(double area, Color color) {
        // Crear una forma cuadrada basada en el área
        int side = (int) Math.sqrt(area);
        return new Rectangle(0, 0, side, side, color, "Rectangle", "");
    }



    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}