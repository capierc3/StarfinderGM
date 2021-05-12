package runables;

import Equipment.EquipmentDatabaseBuilder;
import ships.ShipDatabaseBuilder;

public class BuildDB {

    public static void main(String[] args) {

        new ShipDatabaseBuilder();
        new EquipmentDatabaseBuilder();

    }

}
