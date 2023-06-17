import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

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
    public void setColor(Color clr) {
        super.setColor(clr);
        this.fillColor = clr;
    }

    @Override
    public void draw(GraphicsContext g) {
        super.draw(g);

        if (this.fillColor != Color.TRANSPARENT) {
            ArrayList<Drawable> components = super.getComponents();
            int size = components.size();  // counts points of all the components, which are Lines
            double[] xPoints = new double[size+1];  /* +1 to add both first and last points */
            double[] yPoints = new double[size+1];
            for (int i = 0; i <= size-1; i++) {
                Point point = ((Line) components.get(i)).getPt1();
                xPoints[i] = point.getX();
                yPoints[i] = point.getY();
            }
            // adding coords of the first point in the end cause figure is closed
            Point point = ((Line) components.get(0)).getPt1();
            xPoints[size] = point.getX();
            yPoints[size] = point.getY();

            g.fillPolygon(xPoints, yPoints, size+1);
        }
    }

    @Override
    public boolean collides(double x, double y) {
        if (this.fillColor == Color.TRANSPARENT) return super.borderCollides(x, y);
        else {
            // checks if mouseClick is inside figure
            int borderCollisions = 0;
            for (double rayY = y; rayY > 0; rayY--) {
                for (Drawable line : super.getComponents()) {
                    if (line.collides(x, rayY)) borderCollisions++;
                }
            }
            return (borderCollisions % 2 == 1);
        }
    }
}
