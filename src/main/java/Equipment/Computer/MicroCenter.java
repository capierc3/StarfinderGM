package Equipment.Computer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MicroCenter {

    private ArrayList<Computer> computers;
    private ArrayList<ComputerModule> modules;
    private Scanner in;

    public MicroCenter(){
        try {
            in = new Scanner(new File("txtFiles/Computer.txt"));
            modules = new ArrayList<>();
            readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    //Functions//


    //Creation Helpers//
    private void readFile(){
        while (in.hasNext()) {
            String line = in.nextLine();
            if (line.equalsIgnoreCase("3")){
                inComputerTable();
            } else if (line.equalsIgnoreCase("4")){
                inModuleTable("Basic");
            } else if (line.equalsIgnoreCase("5")){
                inModuleTable("Upgrade");
            } else if (line.equalsIgnoreCase("6")){
                inModuleTable("Countermeasures");
            } else if (line.equalsIgnoreCase("7")){
                isSecurity();
            } else if (line.equalsIgnoreCase("8")){
                isShock();
            }

        }
    }
    private void inComputerTable(){
        String line = in.nextLine();
        ArrayList<Computer> computers = new ArrayList<>();
        while (!line.equalsIgnoreCase("")) {
            computers.add(new Computer(line));
            line = in.nextLine();
        }
        this.computers = computers;
    }
    private void inModuleTable(String type){
        String line = in.nextLine();
        while (!line.equalsIgnoreCase("")){
            if (!line.contains("?Varies")) {
                modules.add(new ComputerModule(line, type));
            }
            line = in.nextLine();
        }
    }
    private void isSecurity(){
        String line = in.nextLine();
        while (!line.equalsIgnoreCase("")){
            modules.add(new SecurityModule(line));
            line = in.nextLine();
        }
    }
    private void isShock(){
        String line = in.nextLine();
        while (!line.equalsIgnoreCase("")){
            modules.add(new ShockModule(line));
            line = in.nextLine();
        }
    }
    //Getters//
    public ArrayList<Computer> getComputers() {
        return computers;
    }
    public ArrayList<ComputerModule> getModules() {
        return modules;
    }
}
