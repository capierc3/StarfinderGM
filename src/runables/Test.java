package runables;
import ArchivesBuilder.*;
import Character.*;
import Equipment.Computer.Computer;
import Equipment.Computer.MicroCenter;
import Equipment.Equipment;
import WorldBuilder.*;
import Equipment.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class Test {



    public static void main(String[] args) throws SQLException {
        new ArchivesToSql();
    }

    public static void buildSector(){

        StarSystem system = new StarSystem(Sector.Population.POPULATED,0,0);
        System.out.println("System Name: "+system.getName());
        System.out.println("System Size: "+system.getSize()+" AU");
        System.out.println("Habitable Zone: "+system.getHabitZone()[0]+" - "+system.getHabitZone()[1]+" ls");
        System.out.println("Stars: ");
        for (Star s:system.getStars()) {
            System.out.println("\t"+s.getType());
        }
        System.out.println("Bodies: ");
        for (Body b:system.getOrderSystem()){
            System.out.println(b);
        }
    }
    private static void sector(){
        Sector sector = new Sector(Sector.Population.COLONIES,0,0,0);
        System.out.println(sector.getName()+" Sector:");
        ArrayList<Star[]> stars = new ArrayList<>();
        for (int i = 0; i < sector.getGrid().length; i++) {
            System.out.print("|");
            for (int j = 0; j < sector.getGrid()[i].length; j++) {
                if (sector.getGrid()[i][j]==null){
                    System.out.print("-");
                } else {
                    System.out.print(sector.getGrid()[i][j].getName());
                    stars.add(sector.getGrid()[i][j].getStars());
                }
            }
            System.out.print("|\n");
        }
        for (int i = 0; i < sector.getGrid().length; i++) {
            for (int j = 0; j < sector.getGrid()[i].length; j++) {
                if (sector.getGrid()[i][j]!=null) {
                    System.out.println("------------------------------------------");
                    System.out.println("Name: " + sector.getGrid()[i][j].getName());
                    System.out.println("Size: "+sector.getGrid()[i][j].getSize()+"AU");
                    System.out.println("Stars: "+sector.getGrid()[i][j].getStars().length);
                    for (int k = 0; k < sector.getGrid()[i][j].getStars().length; k++) {
                        System.out.println("\t" + sector.getGrid()[i][j].getStars()[k].getType());
                    }
                    System.out.println("Bodies: "+sector.getGrid()[i][j].getBodies().length);
//                    for (int k = 0; k < sector.getGrid()[i][j].getOrderSystem().length; k++) {
//                        if (sector.getGrid()[i][j].getOrderSystem()[k]!=null) {
//                            System.out.println(sector.getGrid()[i][j].getOrderSystem()[k].toString());
//                        }
//                    }
                    System.out.println();
                }
            }
        }
    }
    private static void database(){
        //new ArchivesRipper();
        try {
            URLRipper.textLayout("https://www.aonsrd.com/Afflictions.aspx?Category=Disease","test","test3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void run() {
        buildComputer(new MicroCenter(),"Test");
    }
    private static void buildComputer(MicroCenter microCenter,String compName){
        Scanner in = new Scanner(System.in);
        int choice;
        int price = 0;
        System.out.println("Welcome to Micro Center!");
        System.out.println("What base system do you want to start with?");
        for (int i = 0; i < microCenter.getComputers().size(); i++) {
            System.out.println((i + 1) + ":");
            System.out.println(microCenter.getComputers().get(i));
        }
        choice = in.nextInt();
        Computer prisonSecurity = microCenter.getComputers().get(choice-1);
        price = Integer.parseInt(prisonSecurity.getPrice());
        System.out.println("Do you want any basic modules?");
        System.out.println("Yes: 1, No: 0");
        choice = in.nextInt();
        while (choice>0) {
            for (int i = 0; i < microCenter.getModules().size(); i++) {
                if (microCenter.getModules().get(i).getType().equalsIgnoreCase("Basic")) {
                    System.out.println((i + 1) + ": ");
                    System.out.println(microCenter.getModules().get(i));
                    System.out.println();
                }
            }
            System.out.println("None: 0");
            choice = in.nextInt();
            if (choice>0) {
                prisonSecurity.addModule(microCenter.getModules().get(choice - 1));
                System.out.println("input cost(base cost = "+prisonSecurity.getPrice()+"): "+microCenter.getModules().get(choice-1).getPrice());
                price = price+in.nextInt();
            }
            System.out.println("Any other Basics?");
            System.out.println("Yes: 1, No: 0");
            choice = in.nextInt();
        }
        System.out.println("Do you need any upgrades?\nYes:1,No:0");
        choice = in.nextInt();
        while (choice>0){
            for (int i = 0; i < microCenter.getModules().size(); i++) {
                if (microCenter.getModules().get(i).getType().equalsIgnoreCase("Upgrade")) {
                    System.out.println((i + 1) + ": ");
                    System.out.println(microCenter.getModules().get(i));
                    System.out.println();
                }
            }
            System.out.println("None: 0");
            choice = in.nextInt();
            if (choice>0) {
                prisonSecurity.addModule(microCenter.getModules().get(choice - 1));
                System.out.println("input cost(base cost = "+prisonSecurity.getPrice()+"): "+microCenter.getModules().get(choice-1).getPrice());
                price = price+in.nextInt();
            }
            System.out.println("Any other Upgrades?");
            System.out.println("Yes: 1, No: 0");
            choice = in.nextInt();
        }
        System.out.println("What counter measures do you need?\nNone:0,Show them to me:1");
        choice = in.nextInt();
        while (choice>0){
            for (int i = 0; i < microCenter.getModules().size(); i++) {
                if (microCenter.getModules().get(i).getType().equalsIgnoreCase("Countermeasures")) {
                    System.out.println((i + 1) + ": ");
                    System.out.println(microCenter.getModules().get(i));
                    System.out.println();
                }
            }
            System.out.println("None: 0");
            choice = in.nextInt();
            if (choice>0) {
                prisonSecurity.addModule(microCenter.getModules().get(choice - 1));
                System.out.println("input cost(base cost = "+prisonSecurity.getPrice()+"): "+microCenter.getModules().get(choice-1).getPrice());
                price = price+in.nextInt();
            }
            System.out.println("Any other Countermeasures?");
            System.out.println("Yes: 1, No: 0");
            choice = in.nextInt();
        }
        System.out.println("Okay here you go!");
        try {
            PrintWriter printWriter = new PrintWriter(new File(compName+".txt"));
            printWriter.println(prisonSecurity);
            for (int i = 0; i < prisonSecurity.getModules().size(); i++) {
                printWriter.println(prisonSecurity.getModules().get(i));
            }
            printWriter.println("Final Price = "+price);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Your receipt is ready. Total price is "+price);
        System.out.println("Thank you, come again!");
    }
    private static NPCBasic testNPCBasic(){
        System.out.println("\n\nNPCBasic");
        NPCBasic basic = new NPCBasic(1,NPCBasic.type.COMBATANT, NPCBasic.graft.ANDROID);
        System.out.println(basic.getGender()+": "+basic.getfName()+" "+basic.getlName());
        System.out.println(basic.getAttitude()+" "+basic.getType()+" "+basic.getRace());
        System.out.println(basic.MainArrayDisplay());
        System.out.println(basic.AttackArrayDisplay());
        System.out.println("Weapons: ");
        for (Equipment e : basic.getWeapons()){
            System.out.println(e.getType());
            System.out.println(e.toString());
            System.out.println();
        }
        return basic;
    }
    private static void testNPCHero(){
        System.out.println("NPCHero");
        NPCHero ch = new NPCHero();
        System.out.println(ch.getGender()+": "+ch.getfName()+" "+ch.getlName());
        System.out.println(ch.getAttitude()+" "+ch.getRace()+" "+ch.get$class()+" "+ch.getTheme());
        String[] ab = {"str: ","dex: ","con: ","int: ","wis: ","cha: "};
        int loop = 0;
        for (int i :  ch.getAbilityScores()) {
            System.out.print(ab[loop]+i+" ");
            loop++;
        }
    }

}
