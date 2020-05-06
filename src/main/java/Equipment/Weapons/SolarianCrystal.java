package Equipment.Weapons;

import Equipment.Equipment;

import java.util.Comparator;

public class SolarianCrystal implements Equipment {

    public static String[] types = {"-"};
    public static String[] keys = {"Name","Level","Price","Damage","Critical","Bulk","ID"};
    public static String tableName = "Solarian_Weapon_Crystals";

    private String name;
    private String level;
    private String price;
    private String damage;
    private String crit;
    private String bulk;
    private String id;

    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nDamage: "+damage+
                "\nCritical: "+crit+
                "\nBulk: "+bulk;
    }
    public Integer getLevelInt() {
        return Integer.parseInt(level);
    }
    public Integer getPriceInt() {
        return Integer.parseInt(price.replace(",",""));
    }

    @Override
    public void readSQL(String[] values) {
        this.name = values[0];
        this.level = values[1];
        this.price = values[2];
        this.damage = values[3];
        this.crit = values[4];
        this.bulk = values[5];
        this.id = values[6];
    }

    public String getType(){
        return "-";
    }
    //getters
    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String[] getKeys() {
        return keys;
    }

    public static String[] getTypes() {
        return types;
    }

    public String getLevel() {
        return level;
    }
    public String getPrice() {
        return price;
    }
    public String getDamage() {
        return damage;
    }
    public String getCrit() {
        return crit;
    }
    public String getBulk() {
        return bulk;
    }
}
