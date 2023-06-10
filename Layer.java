import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/** Imaginary layer that contains list of drawable objects */
public class Layer {
    private final ArrayList<Drawable> objects;
    private final int id;

    public Layer(int id) {
        this.objects = new ArrayList<>();
        this.id = id;
    }

    public int getId() { return this.id; }

    public void addObject(Drawable obj) { this.objects.add(obj); }
    public ArrayList<Drawable> getObjects() { return this.objects; }

    public void addPoint(double x, double y, Color clr) {
        double size = Point.getSize();
        this.objects.add(new Point(x - size/2, y - size/2, clr));
    }

    public void redraw(GraphicsContext g) {
        // redraws its content on the canvas
        for (Drawable obj : this.objects) {
            obj.draw(g);
        }
    };
}
