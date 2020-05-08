package GUI;

import ships.Makes;
import ships.ShipYard;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MakesScreen {

    private static BorderPane root;
    private static ShipYard shipYard;

    public static void run(BorderPane pane){
        root = pane;
        shipYard = new ShipYard();
        setLeft();
        root.setCenter(SFText.create("Click Make on the right"));
    }

    private static void setLeft(){
        ScrollPane pane = new ScrollPane();
        pane.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 5");
        VBox box = new VBox();
        for (Makes make: shipYard.getMakes()) {
            Button btn = new Button(make.getName());
            btn.setOnAction(actionEvent -> {
                setCenter(make);
            });
            box.getChildren().add(btn);
        }
        Button btn = new Button("New");
        btn.setOnAction(actionEvent -> {
            setCenter(null);
        });
        box.getChildren().add(btn);
        pane.setContent(box);
        root.setLeft(pane);
    }
    private static void setCenter(Makes make){
        VBox box = new VBox();
        box.setPadding(new Insets(5));
        if (make == null){
            HBox name = new HBox();
            TextField nameIn = new TextField();
            name.getChildren().addAll(SFText.create("Name:"),nameIn);
            Text desc = SFText.create("Description:");
            TextArea descArea = new TextArea();
            box.getChildren().addAll(name,nameIn,desc,descArea);
        } else {
            box.getChildren().addAll(SFText.create("Name: "+make.getName()),
                    SFText.create(make.getDescription()),
                    SFText.create("\n"+"Bonus: "+make.getBonus())
            );
//            for (ShipFrame frame: make.getFrames()) {
//                box.getChildren().add(SFText.create(frame.getName()));
//            }
        }
        root.setCenter(box);
    }
}
