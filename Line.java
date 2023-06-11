import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Drawable {
    private final Point pt1, pt2;
    private static final double lineWidth = 2.0;

    /* constructors */
    public Line(Point pt1, Point pt2, Color clr) {
        super(clr);
        this.pt1 = new Point(pt1);
        this.pt2 = new Point(pt2);
    }
//    public Line(Line ln){
//        this.pt1 = ln.pt1;
//        this.pt2 = ln.pt2;
//    }

    public void move(int x, int y) {
        this.pt1.move(x, y);
        this.pt2.move(x, y);
    }

    /* Point getters */
    public Point getPt1() { return this.pt1; }
    public Point getPt2() { return this.pt2; }
    public static double getLineWidth() { return lineWidth; }

    @Override
    public void draw(GraphicsContext g) {
        // draws two points
        pt1.draw(g);
        pt2.draw(g);
        // draws line itself
        g.setStroke(this.getColor());
        g.strokeLine(this.pt1.getX(), this.pt1.getY(), this.pt2.getX(), this.pt2.getY());
    }
}
