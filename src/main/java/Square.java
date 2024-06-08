import java.awt.*;

public class Square extends Shape {
    public Square() {}  // Constructor sin argumentos para Gson

    public Square(int x, int y, int size, Color color, String figureType, String areaType) {
        super(x, y, size, size, color, figureType, areaType);
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
        return width * width;  // o height * height, ya que son iguales
    }
}