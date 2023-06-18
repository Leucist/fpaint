import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/** Main class that runs the application */
 public class FancyPaintApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FancyPaint.fxml"));
//        loader.setController(this);

        Pane root = loader.load();  /* requires exception handling */

        stage.setTitle("Fancy Paint");
        stage.getIcons().add(new Image("source/icon.png"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}