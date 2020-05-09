import ships.Armor;
import ships.Part;
import ships.ShipDatabaseBuilder;
import ships.ShipYardNew;

import java.sql.SQLException;
import java.util.ArrayList;

public class Run {

    public static void main(String[] args) {
        ArrayList<Part> armors = ShipYardNew.getParts(new Armor());
        System.out.println("------------------");
        for (Part part: armors) {
            System.out.println(part);
            System.out.println("------------------");
        }
        //new ShipDatabaseBuilder();
        //Main.main(args);
    }
}
