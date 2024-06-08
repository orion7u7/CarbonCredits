import java.awt.*;

public class Ellipse extends Shape {
    public Ellipse() {}  // Constructor sin argumentos para Gson

    public Ellipse(int x, int y, int width, int height, Color color, String figureType, String areaType) {
        super(x, y, width, height, color, figureType, areaType);
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
        return Math.PI * width * height / 4;
    }
}