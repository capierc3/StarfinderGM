package GUI;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class TopBar {

    private final MenuBar bar;
    private final BorderPane root;

    public TopBar(BorderPane root){
        bar = new MenuBar();
        this.root = root;
        fileMenu(root);
        diceMenu();
    }
    private void fileMenu(BorderPane root){
        //npc
        Menu npc = new Menu("NPC");
        MenuItem newBasic = new MenuItem("New Basic NPC");
        newBasic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.setCenter(new Text("New Basic NPC"));
            }
        });
        MenuItem newHero = new MenuItem("New Hero NPC");
        MenuItem viewNpc = new MenuItem("View NPCs");
        npc.getItems().addAll(newBasic,newHero,viewNpc);
        //ship
        Menu ship = new Menu("Ship");
        MenuItem newShip = new MenuItem("New Ship");
        newShip.setOnAction(actionEvent -> {
            System.out.println("Currently off");
            //ShipScreen.RunNewShip(root);
        });
        MenuItem viewShips = new MenuItem("View Ships");
        MenuItem makes = new MenuItem("Manufacturers");
        makes.setOnAction(actionEvent -> {
            MakesScreen.run(root);
        });
        ship.getItems().addAll(newShip,viewShips,makes);
        //equipment
        Menu equipment = new Menu("Equipment");
        MenuItem shop = new MenuItem("Shop");
        shop.setOnAction(actionEvent -> EquipmentStoreScreen.Display(root));
        equipment.getItems().addAll(shop);
        //Computer
        Menu computer = new Menu("Computer");
        MenuItem newComp = new MenuItem("Build Computer");
        newComp.setOnAction(actionEvent -> {
            ComputerScreen.Run(root);
        });
        //Main bar
        Menu file = new Menu("File");
        file.getItems().addAll(npc,ship,equipment,computer);
        bar.getMenus().add(file);
    }
    private void diceMenu(){
        Menu dice = new Menu("Dice");
        MenuItem d4 = new MenuItem("d4");
        MenuItem d6 = new MenuItem("d6");
        MenuItem d8 = new MenuItem("d8");
        MenuItem d10 = new MenuItem("d10");
        MenuItem d12 = new MenuItem("d12");
        MenuItem d20 = new MenuItem("d20");
        MenuItem dPercent = new MenuItem("d%");
        dice.getItems().addAll(d4,d6,d8,d10,d12,d20,dPercent);
        bar.getMenus().add(dice);
    }

    public MenuBar getBar() {
        return bar;
    }
}
