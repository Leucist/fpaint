import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Drawable {
    private Color color;
    public abstract void draw(GraphicsContext g);
}
