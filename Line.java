import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Line class implementation
 *  Simple drawable object that is build from points */
public class Line extends Drawable {
    private final Point pt1, pt2;
    private static final double LINE_WIDTH = 2.0;

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

    /* getters */
    public Point getPt1() { return this.pt1; }
    public Point getPt2() { return this.pt2; }
    public static double getLineWidth() { return LINE_WIDTH; }

    @Override
    public void setSelection(boolean b) {
        super.setSelection(b);
        pt1.setSelection(b);
        pt2.setSelection(b);
    }

    @Override
    public void setColor(Color clr) {
        super.setColor(clr);
        pt1.setColor(clr);
        pt2.setColor(clr);
    }

    @Override
    public void draw(GraphicsContext g) {
        // draws two points
        pt1.draw(g);
        pt2.draw(g);
        if (this.isSelected()) {
            // draws line of SELECTION_COLOR and +2px in size under the line
            g.setLineWidth(LINE_WIDTH + 4);
            g.setStroke(Drawable.getSelectionColor());
            g.strokeLine(this.pt1.getX(), this.pt1.getY(), this.pt2.getX(), this.pt2.getY());
            g.setLineWidth(LINE_WIDTH);
        }
        // draws line itself
        g.setStroke(this.getColor());
        g.strokeLine(this.pt1.getX(), this.pt1.getY(), this.pt2.getX(), this.pt2.getY());
    }

    @Override
    public void move(double diffX, double diffY) {
        this.pt1.move(diffX, diffY);
        this.pt2.move(diffX, diffY);
    }

    @Override
    public boolean collides(double x, double y) {
        if (pt1.collides(x, y)) return true;
        if (pt2.collides(x, y)) return true;

        // calculates the length of the line (hypotenuse) using the Pythagorean theorem
        double l = Math.sqrt(Math.pow(pt1.getX() - pt2.getX(), 2) + Math.pow(pt1.getY() - pt2.getY(), 2));
        double l1 = Math.sqrt(Math.pow(pt1.getX() - x, 2) + Math.pow(pt1.getY() - y, 2)); /*between user click and pt1*/
        double l2 = Math.sqrt(Math.pow(pt2.getX() - x, 2) + Math.pow(pt2.getY() - y, 2)); /*between user click and pt2*/

//        return (Math.abs(l - (l1 + l2)) <= LINE_WIDTH);
        return (Math.abs(l - (l1 + l2)) <= 0.1);
    }
}
