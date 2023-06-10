import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FPaintController {
    private VCanvas vCanvas;                            /* virtual canvas; contains all layers with real canvases */
    @FXML
    private StackPane canvasContainer;
    @FXML
    private Canvas rCanvas;                             /* canvas for drawing; "points" to the active layer's canvas */
    @FXML
    private ListView<String> layersList;                /* menu list for selection of the active layer */
    private ObservableList<String> layerListItems;      /* list that's used to dynamically change content of the layer list */
    @FXML
    private ColorPicker colorPicker;                    /* standard color picker */
    @FXML
    private VBox toolBox;                               /* tool box; contains all of the tools */
    @FXML
    private ToggleGroup toggleGroup;                    /* abstract group to make layer menu/list scrollable */
    @FXML
    private RadioButton moveTool;                       /* tool used to select and move drawable objects */
    @FXML
    private RadioButton penTool;                        /* tool used to create points -> lines -> figures */
    @FXML
    private RadioButton rectTool;                       /* tool used to easily create rectangles */
    @FXML
    private RadioButton bucketTool;                     /* tool used to color entire object */
    @FXML
    private Button moveLayerUpButton;
    @FXML
    private Button moveLayerDownButton;
    private Drawable selectedObject;
    private int selectedLayer = 0;                      /*  */

    public void initialize() {
        /* Creating observable list for dynamic modifying layer list content */
        layerListItems = FXCollections.observableArrayList();
        layersList.setItems(layerListItems);

        /* Initialising new virtual canvas */
        vCanvas = new VCanvas();

        rCanvas = new Canvas(500, 500);
    }

    public void updateActiveLayer() {           /* sets canvas related to the active layer as rCanvas */
        if (!vCanvas.getLayers().isEmpty()) {
            System.out.println("Not empty! Layers: " + vCanvas.getLayers() + "; " + selectedLayer);

            rCanvas = vCanvas.getLayers().get(selectedLayer).getCanvas();
            System.out.println("rCanvas is related to the " + vCanvas.getLayers().get(selectedLayer));
            System.out.println("rCanvas itself: " + rCanvas);
            GraphicsContext g = rCanvas.getGraphicsContext2D();
            System.out.println("g: " + g + "\n");
            rCanvas.setOnMouseClicked(e -> handleClickOnCanvas(e, g));
        }
    }

    @FXML
    private void addLayer() {                   /* adds new layer and creates+adds related layer list item */
        vCanvas.addLayer();
        canvasContainer.getChildren().add(0, vCanvas.getLayers().get(0).getCanvas());
        layerListItems.add(0, "Layer" + vCanvas.getLastId());
        selectedLayer = 0;
//        selectedLayer = layerListItems.size() <= 1 ? 0 : ++selectedLayer;
        updateActiveLayer();
    }

    @FXML
    private void removeLayer() {                /* removes selected layer and related layer list item */
        // checks if there is at least 1 layer
        if (layerListItems.size() > 0) {
            canvasContainer.getChildren().remove(vCanvas.getLayers().get(selectedLayer).getCanvas());
            vCanvas.removeLayer(selectedLayer);
            layerListItems.remove(selectedLayer);
            selectedLayer = selectedLayer > 0 ? selectedLayer - 1 : 0;
            updateActiveLayer();
        }
    }

    @FXML
    private void moveLayer(MouseEvent e) {
        if (layerListItems.size() > 1 && selectedLayer != 0) {
            // used it in order to not multiply redundant similar code (united up/down methods)
            int direction = (e.getSource() == moveLayerUpButton) ? -1 : 1;
            // interrupts execution of the method if it could cause an error
            if ((direction == -1 && selectedLayer == 0) || ( direction == 1 && selectedLayer == layerListItems.size()-1)) return;

            // (0) initialises a buffer variable
            // (1) removes selected layer('s canvas/label)
            // (2) inserts selected layer('s canvas/label)
//            canvasContainer.getChildren().remove(selectedLayer); /*(1)*/
//            canvasContainer.getChildren().add(selectedLayer+direction, vCanvas.getLayers().get(selectedLayer).getCanvas()); /*(2)*/

            layerListItems.remove(selectedLayer);  /*(1)*/
            layerListItems.add(selectedLayer+direction, "Layer" + vCanvas.getLayers().get(selectedLayer).getId());  /*(2)*/

            Layer buffLayer = vCanvas.getLayers().get(selectedLayer);                 /* uses a buff var */
            vCanvas.getLayers().remove(selectedLayer);                              /*(1)*/
            vCanvas.getLayers().add(selectedLayer+direction, buffLayer);        /*(2)*/
        }
    }

    @FXML
    private void handleListViewClick() {        /* updates active layer index and sets layer of that index as selected */
        selectedLayer = layersList.getSelectionModel().getSelectedIndex();
        updateActiveLayer();
    }

    public void handleClickOnCanvas(MouseEvent e, GraphicsContext g) {
        double x = e.getX();
        double y = e.getY();
        if (moveTool.isSelected()) {
            System.out.println("Got this far!");
            vCanvas.redrawAll(g);
            // doesn't work
//            for (Drawable obj : vCanvas.getLayers().get(selectedLayer).getObjects()) {
//                if (e.getTarget() == obj) {
//                    g.fillRect(0, 0, 100, 100);
//                }
//            }
        }
        else if (penTool.isSelected()) {
            double size = Point.getSize();
            Point point = new Point(x - size/2, y - size/2, colorPicker.getValue());
            vCanvas.getLayers().get(selectedLayer).getObjects().add(point);
            point.draw(g);
        }
    }

    public void selectNew() {

    }

    public void redrawCanvas() {
        // check all the drawables and draw them again. Is called when changes occur
    }
}
