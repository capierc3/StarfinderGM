import GUI.SFText;
import GUI.TopBar;
import WorldBuilder.Diagrams;
import WorldBuilder.GalaxyDataBase;
import WorldBuilder.Sector;
import WorldBuilder.StarSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("file:images/icon.jpg"));
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1500, 800);
        //Center//
        Text txt = SFText.create("Starfinder 1.0",50);
        Diagrams.drawLocalMap(GalaxyDataBase.findSystem("Su"),root);
        //Top Bar//
        TopBar bar = new TopBar(root);
        root.setTop(bar.getBar());
        root.setStyle("-fx-background-color: #0d3849;");
        root.getStylesheets().add(getClass().getResource("Styles.css").toString());



        primaryStage.setScene(scene);
        primaryStage.setTitle("StarFinder");
        primaryStage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }

}
