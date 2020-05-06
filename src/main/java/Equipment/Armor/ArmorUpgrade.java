package Equipment.Armor;

import Equipment.Equipment;

import java.lang.reflect.Field;

public class ArmorUpgrade implements Equipment {

    public static String tableName = "ArmorUpgrades";
    public static String[] keys = {"Name","Type","Level","Price","Slots","Armor_Type","Capacity","Usage","Bulk","ID"};
    public static String[] types = {"-"};

    private String name;
    private String type;
    private String level;
    private String price;
    private String slots;
    private String armorType;
    private String bulk;
    private String id;

    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nSlots: "+slots+
                "\nArmor Type: "+armorType+
                "\nBulk: "+bulk;
    }
    public String getPrice() {
        return this.price;
    }
    public String getLevel() {
        return this.level;
    }
    public Integer getLevelInt() {
        return Integer.parseInt(level);
    }
    public Integer getPriceInt() {
        return Integer.parseInt(price.replace(",",""));
    }

    @Override
    public void readSQL(String[] values) {
        name = values[0];
        type = values[1];
        level = values[2];
        price = values[3];
        slots = values[4];
        armorType = values[5];
        bulk = values[6];
        id = values[7];
    }

    @Override
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

    public String getType() {
        return type;
    }
}
