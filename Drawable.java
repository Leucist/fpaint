import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Drawable {
    private static final Color SELECTION_COLOR = Color.AQUAMARINE;
    private Color color;
    private boolean isComplete = false;
    private boolean isSelected;

    public Drawable(Color clr) { this.color = clr; }

    /* getters */
    public Color getColor() { return this.color; }
    public boolean isComplete() { return this.isComplete; }
    public boolean isSelected() { return this.isSelected; }

    public static Color getSelectionColor() { return SELECTION_COLOR; }

    /* setters */
    public void setColor(Color clr) { this.color = clr; }
    public void setComplete() { this.isComplete = true; }
    public void setSelection(boolean b) { this.isSelected = b; }

    public abstract void draw(GraphicsContext g);
    public abstract boolean collides(double x, double y);
}
