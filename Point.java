import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Point extends Drawable {
    private double x, y;
    private static final double SIZE = 6.;

    /* constructors */
    public Point(double x, double y){
        super(Color.BLACK);
        this.x = x;
        this.y = y;
    }
    public Point(double x, double y, Color clr){
        super(clr);
        this.x = x;
        this.y = y;
    }
    public Point(Point pt){
        super(pt.getColor());
        this.x = pt.x;
        this.y = pt.y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public static double getSize() { return SIZE; }

    @Override
    public Node getNode() {
        return new Rectangle(this.x, this.y, SIZE, SIZE);
    }

    @Override
    public void draw(GraphicsContext g) {
        if (this.isSelected()) {
            // draws square of SELECTION_COLOR and +2px in size under the point
            g.setFill(Drawable.getSelectionColor());
            g.fillRect(this.x-2, this.y-2, SIZE+4, SIZE+4);
        }
        // draws point itself
        g.setFill(this.getColor());
        g.fillRect(this.x, this.y, SIZE, SIZE);
    }

    @Override
    public void move(double diffX, double diffY) {
        this.x += diffX;
        this.y += diffY;
    }

    @Override
    public boolean collides(double x, double y) {
        for (int i = 0; i< SIZE; i++) {
            for (int j = 0; j< SIZE; j++) {
                if (this.x+i == x && this.y+j == y) return true;
            }
        }
        return false;
    }
}
