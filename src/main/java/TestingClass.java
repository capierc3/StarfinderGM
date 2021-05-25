import Equipment.Computer.Computer;
import Equipment.Computer.ComputerModule;
import Equipment.Computer.MicroCenter;

import java.util.ArrayList;
import java.util.Scanner;

public class TestingClass {

    public static void main(String[] args) {

        System.out.println("Welcome to Micro Center!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("What tier computer are you looking for?");
        System.out.println("Tier | Price | Hack DC");
        for (int i = 1; i <= 10; i++) {
            int[] tierInfo = Computer.getTier(i);
            System.out.println(i + "|" + tierInfo[0] + "|" + tierInfo[1]);
        }
        Scanner in = new Scanner(System.in);
        String response = in.next();
        
        ArrayList<ComputerModule> parts = MicroCenter.getPart("Countermeasures");
        for (ComputerModule part: parts) {
            System.out.println(part);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        }

    }



}
