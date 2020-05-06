package Equipment.Armor;

import Equipment.Equipment;

import java.util.ArrayList;

public class PowerArmor implements Equipment {

    private String name;
    private String level;
    private String price;
    private String eacBonus;
    private String kacBonus;
    private String maxDex;
    private String armorCheck;
    private String speed;
    private String str;
    private String damage;
    private String size;
    private String capacity;
    private String usage;
    private String weaponSlots;
    private String upgradeSlots;
    private String bulk;

    public PowerArmor(ArrayList<String> pArmor){
        for (int i = 0; i < pArmor.size(); i++) {
            String[] split = pArmor.get(i).split("\\?");
            if (i==0){
                this.name = split[0];
                this.level = split[1];
                this.price = split[2];
            } else if (i==1){
                this.eacBonus = split[0].split(":")[1];
                this.kacBonus = split[1].split(":")[1];
            } else if (i==2){
                this.maxDex = split[0].split(":")[1];
                this.armorCheck = split[1].split(":")[1];
                this.speed = split[2].split(":")[1];
            } else if (i==3){
                this.str = split[0].split(":")[1];
                this.damage = split[1].split(":")[1];
                this.size = split[2].split(":")[1];
            } else if (i==4){
                this.capacity = split[0].split(":")[1];
                this.usage = split[1].split(":")[1];
            } else {
                this.weaponSlots = split[0].split(":")[1];
                this.upgradeSlots = split[1].split(":")[1];
                this.bulk = split[2].split(":")[1];
            }
        }
    }



    //Equipment overrides
    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nEAC Bonus: "+eacBonus+
                "\nKAC Bonus: "+kacBonus+
                "\nMax Dex Bonus: "+maxDex+
                "\nArmor Penalty: "+armorCheck+
                "\nSpeed Adjustment: "+speed+
                "\nStrength: "+str+
                "\nDamage: "+damage+
                "\nSize: "+size+
                "\nCapacity: "+capacity+
                "\nUsage: "+usage+
                "\nWeapon Slots: "+weaponSlots+
                "\nUpgrade Slots: "+upgradeSlots+
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

    public String getType() {
        return "Power Armor";
    }

    //Getters
    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String[] getKeys() {
        return new String[0];
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
    public String getStr() {
        return str;
    }
    public String getDamage() {
        return damage;
    }
    public String getSize() {
        return size;
    }
    public String getCapacity() {
        return capacity;
    }
    public String getUsage() {
        return usage;
    }
    public String getWeaponSlots() {
        return weaponSlots;
    }
    public String getUpgradeSlots() {
        return upgradeSlots;
    }
    public String getBulk() {
        return bulk;
    }
}
