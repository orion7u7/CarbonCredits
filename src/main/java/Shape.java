import java.awt.*;

public abstract class Shape {
    protected int x, y, width, height;
    protected Color color;

    public Shape(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public abstract void draw(Graphics g);
    public abstract double getArea();
}