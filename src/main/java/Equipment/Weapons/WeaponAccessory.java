package Equipment.Weapons;

import Equipment.Equipment;

public class WeaponAccessory implements Equipment {

    public final static String tableName = "Weapon_Accessories";
    public final static String[] keys =  {"Name","Level","Price","Bulk","Capacity","Usage","Weapon_Type","ID"};
    public final static String[] types = {"All","railed weapon","railed weapon, small arm","small arm","projectile","heavy weapon","melee weapon, small arm","melee weapon","any"};

    private String name;
    private String level;
    private String price;
    private String bulk;
    private String cap;
    private String use;
    private String wType;
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
        return wType;
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
        level = values[1];
        price = values[2];
        bulk = values[3];
        cap = values[4];
        use = values[5];
        wType = values[6];
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
    @Override
    public  String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nCapacity: "+cap+
                "\nUsage: "+use+
                "\nWeapon type: "+wType+
                "\nBulk: "+bulk;
    }
}
