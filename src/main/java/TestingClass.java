import ships.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestingClass {

    public static void main(String[] args) {

        ArrayList<Part> parts = ShipYard.getParts(new ShipFrame());
//        for (Part p:parts) {
//            System.out.println(((ShipFrame) p).getMaxCrew());
//        }

    }



}
