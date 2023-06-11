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
    private boolean isAddingPoints = false;

    public void initialize() {
        /* Creating observable list for dynamic modifying layer list content */
        layerListItems = FXCollections.observableArrayList();
        layersList.setItems(layerListItems);

        /* Initialising new virtual canvas */
        vCanvas = new VCanvas(rCanvas);

        /* Initialising GraphicsContext of the canvas */
        GraphicsContext g = rCanvas.getGraphicsContext2D();
        /* Setting line width */
        g.setLineWidth(Line.getLineWidth());
        /* Adding listener to the mouse clicks on canvas */
        rCanvas.setOnMouseClicked(e -> handleClickOnCanvas(e, g));
    }


    @FXML
    private void addLayer() {                   /* adds new layer and creates+adds related layer list item */
        vCanvas.addLayer();
        layerListItems.add(0, "Layer" + vCanvas.getLastId());
        // updates selected layer
        selectedLayer = 0;
    }

    @FXML
    private void removeLayer() {                /* removes selected layer and related layer list item */
        // checks if there is at least 1 layer
        if (layerListItems.size() > 0) {
            vCanvas.removeLayer(selectedLayer);
            layerListItems.remove(selectedLayer);
            // updates selected layer
            selectedLayer = selectedLayer > 0 ? selectedLayer - 1 : 0;
            // updates graphics on the screen
            vCanvas.redrawAll();
        }
    }

    @FXML
    private void moveLayer(MouseEvent e) {
        if (layerListItems.size() > 1) {
            // used it in order to not multiply redundant similar code (united up/down methods)
            int direction = (e.getSource() == moveLayerUpButton) ? -1 : 1;
            // interrupts execution of the method if it could cause an error
            if ((direction == -1 && selectedLayer == 0) || ( direction == 1 && selectedLayer == layerListItems.size()-1)) return;

            // (1) removes selected layer('s label)
            // (2) inserts selected layer('s label)
            layerListItems.remove(selectedLayer);                                   /*(1)*/
            layerListItems.add(selectedLayer+direction, "Layer" + vCanvas.getLayers().get(selectedLayer).getId());  /*(2)*/

            Layer buffLayer = vCanvas.getLayers().get(selectedLayer);                 /* uses a buff var */
            vCanvas.removeLayer(selectedLayer);                                     /*(1)*/
            vCanvas.getLayers().add(selectedLayer+direction, buffLayer);        /*(2)*/

            vCanvas.redrawAll();
        }
    }

    @FXML
    private void handleListViewClick() {        /* updates active layer index and sets layer of that index as selected */
        selectedLayer = layersList.getSelectionModel().getSelectedIndex();
    }

    public void handleClickOnCanvas(MouseEvent e, GraphicsContext g) {
        double x = e.getX();
        double y = e.getY();
        if (moveTool.isSelected()) {
            vCanvas.redrawAll();
        }
        else if (penTool.isSelected()) {
            if (vCanvas.hasNoLayers()) return;
            vCanvas.getLayers().get(selectedLayer).addPoint(x, y, colorPicker.getValue());
            if (isAddingPoints) vCanvas.getLayers().get(selectedLayer).createLine();
            else isAddingPoints = true;
            vCanvas.redrawAll();
        }
    }

    public void completeDrawable() {
        isAddingPoints = false;
        vCanvas.getLayers().get(selectedLayer).completeDrawable();
    }

    public void selectNew() {

    }

    public void redrawCanvas() {
        // check all the drawables and draw them again. Is called when changes occur
    }
}
