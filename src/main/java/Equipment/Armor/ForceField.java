package Equipment.Armor;

import Equipment.Equipment;

import java.lang.reflect.Field;

public class ForceField {

    private String color;
    private String capacity;
    private String tempHP;
    private String fastHealing;
    private String Fortification;

    public ForceField(String line){
        String[] split = line.split("\\?");
        int i = 0;
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                f.set(this,split[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    //Getters
    public String getColor() {
        return color;
    }
    public String getCapacity() {
        return capacity;
    }
    public String getTempHP() {
        return tempHP;
    }
    public String getFastHealing() {
        return fastHealing;
    }
    public String getFortification() {
        return Fortification;
    }
}
