package GUI;

import Equipment.Computer.MicroCenter;
import javafx.scene.layout.BorderPane;

public class ComputerScreen {

    private static MicroCenter microCenter;
    private static BorderPane root;

    public static void Run(BorderPane inRoot){
        root = inRoot;
        microCenter = new MicroCenter();

    }


}
