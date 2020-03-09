package Equipment.Armor;

import Equipment.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GearHouse {

    private ArrayList<Equipment> lightArmor;
    private ArrayList<Equipment> heavyArmor;
    private ArrayList<Equipment> powerArmor;
    private ArrayList<Equipment> armorUpgrades;
    private ArrayList<ForceField> forceFields;
    private Scanner in;

    public GearHouse(){
        try {
            fillArmorList();
        } catch (FileNotFoundException e){
            System.out.println("GearHouse armor.txt not found");
        }
    }

    //Helpers
    private void fillArmorList() throws FileNotFoundException {
        in = new Scanner(new File("txtFiles/armor.txt"));
        ArrayList<String> table5 = new ArrayList<>();
        String line;
        while (in.hasNext()){
            line = in.nextLine();
            if (line.equalsIgnoreCase("3")){
                this.lightArmor = inArmorTable("light");
            } else if (line.equalsIgnoreCase("4")){
                this.heavyArmor = inArmorTable("heavy");
            } else if (line.equalsIgnoreCase("5")){
                line = in.nextLine();
                while (!line.equalsIgnoreCase("")){
                    table5.add(line);
                    line = in.nextLine();
                }
                Collections.sort(table5);
                this.powerArmor = new ArrayList<Equipment>();
            } else if (line.equalsIgnoreCase("6")){
                this.powerArmor.add(inPowerTable(table5.get(0)));
            } else if (line.equalsIgnoreCase("7")){
                this.powerArmor.add(inPowerTable(table5.get(1)));
            } else if (line.equalsIgnoreCase("8")){
                this.powerArmor.add(inPowerTable(table5.get(2)));
            } else if (line.equalsIgnoreCase("9")){
                this.powerArmor.add(inPowerTable(table5.get(3)));
            } else if (line.equalsIgnoreCase("10")){
                this.powerArmor.add(inPowerTable(table5.get(4)));
            } else if (line.equalsIgnoreCase("11")){
                this.armorUpgrades = inUpgradeTable();
            } else if (line.equalsIgnoreCase("12")){
                this.forceFields = inForceTable();
            }
        }
    }
    public ArrayList<Equipment> getArmorByLevelAndType(double low,double high,ArrayList<Equipment> list){
        list.sort(new SortByLevel());
        ArrayList<Equipment> equipment = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLevelInt()<=high && list.get(i).getLevelInt()>=low){
                equipment.add(list.get(i));
            } else if (list.get(i).getLevelInt()>high) {
                return equipment;
            }
        }
        return equipment;
    }
    private ArrayList<ForceField> inForceTable(){
        String line = in.nextLine();
        ArrayList<ForceField> forceFields = new ArrayList<>();
        while (!line.equalsIgnoreCase("")){
            forceFields.add(new ForceField(line));
            line = in.nextLine();
        }
        return forceFields;
    }
    private ArrayList<Equipment> inUpgradeTable(){
        String line = in.nextLine();
        ArrayList<Equipment> equipment = new ArrayList<Equipment>();
        while (!line.equalsIgnoreCase("")){
            equipment.add(new ArmorUpgrade(line));
            line = in.nextLine();
        }
        return equipment;
    }
    private Equipment inPowerTable(String table5){
        String line = in.nextLine();
        ArrayList<String> pArmor = new ArrayList<>();
        pArmor.add(table5);
        while (!line.equalsIgnoreCase("")){
            pArmor.add(line);
            line = in.nextLine();
        }
        return new PowerArmor(pArmor);
    }
    private ArrayList<Equipment> inArmorTable(String type){
        String line = in.nextLine();
        ArrayList<Equipment> equipment = new ArrayList<>();
        while (!line.equalsIgnoreCase("")) {
            equipment.add(new Armor());
            line = in.nextLine();
        }
        return equipment;
    }
    //Getters
    public ArrayList<Equipment> getLightArmor() {
        return lightArmor;
    }
    public ArrayList<Equipment> getHeavyArmor() {
        return heavyArmor;
    }
    public ArrayList<Equipment> getPowerArmor() {
        return powerArmor;
    }
    public ArrayList<Equipment> getArmorUpgrades() {
        return armorUpgrades;
    }
    public ArrayList<ForceField> getForceFields() {
        return forceFields;
    }
}
