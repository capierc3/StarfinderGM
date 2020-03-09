package Equipment.Weapons;

import Equipment.Equipment;

public class Weapon implements Equipment {

    public static enum type{MELEE,GUN,THROWN}

    public static final String[] keys = {"Name","Type","Category", "Level", "Price", "Damage", "Range", "Critical", "Capacity", "Usage", "Bulk", "Special","ID"};
    public static final String[] types = {"All","Basic Melee:One-Handed","Basic Melee:Two-Handed","Grenade","Heavy:Two-Handed","Long Arms:One-Handed","Long Arms:Two-Handed",
            "Small Arms:One-Handed","Snipers:Two-Handed","Special:One-Handed","Special:Two-Handed"};
    public static final String tableName = "Weapons";
    private String name;
    private String type;
    private String category;
    private String level;
    private String price;
    private String damage;
    private String range;
    private String crit;
    private String capacity;
    private String usage;
    private String bulk;
    private String special;
    private String id;

    public String toString(){
        if (id.contains("AM")|id.contains("BM")) {
            return "Name: " + name +
                    "\nLevel: " + level +
                    "\nPrice: " + price +
                    "\nDamage: " + damage +
                    "\nCrit: " + crit +
                    "\nBulk: " + bulk +
                    "\nSpecial: " + special;
        } else if (id.equals("G")) {
            return "Name: " + name +
                    "\nLevel: " + level +
                    "\nPrice: " + price +
                    "\nRange: " + range +
                    "\nCapacity: " + capacity +
                    "\nBulk: " + bulk +
                    "\nSpecial: " + special;
        }else {
            return "Name: "+name+
                    "\nLevel: "+level+
                    "\nPrice: "+price+
                    "\nDamage: "+damage+
                    "\nRange: "+range+
                    "\nCritical: "+crit+
                    "\nCapacity: "+capacity+
                    "\nUsage: "+usage+
                    "\nBulk: "+bulk+
                    "\nSpecial: "+special;
        }
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
        category = values[2];
        level=values[3];
        price = values[4];
        damage = values[5];
        range = values[6];
        crit = values[7];
        capacity = values[8];
        usage = values[9];
        bulk = values[10];
        special = values[11];
        id = values[12];
    }


    //Getters
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

    @Override
    public String getType() {
        return type;
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
    public String getSpecial() {
        return special;
    }
}
