import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle() {}  // Constructor sin argumentos para Gson

    public Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawRect(x, y, width, height);
    }

    @Override
    public void drawPreview(Graphics2D g2d) {
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 128));
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawRect(x, y, width, height);
    }

    @Override
    public double getArea() {
        return width * height;
    }
}