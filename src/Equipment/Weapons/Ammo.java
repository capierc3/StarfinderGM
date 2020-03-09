package Equipment.Weapons;

import Equipment.Equipment;

public class Ammo implements Equipment {

    public static final String[] keys = {"Name","Category","Level","Price","Bulk","Special","ID"};
    public static final String[] types = {"All","—","Special"};
    public static final String tableName = "Ammunition";

    private String name;
    private String category;
    private String level;
    private String price;
    private String bulk;
    private String special;
    private String id;

    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nBulk: "+bulk+
                "\nSpecial: "+special;
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
        this.category = values[1];
        this.level = values[2];
        this.price = values[3];
        this.bulk = values[4];
        this.special = values[5];
        this.id = values[6];
    }

    public String getType() {
        return category;
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

    public String getLevel() {
        return level;
    }
    public String getPrice() {
        return price;
    }
    public String getBulk() {
        return bulk;
    }
    public String getSpecial() {
        return special;
    }
}
