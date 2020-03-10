package GUI;

import Equipment.Armor.Armor;
import Equipment.Armor.ArmorUpgrade;
import Equipment.Armor.Shields;
import Equipment.Equipment;
import Equipment.EquipmentStore;
import Equipment.Weapons.Ammo;
import Equipment.Weapons.SolarianCrystal;
import Equipment.Weapons.Weapon;
import Equipment.Weapons.WeaponAccessory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * JavaFX Class that displays the Equipment stored in Archives database
 */
public class EquipmentStoreScreen {

    /**Main EquipmentStore object that stores the equipment objects**/
    private static EquipmentStore equipmentStore = new EquipmentStore(0,20,"All","All", EquipmentStore.size.All);
    private static final String[] eqTypes = {"All","Weapons","Armor","Armor Upgrades","Ammo","Solarian Crystals","Weapon Accessories","Shields"};

    /**
     * Displays the UI screen for the EquipmentStore
     * @param root main UI pane
     */
    static void Display(BorderPane root){
        try {
            equipmentStore.fillWeapons();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        root.setLeft(left(root, equipmentStore.getNames()));
        root.setRight(null);
        root.setCenter(center(root,null));
    }

    /**
     * Shows the list of equipment on the left side of screen
     * @param root main UI pane
     * @param list list of equipment to be displayed
     * @return Scroll Pane to be displayed
     */
    private static ScrollPane left(BorderPane root, ArrayList<String> list){
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

    /**
     * Displays the center search bar and Equipment information
     * @param root Main UI pane
     * @param name Equipment name to be displayed
     * @return BorderPane to be displayed
     */
    private static BorderPane center(BorderPane root,String name){
        BorderPane pane = new BorderPane();
        //search box
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        //hBox comboBoxes
        //eqBox
        ComboBox<String> eqType= new ComboBox<>();
        eqType.getItems().addAll(eqTypes);
        eqType.getSelectionModel().select(equipmentStore.getEqType());
        //typeBox
        Text typeTxt = SFText.create("Type: ");
        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll(typeUpdate(eqType.getValue()));
        type.getSelectionModel().select(equipmentStore.getType());
        //levelLow and High box
        Text levelTxt = SFText.create("Level Range: ");
        ComboBox<String> levelLow = new ComboBox<>();
        Text mid = SFText.create("-");
        ComboBox<String> levelHigh = new ComboBox<>();
        for (int i = 0; i < 21; i++) {
            levelLow.getItems().add(Integer.toString(i));
            levelHigh.getItems().add(Integer.toString(i));
        }
        levelHigh.getSelectionModel().select(equipmentStore.getLevelHigh());
        levelLow.getSelectionModel().select(equipmentStore.getLevelLow());
        //SortBox
        Text sortTxt = SFText.create("Sort: ");
        ComboBox<String> sort = new ComboBox<>();
        sort.getItems().addAll(equipmentStore.getSortTypes());
        sort.getSelectionModel().select(equipmentStore.getSort());
        //Update action
        levelLow.setOnAction(actionEvent -> Update(levelLow.getValue(),levelHigh.getValue(),type.getValue(),eqType.getValue(), EquipmentStore.size.All,root));
        levelHigh.setOnAction(actionEvent -> Update(levelLow.getValue(),levelHigh.getValue(),type.getValue(),eqType.getValue(), EquipmentStore.size.All,root));
        eqType.setOnAction(actionEvent -> Update(levelLow.getValue(),levelHigh.getValue(),"All",eqType.getValue(), EquipmentStore.size.All,root));
        type.setOnAction(actionEvent -> Update(levelLow.getValue(),levelHigh.getValue(),type.getValue(),eqType.getValue(), EquipmentStore.size.All,root));
        //fill hBox and set to pane
        hBox.getChildren().addAll(eqType,typeTxt,type,levelTxt,levelLow,mid,levelHigh,sortTxt,sort);
        pane.setTop(hBox);
        //Equipment information
        Equipment equipment = newEquipment(eqType.getValue());
        equipmentStore.getEquipment(name,equipment,equipment.getTableName(),equipment.getKeys());
        if (equipment.getName()!=null){
            pane.setCenter(SFText.create(equipment.toString()));
        } else pane.setCenter(SFText.create(eqType.getValue()));

        return pane;
    }

    /**
     * Uses the equipment type to fill the main table
     * @param eqType eqType ComboBox value
     * @throws SQLException
     */
    private static void equipmentStoreTableFill(String eqType) throws SQLException {
        switch (eqType){
            case "Weapons":
                equipmentStore.fillWeapons();
                break;
            case "Armor":
                equipmentStore.fillArmor();
                break;
            case "Ammo":
                equipmentStore.fillAmmo();
                break;
            case "Solarian Crystals":
                equipmentStore.fillCrystals();
                break;
            case "Weapon Accessories":
                equipmentStore.fillAccessories();
                break;
            case "Shields":
                equipmentStore.fillShields();
                break;
            case "Armor Upgrades":
                equipmentStore.fillArmorUps();
            default:
                equipmentStore.fillAmmo();
                equipmentStore.fillArmor();
                equipmentStore.fillWeapons();
                equipmentStore.fillCrystals();
                equipmentStore.fillAccessories();
                equipmentStore.fillShields();
                equipmentStore.fillArmorUps();
                break;
        }
    }

    /**
     * Updates The types to be used based on the eqComboBox
     * @param eqType value from combo box
     * @return String[] of types
     */
    private static String[] typeUpdate(String eqType){
        switch (eqType){
            case "Weapons":
                return Weapon.types;
            case "Armor":
                return Armor.types;
            case "Ammo":
                return Ammo.types;
            case "Solarian Crystals":
                return SolarianCrystal.types;
            case "Weapon Accessories":
                return WeaponAccessory.types;
            case "Shields":
                return Shields.types;
            case "Armor Upgrades":
                return ArmorUpgrade.types;
            default:
                String[] types = new String[Weapon.types.length+Ammo.types.length+Armor.getTypes().length];
                int i=0;
                for (String s:Weapon.types) {
                    types[i] = s;
                    i++;
                }
                for (String s: Armor.types) {
                    types[i] = s;
                    i++;
                }
                for (String s:Ammo.types) {
                    types[i] = s;
                    i++;
                }
                return types;
        }
    }

    /**
     * Updates the equipmentStore with the changed values of the combo box
     * @param levelLow comboBox value
     * @param levelHigh comboBox value
     * @param type comboBox value
     * @param eqType comboBox value
     * @param size store size
     * @param root main UI Pane
     */
    private static void Update(String levelLow,String levelHigh,String type,String eqType, EquipmentStore.size size,BorderPane root){
        equipmentStore = new EquipmentStore(Integer.parseInt(levelLow),Integer.parseInt(levelHigh),type,eqType, size);
        try {
            equipmentStoreTableFill(eqType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        root.setLeft(left(root, equipmentStore.getNames()));
        root.setCenter(center(root,null));
    }

    /**
     * returns the object the corresponds to the eqType
     * @param eqType comboBox value
     * @return Equipment object
     */
    private static Equipment newEquipment(String eqType){
        switch (eqType){
            case "Armor":
                return new Armor();
            case "Ammo":
                return new Ammo();
            case "Solarian Crystals":
                return new SolarianCrystal();
            case"Weapon Accessories":
                return new WeaponAccessory();
            case "Shields":
                return new Shields();
            case "Armor Upgrades":
                return new ArmorUpgrade();
            default:
                return new Weapon();
        }
    }
}
