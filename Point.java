import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point extends Drawable {
    private double x, y;
    private static final double size = 6.;
    private Color color;

    /* constructors */
    public Point(Color clr){
        this.x = 0;
        this.y = 0;
        this.color = clr;
    }
    public Point(double x, double y, Color clr){
        this.x = x;
        this.y = y;
        this.color = clr;
    }
    public Point(Point pt){
        this.x = pt.x;
        this.y = pt.y;
        this.color = pt.color;
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public static double getSize() { return size; }
//    public Color getColor() { return this.color; }
//    public void setColor(Color clr) { this.color = clr; }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(this.color);
        g.fillRect(this.x, this.y, size, size);
    }
}
