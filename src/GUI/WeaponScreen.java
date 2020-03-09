package GUI;

import ArchivesBuilder.SQLite;
import Equipment.*;
import Equipment.Weapons.Weapon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class WeaponScreen {

    private static EquipmentStore equipmentStore = new EquipmentStore(0,20,"All","All" ,EquipmentStore.size.All);

    public static void Run(BorderPane root){
        try {
            equipmentStore.fillWeapons();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        root.setLeft(left(root, equipmentStore.getWeaponsNames()));
        root.setRight(null);
        root.setCenter(center(root,null));
    }

    private static ScrollPane left(BorderPane root,String[] list){
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(5);
        box.setPrefWidth(200);
        for (String s : list) {
            Button btn = new Button(s);
            btn.setOnAction(actionEvent -> root.setCenter(center(root,s)));
            box.getChildren().add(btn);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(box);
        return scrollPane;
    }

    private static BorderPane center(BorderPane root,String name){
        BorderPane pane = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        Text levelTxt = SFText.create("Level Range: ");
        ComboBox<String> levelLow = new ComboBox<>();
        ComboBox<String> levelHigh = new ComboBox<>();
        ComboBox<String> type = new ComboBox<>();
        for (int i = 0; i < 21; i++) {
            levelLow.getItems().add(Integer.toString(i));
            levelHigh.getItems().add(Integer.toString(i));
        }
        type.getItems().addAll(Weapon.types);
        levelHigh.getSelectionModel().select(equipmentStore.getLevelHigh());
        levelLow.getSelectionModel().select(equipmentStore.getLevelLow());
        type.getSelectionModel().select(equipmentStore.getType());
        type.setOnAction(actionEvent -> {
            equipmentStore = new EquipmentStore(Integer.parseInt(levelLow.getValue()),Integer.parseInt(levelHigh.getValue()),type.getValue(),"Weapons", EquipmentStore.size.All);
            try {
                equipmentStore.fillWeapons();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            root.setLeft(left(root, equipmentStore.getWeaponsNames()));
        });
        levelLow.setOnAction(actionEvent -> {
            equipmentStore = new EquipmentStore(Integer.parseInt(levelLow.getValue()),Integer.parseInt(levelHigh.getValue()),type.getValue(),"Weapons", EquipmentStore.size.All);
            try {
                equipmentStore.fillWeapons();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            root.setLeft(left(root, equipmentStore.getWeaponsNames()));
        });
        levelHigh.setOnAction(actionEvent -> {
            equipmentStore = new EquipmentStore(Integer.parseInt(levelLow.getValue()),Integer.parseInt(levelHigh.getValue()),type.getValue(),"Weapons", EquipmentStore.size.All);
            try {
                equipmentStore.fillWeapons();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            root.setLeft(left(root, equipmentStore.getWeaponsNames()));
        });
        Text mid = SFText.create("-");
        Text typeTxt = SFText.create("Type: ");

        Text sortTxt = SFText.create("Sort: ");
        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().add("By Level");
        hBox.getChildren().addAll(typeTxt,type,levelTxt,levelLow,mid,levelHigh,sortTxt,sort);
        pane.setTop(hBox);
        Weapon weapon = new Weapon();
        equipmentStore.getEquipment(name,weapon,Weapon.tableName,Weapon.keys);
        if (weapon.getName()!=null){
            pane.setCenter(SFText.create(weapon.toString()));
        } else pane.setCenter(SFText.create("Weapons"));
        return pane;
    }
}
