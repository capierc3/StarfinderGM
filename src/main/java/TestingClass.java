import ships.PowerCore;
import ships.ShipDatabaseBuilder;
import ships.ShipWeapon;
import ships.ShipYard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestingClass {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        for (ShipWeapon weapon: ShipYard.getWeaponsByType("df")) {
            System.out.println(weapon +"\n");
        }
    }

}
