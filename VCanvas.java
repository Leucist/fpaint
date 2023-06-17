import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/** Virtual canvas that contains layers*/
public class VCanvas {
    private final Canvas rCanvas;
    private final GraphicsContext g;
    private final ArrayList<Layer> layers;
    private int nextId = 1;
    private Drawable selectedObject = null;

    public VCanvas(Canvas rCanvas) {
        this.rCanvas = rCanvas;
        this.g = rCanvas.getGraphicsContext2D();
        this.layers = new ArrayList<>();
    }

    public void addLayer() { this.layers.add(0, new Layer(nextId++)); }
    public void removeLayer(int index) { this.layers.remove(index); }

    public ArrayList<Layer> getLayers() { return layers; }
    public int getLastId() { return this.nextId-1; }
    public boolean hasNoLayers() { return this.layers.size() == 0; }

    public void select(Drawable obj) {
        if (this.selectedObject != null) this.selectedObject.setSelection(false);
        this.selectedObject = obj;
        if (this.selectedObject != null) this.selectedObject.setSelection(true);
    }

    // check all the drawables and draw them again; Might be called when changes occur
    public void redrawAll() {
        this.g.clearRect(0, 0, rCanvas.getWidth(), rCanvas.getHeight());    /* clears all graphics on the canvas*/
        for (int i=layers.size()-1; i>=0; i--) {    /* reversed iteration here to save matching indexation in controls*/
            layers.get(i).redraw(g);
        }
    }
}
