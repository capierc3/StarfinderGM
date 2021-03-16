package runables;

import WorldBuilder.*;

import javax.sound.midi.Soundbank;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class WorldBuilder {


    public static void main(String[] args) {
        while (true) {
            System.out.println("////////////////////////////////////");
            System.out.println("Welcome to the Galaxy Viewer CLI 0.1");
            System.out.println("////////////////////////////////////");
            System.out.println("1) View current Galaxy");
            System.out.println("2) Add new Sector");
            Scanner in = new Scanner(System.in);
            int input = in.nextInt();
            if (input == 1) {
                System.out.println("\n////////////////////////////////////");
                System.out.println("Pick a Sector to view:");
                ArrayList<Sector> sectors = GalaxyViewer.getSectors();
                for (int i = 0; i < sectors.size(); i++) {
                    Sector sector = sectors.get(i);
                    System.out.println((i + 1) + ") " + sector.getName() + " ["
                            + sector.getCoordinates()[0] + ", "
                            + sector.getCoordinates()[1] + ", "
                            + sector.getCoordinates()[2] + "]");
                }
                input = in.nextInt();
                System.out.println("\nSelect the system in " + sectors.get(input - 1).getName());
                ArrayList<String> systemNames = GalaxyViewer.getSystemNames(sectors.get(input -1).getName());
                for (int i = 0; i < systemNames.size(); i++) {
                    System.out.println((i + 1) + ") " + systemNames.get(i));
                }
                input = in.nextInt();
                StarSystem system = GalaxyDataBase.findSystem(systemNames.get(input -1));
                while (true) {
                    System.out.println("///// " + system.getName() + " /////");
                    for (int i = 0; i < system.getOrderSystem().size(); i++) {
                        System.out.println((i + 1) + ") " + system.getOrderSystem().get(i).getName());
                    }
                    System.out.println("View body #: ");
                    input = in.nextInt();
                    if (input == 0) {
                        break;
                    } else {
                        System.out.println(system.getOrderSystem().get(input - 1));
                        System.out.println("//////////////////////////////");
                    }
                }

            } else {
                new GalaxyDataBase();
                System.out.println("Current Sectors locations:");
                ArrayList<Sector> sectors = GalaxyViewer.getSectors();
                if (sectors.size() == 0) {
                    System.out.println("No sectors found");
                } else {
                    for (Sector sector : sectors) {
                        System.out.println("[" + sector.getCoordinates()[0] + ", " +
                                sector.getCoordinates()[1] + ", " +
                                sector.getCoordinates()[2] + "]");
                    }
                }
                System.out.println("Where is the new Sector");
                System.out.println("x:");
                int x = in.nextInt();
                System.out.println("y:");
                int y = in.nextInt();
                System.out.println("z:");
                int z = in.nextInt();
                System.out.println("What is the population status of the Sector?");
                System.out.println("1) " + Sector.Population.NONE);
                System.out.println("2) " + Sector.Population.RESEARCH.toString());
                System.out.println("3) " + Sector.Population.COLONIES.toString());
                System.out.println("4) " + Sector.Population.POPULATED.toString());
                input = in.nextInt();
                Sector sector;
                switch (input) {
                    case 1:
                        sector = new Sector(Sector.Population.NONE, x, y, z);
                        break;
                    case 2:
                        sector = new Sector(Sector.Population.RESEARCH, x, y, z);
                        break;
                    case 3:
                        sector = new Sector(Sector.Population.COLONIES, x, y, z);
                        break;
                    default:
                        sector = new Sector(Sector.Population.POPULATED, x, y, z);
                }
                GalaxyDataBase.addEntry(sector);
                System.out.println("Sector added!");
            }
        }
    }

}
