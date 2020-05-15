import ships.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Run {

    public static void main(String[] args) {
        ArrayList<Part> armors;
        armors = ShipYard.getParts(new Drift());
        System.out.println("------------------");
        for (Part part: armors) {
            System.out.println(part);
            System.out.println("------------------");
        }
        //new ShipDatabaseBuilder();
        //Main.main(args);
    }
}
