import ships.PowerCore;
import ships.ShipYard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestingClass {

    public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            ArrayList<String> names = ShipYard.getNames("cores");
            int i = 1;
            for (String s: names) {
                System.out.println(i + ") " + s);
                i++;
            }
            System.out.println("Which item do you want?");
            int sel = in.nextInt();
            PowerCore core = (PowerCore) ShipYard.getParts(new PowerCore()).get(sel - 1);
            System.out.println(core.toString());
    }
}
