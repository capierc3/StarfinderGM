package Equipment.Augmentations;

import Equipment.Equipment;

public class Augmentations implements Equipment {

    public enum systems {ARM_R,ARM_L,LEG_R,LEG_L,HAND_R,HAND_L,FOOT_R,FOOT_L,LUNGS,THROAT,BRAIN,EYES,SKIN,HEART,SPINAL,
        ACCELERATOR_MK1,ACCELERATOR_MK2,ACCELERATOR_MK3}

    private String name;
    private String level;
    private String price;
    private String system;

    public Augmentations(String line){
        String[] split = line.split("\\?");
        this.name = split[0];
        this.level = split[1];
        this.price = split[2];
        this.system = split[3];
    }

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

    public String getSystem() {
        return system;
    }
    public String toString(){
        return "Name: "+name+
                "\nLevel: "+level+
                "\nPrice: "+price+
                "\nSystem: "+system;
    }
    public String getPrice() {
        return price;
    }
    public String getLevel() {
        return level;
    }
    public String getType() {
        return "Augmentation";
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
}
