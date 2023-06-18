import org.junit.jupiter.api.Assertions;            /* Test imports */
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;                  /* JavaFX imports */
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class FPaintTest {
    private final Canvas rCanvas = new Canvas();

    /** Point tests */
    @Test
    public void createPoint() {
        double expectedX, expectedY, actualX, actualY;
        Color expectedColor, actualColor;
        Point point = new Point(120, 25.7, Color.RED);
        // expected values
        expectedColor = Color.RED;
        expectedX = 120;
        expectedY = 25.7;
        // actual values
        actualColor = point.getColor();
        actualX = point.getX();
        actualY = point.getY();
        // comparing
        Assertions.assertEquals(expectedColor, actualColor);
        Assertions.assertEquals(expectedX, actualX);
        Assertions.assertEquals(expectedY, actualY);
    }

    @Test
    public void movePoint() {
        Color expectedColor, actualColor;
        // set expected values
        expectedColor = Color.BLACK;
        Point expected = new Point(31, -26);
        // actions
        Point result = new Point(10, 10, expectedColor);
        result.move(21, -36);
        actualColor = result.getColor();
        // test values
        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expectedColor, actualColor);
    }

    /** Line tests */
    @Test
    public void createLine() {
        // init
        Point pt1 = new Point(31, -26);
        Point pt2 = new Point(22, 0);
        Line line = new Line(pt1, pt2, Color.BLUE);
        // test
        Assertions.assertEquals(pt1, line.getPt1());
        Assertions.assertEquals(pt2, line.getPt2());
    }

    @Test
    public void moveLine() {
        // init
        Point pt1 = new Point(0, 0);
        Point pt2 = new Point(10, 10);
        Line line = new Line(pt1, pt2, Color.BLUE);
        line.move(25, -10);
        // test
        Assertions.assertEquals(25, line.getPt1().getX());
        Assertions.assertEquals(-10, line.getPt1().getY());
        Assertions.assertEquals(35, line.getPt2().getX());
        Assertions.assertEquals(0, line.getPt2().getY());
    }

    /** VCanvas tests */
    @Test
    public void createVCanvas() {
        VCanvas vCanvas = new VCanvas(rCanvas);

        Assertions.assertEquals(new ArrayList<Layer>(), vCanvas.getLayers());
        Assertions.assertEquals(0, vCanvas.getLastId());
    }

    @Test
    public void addLayer() {
        // init
        VCanvas vCanvas = new VCanvas(rCanvas);
        ArrayList<Layer> expectedLayers = new ArrayList<>();
        // actions
        expectedLayers.add(new Layer(1));
        vCanvas.addLayer();
        // test
        Assertions.assertEquals(expectedLayers.size(), vCanvas.getLayers().size());
        Assertions.assertEquals(expectedLayers.get(0).getId(), vCanvas.getLayers().get(0).getId());
        Assertions.assertEquals(1, vCanvas.getLastId());
    }

    @Test
    public void selectObject() {
        VCanvas vCanvas = new VCanvas(rCanvas);
        vCanvas.addLayer();
        vCanvas.getLayers().get(0).addPoint(10, 10, Color.GREEN);
        Point point = (Point) vCanvas.getLayers().get(0).getObjects().get(0);
        // test if not selected
        Assertions.assertFalse(point.isSelected());
        // action (selection)
        vCanvas.select(point);
        // test if selected
        Assertions.assertTrue(point.isSelected());
    }
}
