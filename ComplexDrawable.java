import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/** Abstract class for more complex drawable objects as figures or chains */
public abstract class ComplexDrawable extends Drawable {
    private final ArrayList<Drawable> components;
    ComplexDrawable(Color clr, ArrayList<Drawable> components) {
        super(clr);
        this.components = components;
        setColor(clr);
    }

    public ArrayList<Drawable> getComponents() { return this.components; }

    @Override
    public void setColor(Color clr) {
        super.setColor(clr);
        for (Drawable component : this.components) {
            component.setColor(clr);
        }
    }

    @Override
    public void setSelection(boolean b) {
        super.setSelection(b);
        for (Drawable component : this.components) {
            component.setSelection(b);
        }
    }

    @Override
    public void draw(GraphicsContext g) {
        for (Drawable component : this.components) {
            component.draw(g);
        }
    }

    @Override
    public void move(double diffX, double diffY) {
        for (Drawable obj : this.components) {
            obj.move(diffX, diffY);
        }
    }

    public boolean borderCollides(double x, double y) {
        for (Drawable component : this.components) {
            if (component.collides(x, y)) return true;
        }
        return false;
    }
}
