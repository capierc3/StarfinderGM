package Equipment;

import ArchivesBuilder.SQLite;
import Equipment.Armor.Armor;
import Equipment.Armor.ArmorUpgrade;
import Equipment.Armor.Shields;
import Equipment.Weapons.Ammo;
import Equipment.Weapons.SolarianCrystal;
import Equipment.Weapons.Weapon;
import Equipment.Weapons.WeaponAccessory;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the equipment objects
 */
public class EquipmentStore {

    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armor;
    private ArrayList<Ammo> ammo;
    private ArrayList<SolarianCrystal> crystals;
    private ArrayList<WeaponAccessory> accessories;
    private ArrayList<Shields> shields;
    private ArrayList<ArmorUpgrade> armorUpgrades;
    public enum size{Tiny, Small, Average, large, Huge, All}
    private int sizeMod;
    private boolean all;
    private int levelLow;
    private int levelHigh;
    private String type;
    private String eqType;
    private String sort;
    private String[] sortTypes = {"By Level"};


    public EquipmentStore(int levelLow,int levelHigh, String type,String eqType, size size){
        this.levelLow = levelLow;
        this.levelHigh = levelHigh;
        this.type = type;
        this.eqType = eqType;
        this.sort = sortTypes[0];
        sizeMod = 0;
        all = false;
        switch (size){
            case Huge:
                sizeMod = sizeMod + 40;
                break;
            case Tiny:
                sizeMod = sizeMod + 5;
                break;
            case large:
                sizeMod = sizeMod + 30;
                break;
            case Small:
                sizeMod = sizeMod + 10;
                break;
            case Average:
                sizeMod = sizeMod + 20;
                break;
            case All:
                all = true;
        }
    }

    public void fillArmor()throws SQLException {
        ArrayList<String> armorTemp = SQLite.GetNamesByLevel("database","Armor", levelLow, levelHigh);
        armor = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : armorTemp) {
                    Armor a = new Armor();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        armor.add(a);
                    }
                }
            }
        }
    }
    public void fillWeapons() throws SQLException {
        ArrayList<String> weaponsTemp = SQLite.GetNamesByLevel("database","Weapons",levelLow,levelHigh);
        weapons = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")){
                for (String s:weaponsTemp) {
                    Weapon w = new Weapon();
                    SQLite.Read(w,s);
                    if (w.getType().contains(type)){
                        weapons.add(w);
                    }
                }
            }
        } else {
            //Make a size based random store
        }
    }
    public void fillAmmo() throws SQLException {
        ArrayList<String> ammoTemp = SQLite.GetNamesByLevel("database",Ammo.tableName, levelLow, levelHigh);
        ammo = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : ammoTemp) {
                    Ammo a = new Ammo();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        ammo.add(a);
                    }
                }
            }
        }
    }
    public void fillCrystals() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel("database",SolarianCrystal.tableName, levelLow, levelHigh);
        crystals = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    SolarianCrystal a = new SolarianCrystal();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        crystals.add(a);
                    }
                }
            }
        }
    }
    public void fillAccessories() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel("database",WeaponAccessory.tableName, levelLow, levelHigh);
        accessories = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    WeaponAccessory a = new WeaponAccessory();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        accessories.add(a);
                    }
                }
            }
        }
    }
    public void fillShields() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel("database",Shields.tableName, levelLow, levelHigh);
        shields = new ArrayList<>();
        if (all) {
            System.out.println(type);
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    Shields a = new Shields();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        shields.add(a);
                    }
                }
            }
        }
    }
    public void fillArmorUps() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel("database",ArmorUpgrade.tableName, levelLow, levelHigh);
        armorUpgrades = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    ArmorUpgrade a = new ArmorUpgrade();
                    SQLite.Read(a,s);
                    if (a.getType().contains(type)) {
                        armorUpgrades.add(a);
                    }
                }
            }
        }
    }

    public void getEquipment(String name, Equipment equipment){
        try {
            SQLite.Read(equipment,name);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        switch (eqType){
            case "Armor":
                for (Armor value : armor) {
                    names.add(value.getName());
                }
                break;
            case "Ammo":
                for (Ammo value : ammo) {
                    names.add(value.getName());
                }
                break;
            case "Solarian Crystals":
                for (SolarianCrystal crystal : crystals) {
                    names.add(crystal.getName());
                }
                break;
            case"Weapon Accessories":
                for (WeaponAccessory accessory : accessories) {
                    names.add(accessory.getName());
                }
                break;
            case "Shields":
                for (Shields shield : shields) {
                    names.add(shield.getName());
                }
                break;
            case "Armor Upgrades":
                for (ArmorUpgrade armorUpgrade : armorUpgrades) {
                    names.add(armorUpgrade.getName());
                }
                break;
            default:
                for (Weapon weapon : weapons) {
                    names.add(weapon.getName());
                }
        }
        return names;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----Weapons-----\n");
        for (Weapon w:weapons) {
            sb.append("\t"+w.getName()+"\n");
        }
        sb.append(weapons.size());

        return sb.toString();
    }
    public int getLevelHigh() {
        return levelHigh;
    }
    public int getLevelLow() {
        return levelLow;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getEqType() {
        return eqType;
    }
    public String getSort(){
        return sort;
    }
    public void setSort(String sort){
        this.sort = sort;
    }

    public String[] getSortTypes() {
        return sortTypes;
    }
}
