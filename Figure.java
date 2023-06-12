import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Figure extends ComplexDrawable {
    private Color fillColor;
    Figure(Color clr, ArrayList<Drawable> components) {
        super(clr, components);
        this.fillColor = Color.TRANSPARENT;
    }

    public Color getFillColor () { return this.fillColor; }
    public void setFillColor (Color clr) { this.fillColor = clr; }

    @Override
    public boolean collides(double x, double y) {
        if (this.fillColor == Color.TRANSPARENT) return super.borderCollides(x, y);
        else {
            // check if mouseClick is inside figure
            return false;
        }
    }
}
