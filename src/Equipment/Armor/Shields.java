package Equipment.Armor;

import Equipment.Equipment;

public class Shields implements Equipment {

    public static final String tableName = "Shields";
    public static final String[] keys = {"Name","Type","Level","Price","Shield_Bonus","Max_Dex","Armor_Check_Penalty","Bulk","Upgrades","ID"};
    public static final String[] types = {"-"};

    private String name;
    private String type;
    private String level;
    private String price;
    private String bonus;
    private String maxDex;
    private String checkPen;
    private String bulk;
    private String upgrades;
    private String id;


    @Override
    public String getPrice() {
        return price;
    }
    @Override
    public String getLevel() {
        return level;
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public Integer getLevelInt() {
        return Integer.parseInt(level);
    }
    @Override
    public Integer getPriceInt() {
        return Integer.parseInt(price);
    }
    @Override
    public void readSQL(String[] values) {
        name = values[0];
        type = values[1];
        level = values[2];
        price = values[3];
        bonus = values[4];
        maxDex = values[5];
        checkPen = values[6];
        bulk = values[7];
        upgrades = values[8];
        id = values[9];
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
}
