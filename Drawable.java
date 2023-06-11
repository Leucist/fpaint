import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Drawable {
    private Color color;
    private boolean isComplete = false;

    public Color getColor() { return this.color; }
    public void setColor(Color clr) { this.color = clr; }
    public boolean isComplete() { return this.isComplete; }
    public void setComplete() { this.isComplete = true; }

    public abstract void draw(GraphicsContext g);
}
