import java.awt.*;

public abstract class Shape {
    protected int x, y, width, height;
    protected Color color;
    protected float strokeWidth = 2.0f;  // Grosor del borde
    protected String figureType, areaType;

    public Shape() {
    }  // Constructor sin argumentos para Gson

    public Shape(int x, int y, int width, int height, Color color, String figureType, String areaType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.figureType = figureType;
        this.areaType = areaType;
    }


    public abstract void draw(Graphics2D g2d);

    public abstract void drawPreview(Graphics2D g2d);

    public abstract double getArea();

    // Getters y setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

}