import java.awt.*;

public class Ellipse extends Shape {
    public Ellipse(int x, int y, int width, int height, Color color) {
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

