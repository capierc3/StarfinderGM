package Equipment.Armor;

import Equipment.Equipment;

import java.lang.reflect.Field;

public class Armor implements Equipment{

    public static final String[] keys = {"Name","Type", "Level", "Price", "EAC_Bonus", "KAC_Bonus", "Maximum_Dex_Bonus", "Armor_Check_Penalty",
            "Speed_Adjustment", "Upgrade_Slots", "Bulk","ID"};
    public static final String[] types = {"All","Heavy:-","Light:-","Powered:-"};
    public static final String tableName = "Armor";

    private String name;
    private String type;
    private String level;
    private String price;
    private String eacBonus;
    private String kacBonus;
    private String maxDex;
    private String armorCheck;
    private String speed;
    private String upgradeSlots;
    private String bulk;
    private String id;

    @Override
    public void readSQL(String[] values){
        for (int j = 3; j < this.getClass().getDeclaredFields().length; j++) {
                Field f = this.getClass().getDeclaredFields()[j];
                try {
                    f.set(this,values[j-3]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }


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

    public String getEacBonus() {
        return eacBonus;
    }
    public String getKacBonus() {
        return kacBonus;
    }
    public String getMaxDex() {
        return maxDex;
    }
    public String getArmorCheck() {
        return armorCheck;
    }
    public String getSpeed() {
        return speed;
    }
    public String getUpgradeSlots() {
        return upgradeSlots;
    }
    public String getBulk() {
        return bulk;
    }

    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nEAC Bonus: "+eacBonus+
                "\nKAC Bonus: "+kacBonus+
                "\nMax Dex Bonus: "+maxDex+
                "\nArmor Penalty: "+armorCheck+
                "\nSpeed Adjustment: "+speed+
                "\nUpgrade Slots: "+upgradeSlots+
                "\nBulk: "+bulk;
    }
    @Override
    public String getPrice() {
        return this.price;
    }
    @Override
    public String getLevel() {
        return this.getLevel();
    }
    public Integer getLevelInt() {
        return Integer.parseInt(level);
    }
    public Integer getPriceInt() {
        return Integer.parseInt(price.replace(",",""));
    }


    public String getType() {
        return type+" Armor";
    }

    public static String[] getTypes() {
        return types;
    }
}
