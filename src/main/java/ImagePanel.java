import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImagePanel extends JPanel {
    private Image image;
    private List<Shape> shapes = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    public void setImage(Image img) {
        image = img;
        repaint();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    // Mouse listener to draw shapes

}