import java.awt.*;

public class Circle extends Shape {
    private int radius;

    public Circle() {}  // Constructor sin argumentos para Gson

    public Circle(int centerX, int centerY, int radius, Color color) {
        super(centerX - radius, centerY - radius, 2 * radius, 2 * radius, color);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawOval(x, y, width, height);
    }

    @Override
    public void drawPreview(Graphics2D g2d) {
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 128));
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawOval(x, y, width, height);
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // Getters y setters adicionales para radius
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.width = this.height = 2 * radius;
    }
}