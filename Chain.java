import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

public class Chain extends ComplexDrawable {
    Chain(Color clr, ArrayList<Drawable> components) {
        super(clr, components);
    }

    @Override
    public Node getNode() {
        Polyline polyline = new Polyline();
        ArrayList<Drawable> components = super.getComponents();
        for (int i = 0; i < components.size()-1; i++) {
            Line line = (Line) components.get(i);
            polyline.getPoints().add(line.getPt1().getX());
            polyline.getPoints().add(line.getPt1().getY());
            if (i == components.size()-1) {             /* adds second point if it's the last line */
                polyline.getPoints().add(line.getPt2().getX());
                polyline.getPoints().add(line.getPt2().getY());
            }
        }
        return polyline;
    }

    @Override
    public boolean collides(double x, double y) {
        return super.borderCollides(x, y);
    }
}
