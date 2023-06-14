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

    private ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (Drawable obj : super.getComponents()) {
            Line line = (Line) obj;
            points.add(line.getPt1());
            points.add(line.getPt2());
        }
        return points;
    }

    private ArrayList<Double> getPointsCoords() {
        ArrayList<Double> pointsCoords = new ArrayList<>();
        for (Drawable obj : super.getComponents()) {
            Line line = (Line) obj;
            pointsCoords.add(line.getPt1().getX());
            pointsCoords.add(line.getPt1().getY());
        }
        return pointsCoords;
    }

    @Override
    public Node getNode() {
        ArrayList<Double> pointsCoords = this.getPointsCoords();
        if (this.fillColor == Color.TRANSPARENT) {              /* builds polyline from a figure border */
            Polyline polyline = new Polyline();
            polyline.getPoints().addAll(pointsCoords);
            return polyline;
        }
        else {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(pointsCoords);
            return polygon;
        }
    }

    @Override
    public boolean collides(double x, double y) {
        if (this.fillColor == Color.TRANSPARENT) return super.borderCollides(x, y);
        else {
            // DOESN'T WORK YET

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
