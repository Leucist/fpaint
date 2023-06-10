import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/** Virtual canvas that contains layers*/
public class VCanvas {
    private final ArrayList<Layer> layers;
    private int nextId = 1;

    public VCanvas() { this.layers = new ArrayList<>(); }

    public void addLayer() { this.layers.add(0, new Layer(nextId++)); }
    public void removeLayer(int index) { this.layers.remove(index); }

    public ArrayList<Layer> getLayers() { return layers; }
    public int getLastId() { return this.nextId-1; }

    public void redrawAll(GraphicsContext gc) {
        gc.clearRect(0, 0, 500, 500);
        for (int i=layers.size()-1; i>=0; i--) {    /* reversed iteration here to save matching indexation in controls*/
            layers.get(i).redraw(gc);
        }
    }
}
