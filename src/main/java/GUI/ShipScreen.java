package GUI;

import ships.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.Collection;

public class ShipScreen {

    private static StarShip ship;
    private static ShipYard shipYard;
    private static BorderPane root;
    private static Text info;
    private static int sel1;
    private static int sel2;

    //New Ship Entry Spot//
    public static void RunNewShip(BorderPane pane){
        RunNewShip(pane, 0, 0);
    }
    public static void RunNewShip(BorderPane pane,int a,int b) {
        root = pane;
        shipYard = new ShipYard();
        ship = null;
        sel1 = a;
        sel2 = b;

        HBox tierBox = new HBox();
        tierBox.setAlignment(Pos.CENTER);
        Text tierText = SFText.create("Tier: ");
        ComboBox<ShipTier> tierComboBox = new ComboBox<>();
        tierComboBox.getItems().addAll(ShipTier.getTiers());
        tierComboBox.getSelectionModel().select(sel1);
        tierBox.getChildren().addAll(tierText,tierComboBox);
        HBox frameBox = new HBox();
        frameBox.setAlignment(Pos.CENTER);
        Text frameText = SFText.create("Frame: ");
        ComboBox<String> frameComboBox = new ComboBox<>();
        frameComboBox.getItems().addAll(ShipYard.getNames(ShipYard.ShipTables.baseframes.toString()));
        frameComboBox.getSelectionModel().select(sel2);
        ShipFrame shipFrame = (ShipFrame) ShipYard.getParts(new ShipFrame())
                .get(frameComboBox.getSelectionModel().getSelectedIndex());
        info = SFText.create("Build Points: "+tierComboBox.getValue().getBuildPoints()
                +"\n"+ shipFrame.getInfo());
        info.setWrappingWidth(300);
        tierComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, tier, t1) -> {
            sel1 = tierComboBox.getSelectionModel().getSelectedIndex();
            RunNewShip(pane,sel1,sel2);

        });
        frameComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, frame, t1) -> {
            sel2 = frameComboBox.getSelectionModel().getSelectedIndex();
            RunNewShip(pane,sel1,sel2);
        });
        frameBox.getChildren().addAll(frameText,frameComboBox);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Button next = new Button("Next");
        next.setOnAction(actionEvent -> {
            ship = new StarShip();
            ship.setTier(tierComboBox.getValue());
            ship.setFrame(shipFrame);
            shipYard.newBuild(ship);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(powerCore());
        });
        vBox.getChildren().addAll(tierBox,frameBox,next,info);
        pane.setCenter(vBox);
        currentShip(ship);
    }

    //Center part picker//
    private static Node powerCore(){
        ComboBox<Part> powerCoreComboBox = new ComboBox<>();
        powerCoreComboBox.getItems().addAll(ShipYard.getParts(new PowerCore()));
        Button next = new Button("Next");
        next.setOnAction(actionEvent -> {
            PowerCore powerCore = (PowerCore) powerCoreComboBox.getValue();
            if (ship.getPowerCore()!=null){
                shipYard.returnPart(ship.getPowerCore());
            }
            ship.setPowerCore(powerCore);
            shipYard.buyPart(powerCore);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(armor());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Pick your Power Core."),powerCoreComboBox,next);
        return vBox;
    }
    private static Node armor() {
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().add(new Armor());
        box.getItems().addAll(ShipYard.getParts(new Armor()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getArmor()!=null) {
                shipYard.returnPart(ship.getArmor());
            }
            Armor armor = (Armor) box.getValue();
            ship.setArmor(armor);
            shipYard.buyPart(armor);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(crew());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Pick Your Ship's Armor"),box,btn);
        return vBox;
    }
    private static Node crew(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new CrewQuarter()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getCrewQuarter()!=null){
                shipYard.returnPart(ship.getCrewQuarter());
            }
            CrewQuarter part = (CrewQuarter) box.getValue();
            ship.setCrewQuarter(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(dcm());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select a Crew Quarter"),box,btn);
        return vBox;
    }
    private static Node dcm(){
        ComboBox<Part> box = new ComboBox<>();
        //TODO NULL ITEM
        //box.getItems().add(new CounterMeasures(0));
        box.getItems().addAll(ShipYard.getParts(new CounterMeasures()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getCounterMeasures()!=null){
                shipYard.returnPart(ship.getCounterMeasures());
            }
            CounterMeasures part = (CounterMeasures) box.getValue();
            ship.setCounterMeasures(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(drift());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select a Counter Measure"),box,btn);
        return vBox;
    }
    private static Node drift(){
        ComboBox<Part> box = new ComboBox<>();
        //TODO NULL ITEM
        //box.getItems().add(new Drift(0));
        box.getItems().addAll(ShipYard.getParts(new Drift()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getDriftEngine()!=null){
                shipYard.returnPart(ship.getDriftEngine());
            }
            Drift part = (Drift) box.getValue();
            ship.setDriftEngine(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            if (ship.getBays().length>0) {
                root.setCenter(bays());
            }else {
                root.setCenter(thrusters());
            }
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select a Drift Engine"),box,btn);
        return vBox;
    }
    private static Node bays(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new ExpansionBay()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            ExpansionBay part = (ExpansionBay) box.getValue();
            ship.addBay(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(SFText.create("Whats next?"));
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Choose your Expansion Bays Measure"),box,btn);
        return vBox;
    }
    private static Node thrusters(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new Thruster()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getThrusters()!=null){
                shipYard.returnPart(ship.getThrusters());
            }
            Thruster part = (Thruster) box.getValue();
            ship.setThrusters(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(computers());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select your Thrusters"),box,btn);
        return vBox;
    }
    private static Node computers(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new ShipComputer()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getComputer()!=null){
                shipYard.returnPart(ship.getComputer());
            }
            ShipComputer part = (ShipComputer) box.getValue();
            ship.setComputer(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(security());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select your Ship's Computer"),box,btn);
        return vBox;
    }
    private static Node security(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new Security()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            Security part = (Security) box.getValue();
            ship.addSecurity(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(shield());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select Any Security Measures You Want."),box,btn);
        return vBox;
    }
    private static Node sensors(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new Sensor()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getSensor()!=null){
                shipYard.returnPart(ship.getSensor());
            }
            Sensor part = (Sensor) box.getValue();
            ship.setSensor(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(SFText.create("Choose Which Weapon To Add On Side "));
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select Your Ship's Sensors."),box,btn);
        return vBox;
    }
    private static Node shield(){
        ComboBox<Part> box = new ComboBox<>();
        box.getItems().addAll(ShipYard.getParts(new Shields()));
        Button btn = new Button("Next");
        btn.setOnAction(actionEvent -> {
            if (ship.getShields()!=null){
                shipYard.returnPart(ship.getShields());
            }
            Shields part = (Shields) box.getValue();
            ship.setShields(part);
            shipYard.buyPart(part);
            currentShip(ship);
            shoppingCart(shipYard);
            root.setCenter(sensors());
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(SFText.create("Select The Shields You Want."),box,btn);
        return vBox;
    }
//    private static Node weapons(String key){
//        String type = key.substring(key.indexOf('(')+1,key.indexOf(')'));
//        type = type.replace(type.charAt(0),Character.toUpperCase(type.charAt(0)));
//        ComboBox<ShipWeapon> box = new ComboBox<>();
//        box.getItems().addAll(shipYard.getWeaponsByType(type));
//        Button btn = new Button("Buy");
//        btn.setOnAction(actionEvent -> {
//            ShipWeapon weapon = box.getValue();
//            ship.getWeapons().put(key,weapon);
//            shipYard.buyPart(weapon);
//            currentShip(ship);
//            shoppingCart(shipYard);
//            root.setCenter(SFText.create("Choose Which Weapon To Add On Side "));
//        });
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.getChildren().addAll(SFText.create("Select Your Weapons."),box,btn);
//        return vBox;
//    }


    //Current Ship(Left Bar)//
    private static void currentShip(StarShip ship){
        ScrollPane pane = new ScrollPane();
        pane.setPrefWidth(500);
        pane.setFitToWidth(true);
        pane.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 5");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setPadding(new Insets(5));
        if (ship!=null) {
            String s = "Current Ship:"+
                    "\n\tTier " + ship.getTier().getTier()+
                    "\n\tFrame: "+ship.getFrame().getName()+
                    "\n\tSize: "+ship.getSize()+
                    "\n\tHp: "+ship.getHp()+" DT: "+ship.getDt()+" CT: "+ship.getCt()+
                    "\n\tAC: "+ship.getAc()+" TL: "+ship.getTl()+
                    "\n\tShields: "+ship.getShieldTotal()+" ("+ship.getRegenPerMin()+" per min)"+
                    "\n\tPilot Mod: "+ship.getPilotMod()+" Turn: "+ship.getTurn();
            HBox current = currentPartBox(s,0);
            HBox name = nameMakeBox(1);
            HBox make = nameMakeBox(0);
            HBox powerBox = currentPartBox("Power Core: "+displayPart(ship.getPowerCore()),1);
            HBox armor = currentPartBox("Armor: "+displayPart(ship.getArmor()),2);
            HBox crew = currentPartBox("Crew Quarters: "+displayPart(ship.getCrewQuarter()),3);
            HBox dcm = currentPartBox("DCM: "+displayPart(ship.getCounterMeasures()),4);
            HBox drift = currentPartBox("Drift Engine: "+displayPart(ship.getDriftEngine()),5);
            Text bay = SFText.create(" Expansion Bays: ");
            HBox[] bays = new HBox[ship.getBays().length];
            for (int i = 0; i < bays.length; i++) {
                if (ship.getBays()[i]==null){
                    bays[i] = currentPartBox("\tCargo Hold",6);
                } else {
                    bays[i] = currentPartBox("\t"+displayPart(ship.getBays()[i]),6);
                }
            }
            if (bays.length==0){
                bays = new HBox[1];
                bays[0] = currentPartBox("     No Expansion Slots",0);
            }
            vBox.getChildren().addAll(current,name,make,powerBox,armor,crew,dcm,drift,bay);
            for (HBox t : bays) { vBox.getChildren().add(t); }
            HBox thrusters = currentPartBox("Thrusters: "+displayPart(ship.getThrusters()),7);
            HBox computer = currentPartBox("Computer: " +displayPart(ship.getComputer()),8);
            Text security = SFText.create(" Securities: ");
            HBox[] securities = new HBox[ship.getSecurities().size()+1];
            vBox.getChildren().addAll(thrusters,computer,security);
            for (int i = 0; i < securities.length; i++) {
                if (i < (securities.length-1)){
                    securities[i]= currentPartBox("\t"+displayPart(ship.getSecurities().get(i)),9);
                } else {
                    securities[i] = currentPartBox("\tEmpty",9);
                }
            }
            for (HBox box:securities) {
                vBox.getChildren().add(box);
            }
            HBox shield = currentPartBox("Shields: "+displayPart(ship.getShields()),10);
            HBox sensor = currentPartBox("Sensors: "+displayPart(ship.getSensor()),11);
            Text weapons = SFText.create(" Weapons: ");
            vBox.getChildren().addAll(shield,sensor,weapons);
//            for (String key: ship.getWeapons().keySet()) {
//                vBox.getChildren().add(weaponBox(key));
//            }
        } else {
            HBox current = currentPartBox("Current Ship:\n\tSet Tier and Frame",0);
            vBox.getChildren().addAll(current);
        }
        pane.setContent(vBox);
        root.setLeft(pane);
    }
    private static HBox nameMakeBox(int isName){
        HBox box = new HBox();
        box.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 0 4 0");
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        Text text;
        Button btn = new Button();
        TextField textField = new TextField();
        textField.setMaxWidth(200);
        if (isName==1) {
            text = SFText.create("Ship Name:   ");
            if (ship.getName()==null){
                textField.setPromptText("Enter Name");
                btn.setText("Set");
                btn.setOnAction(actionEvent -> {
                    ship.setName(textField.getText());
                    currentShip(ship);
                });
                box.getChildren().addAll(text,region1,textField,region2,btn);
            } else {
                Text text2 = SFText.create(ship.getName());
                text2.setWrappingWidth(115);
                btn.setText("Change");
                btn.setOnAction(actionEvent -> {
                    ship.setName(null);
                    currentShip(ship);
                });
                box.getChildren().addAll(text,region1,text2,region2,btn);
            }
        } else {
            text = SFText.create("Make/Model: ");
            if (ship.getMakeModel()==null){
                textField.setPromptText("Enter Make and Model");
                btn.setText("Set");
                btn.setOnAction(actionEvent -> {
                    ship.setMakeModel(textField.getText());
                    currentShip(ship);
                });
                box.getChildren().addAll(text,region1,textField,region2,btn);
            } else {
                Text text2 = SFText.create(ship.getMakeModel());
                text2.setWrappingWidth(115);
                btn.setText("Change");
                btn.setOnAction(actionEvent -> {
                    ship.setMakeModel(null);
                    currentShip(ship);
                });
                box.getChildren().addAll(text,region1,text2,region2,btn);
            }
        }
        return box;
    }
//    private static HBox weaponBox(String key){
//        HBox box = new HBox();
//        box.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 0 4 0");
//        //Text text = SFText.create("\t"+key.substring(0,key.indexOf(" "))+": "+displayPart(ship.getWeapons().get(key)));
//        Text text = SFText.create("\t"+key+": "+displayPart(ship.getWeapons().get(key)));
//        Region region1 = new Region();
//        HBox.setHgrow(region1, Priority.ALWAYS);
//        Button btn = weaponAddReplace(key,displayPart(ship.getWeapons().get(key)));
//        box.getChildren().addAll(text,region1,btn);
//        box.setAlignment(Pos.CENTER_LEFT);
//        return box;
//    }
//    private static Button weaponAddReplace(String key,String addReplace){
//        Button btn = new Button("Replace");
//        if (addReplace.contains("-")) btn.setText("Add");
//        btn.setOnAction(actionEvent -> {
//            root.setCenter(weapons(key));
//        });
//        return btn;
//    }
    private static HBox currentPartBox(String s,int i){
        HBox box = new HBox();
        box.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 0 4 0");
        Text text = SFText.create(s);
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        box.getChildren().addAll(text,region1);
        if (i != 0) {
            Button btn = addReplaceBtn(i, s);
            box.getChildren().add(btn);
        }
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }
    private static Button addReplaceBtn(int i,String s){
        Button btn = new Button("Replace");
        if (s.contains(": -")) btn.setText("Add");
        btn.setOnAction(addReplaceEvent(i));
        return btn;
    }
    private static String displayPart(Part part){
        if (part!=null){
            return part.getName();
        } else return "-";
    }
    private static EventHandler<ActionEvent> addReplaceEvent(int i){
        EventHandler<ActionEvent> click;
        switch (i){
            case 1:
                click = actionEvent -> root.setCenter(powerCore());
                break;
            case 2:
                click = actionEvent -> root.setCenter(armor());
                break;
            case 3:
                click = actionEvent -> root.setCenter(crew());
                break;
            case 4:
                click = actionEvent -> root.setCenter(dcm());
                break;
            case 5:
                click = actionEvent -> root.setCenter(drift());
                break;
            case 6:
                click = actionEvent -> root.setCenter(bays());
                break;
            case 7:
                click = actionEvent -> root.setCenter(thrusters());
                break;
            case 8:
                click = actionEvent -> root.setCenter(computers());
                break;
            case 9:
                click = actionEvent -> root.setCenter(security());
                break;
            case 10:
                click = actionEvent -> root.setCenter(shield());
                break;
            case 11:
                click = actionEvent -> root.setCenter(sensors());
                break;
            default:
                click = actionEvent -> root.setCenter(SFText.create("404"));
        }

        return click;
    }

    // TODO: 8/16/2019 Get buggy shopping cart to work.
    //Shopping Cart(Right Bar)//
    private static void shoppingCart(ShipYard shipYard){
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-border-color: #001d2d; -fx-border-width: 0 5");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(5));
        vBox.setStyle("-fx-background-color: #0d3849;");
        Text bp = SFText.create("Build Points: "+ shipYard.getBuildPoints());
        Text pcu = SFText.create("PCU: "+shipYard.getPcuLeft());
        vBox.getChildren().addAll(bp,pcu,SFText.create("////////////////////////"));
        for (int i = 0; i < shipYard.getCart().size(); i++) {
            String s = shipYard.getCart().get(i).getName()+": -"+shipYard.getCart().get(i).getCost()+" bp";
            if (shipYard.getCart().get(i).getCost().contains(" × size category")){
                s = shipYard.getCart().get(i).getName()+": "+(shipYard.getCart().get(i).getCostInt()*ship.getSizeMod())+" bp";
            }
            Text text = SFText.create(s);
            vBox.getChildren().add(text);
        }
        scroll.setContent(vBox);
        root.setRight(scroll);



    }



}
