import ships.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestingClass {

    public static void main(String[] args) {

        for (Part p:ShipYard.getParts(new Shields())) {
            System.out.println(p.getName() + ": " + ((Shields) p).isDeflector());
        }

    }



}
