import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/** Imaginary layer that is related to the JavaFX Canvas
 * and contains list of drawable objects */
public class Layer {
    private final Canvas canvas;
    private final GraphicsContext g;
    private final ArrayList<Drawable> objects;
    private final int id;

    public Layer(int id) {
        this.canvas = new Canvas(500, 500);
        this.g = this.canvas.getGraphicsContext2D();
        this.objects = new ArrayList<>();
        this.id = id;
    }

    public Canvas getCanvas() { return this.canvas; }
    public int getId() { return this.id; }

    public void addObject(Drawable obj) { this.objects.add(obj); }
    public ArrayList<Drawable> getObjects() { return this.objects; }

    public void redraw(GraphicsContext gc) {
        // clears all graphics on the canvas
//        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        // redraws its content on the canvas
        for (Drawable obj : this.objects) {
            obj.draw(gc);
        }
    };
}
