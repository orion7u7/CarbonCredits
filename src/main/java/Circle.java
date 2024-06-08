import java.awt.*;

public class Circle extends Shape{

    public Circle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    @Override
    public double getArea() {
        return Math.PI * width * height / 4;
    }
}
