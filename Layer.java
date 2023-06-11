import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/** Imaginary layer that contains list of drawable objects */
public class Layer {
    private final ArrayList<Drawable> objects;
    private final int id;

    public Layer(int id) {
        this.objects = new ArrayList<>();
        this.id = id;
    }

    public int getId() { return this.id; }

    public void addObject(Drawable obj) { this.objects.add(obj); }
    public ArrayList<Drawable> getObjects() { return this.objects; }

    public boolean isEmpty() { return this.objects.size() == 0; }

    public void addPoint(double x, double y, Color clr) {
        double size = Point.getSize();
        this.objects.add(new Point(x - size/2, y - size/2, clr));
    }

    public void addLine(Point pt1, Point pt2, Color clr) {
        this.objects.add(new Line(pt1, pt2, clr));
    }

    public void createLine() {
        int size = this.objects.size();
        Drawable lastObj = this.objects.get(size-1);
        if (lastObj.getClass() == Point.class) {
            Drawable preLastObj = this.objects.get(size-2);
            if (preLastObj.getClass() == Point.class) {
                this.addLine((Point) preLastObj, (Point) lastObj, preLastObj.getColor());
                this.objects.remove(preLastObj);    /* removes redundant point, that is now part of the line */
            }
            else if (preLastObj.getClass() == Line.class) {
                this.addLine(((Line) preLastObj).getPt2(), (Point) lastObj, preLastObj.getColor());
            }
            this.objects.remove(lastObj);           /* removes redundant point, that is now part of the line */
        }
        else throw new IncompatibleElementException("Last object in layer is " + lastObj.getClass() + ". Must be Point.");
    }

//    public void createChain(ArrayList<Drawable> newDrawable) { this.objects.add(new Chain(newDrawable)); }
//    public void createFigure(ArrayList<Drawable> newDrawable) {}

    public ArrayList<Drawable> collectDrawableComponents() {
        ArrayList<Drawable> newDrawable = new ArrayList<>();
        if (!this.isEmpty()) {          /* in case layer is empty */
            for (int i = this.objects.size() - 1; i >= 0 && !this.objects.get(i).isComplete(); i--) {
                newDrawable.add(0, this.objects.get(i));
            }
        }
        return newDrawable;
    }

    public void cancelCreatingDrawable(ArrayList<Drawable> components) {
        for (Drawable obj : components) {
            this.objects.remove(obj);
        }
    }

    public void completeDrawable() {
        if (this.isEmpty()) return;
        ArrayList<Drawable> newDrawable = collectDrawableComponents();  /* collects pieces of a new drawable */
        if (newDrawable.size() == 1) newDrawable.get(0).setComplete();
        else {
            Drawable firstPiece = newDrawable.get(0);
            Drawable lastPiece = newDrawable.get(newDrawable.size() - 1);
            // throws exception if last piece is Point
            if (firstPiece.getClass() == Point.class)
                throw new IncompatibleElementException("First element of a polygonal drawable can not be Point.");
            if (lastPiece.getClass() == Point.class)
                throw new IncompatibleElementException("Last element of a polygonal drawable can not be Point.");
            // redirects drawable to one of two constructors;
            // ..if last and first points of drawable are the same - it's a figure, else - chain
            if (((Line)firstPiece).getPt1() == ((Line)lastPiece).getPt2())
                this.objects.add(new Chain(firstPiece.getColor(), newDrawable));
            else
                this.objects.add(new Figure(firstPiece.getColor(), newDrawable));
            // sets the new drawable as complete
            this.objects.get(this.objects.size()-1).setComplete();
            // clears particles of a newly created drawable
            cancelCreatingDrawable(newDrawable);
        }

    }

    public void redraw(GraphicsContext g) {
        // redraws its content on the canvas
        for (Drawable obj : this.objects) {
            obj.draw(g);
        }
    };
}
