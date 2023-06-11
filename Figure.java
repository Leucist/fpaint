import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Figure extends Drawable {
    private final ArrayList<Drawable> components;
    Figure(Color clr, ArrayList<Drawable> components) {
        super(clr);
        this.components = components;
        setColor(clr);
    }

    @Override
    public void setColor(Color clr) {
        super.setColor(clr);
        for (Drawable component : this.components) {
            component.setColor(clr);
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        for (Drawable component : this.components) {
            component.draw(g);
        }
    }
}
