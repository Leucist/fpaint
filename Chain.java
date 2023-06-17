import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Chain extends ComplexDrawable {
    Chain(Color clr, ArrayList<Drawable> components) {
        super(clr, components);
    }

    @Override
    public boolean collides(double x, double y) {
        return super.borderCollides(x, y);
    }
}
