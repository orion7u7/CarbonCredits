import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private Map<MainWindow.AreaType, Shape> shapes = new HashMap<>();
    private ShapeType currentShapeType = ShapeType.Rectangle;
    private MainWindow.AreaType currentAreaType = MainWindow.AreaType.TOTAL_AREA;
    private Color currentColor = Color.RED;
    private Point startPoint;
    private Shape previewShape;

    public ImagePanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Shape shape = createShape(startPoint.x, startPoint.y, e.getX(), e.getY());
                shapes.put(currentAreaType, shape);
                previewShape = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                previewShape = createShape(startPoint.x, startPoint.y, e.getX(), e.getY());
                repaint();
            }
        });
    }

    private Shape createShape(int x1, int y1, int x2, int y2) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);

        switch (currentShapeType) {
            case Rectangle:
                return new Rectangle(x, y, width, height, currentColor);
            case Square:
                int size = Math.min(width, height);
                return new Square(x, y, size, currentColor);
            case Ellipse:
                return new Ellipse(x, y, width, height, currentColor);
            case Circle:
                int radius = Math.min(width, height) / 2;
                return new Circle(x + radius, y + radius, radius, currentColor);
            default:
                return new Rectangle(x, y, width, height, currentColor);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }

        for (Shape shape : shapes.values()) {
            shape.draw(g2d);
        }

        if (previewShape != null) {
            previewShape.drawPreview(g2d);
        }
    }

    public void setImage(BufferedImage img) {
        image = img;
        setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        revalidate();
        repaint();
    }

    public void setCurrentShapeType(ShapeType shapeType) {
        this.currentShapeType = shapeType;
    }

    public void setCurrentAreaType(MainWindow.AreaType areaType) {
        this.currentAreaType = areaType;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public Shape getShapeByType(MainWindow.AreaType areaType) {
        return shapes.get(areaType);
    }

    public void setShapeForAreaType(MainWindow.AreaType areaType, Shape shape) {
        shapes.put(areaType, shape);
    }

    public List<Shape> getAllShapes() {
        return new ArrayList<>(shapes.values());
    }
}