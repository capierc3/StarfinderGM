package Equipment.Armor;

import Equipment.Equipment;

import java.lang.reflect.Field;

public class ArmorUpgrade implements Equipment {

    private String name;
    private String level;
    private String price;
    private String slots;
    private String armorType;
    private String bulk;

    public ArmorUpgrade(String line){
        String[] split = line.split("\\?");
        int i = 0;
        for (Field f: this.getClass().getDeclaredFields()) {
            try {
                f.set(this,split[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }
    }


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

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return getTableName();
    }

    @Override
    public String[] getKeys() {
        return new String[0];
    }

    public String getType() {
        return "Armor Upgrade";
    }
}
