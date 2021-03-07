package ships;

import java.util.ArrayList;

public class Armor extends Part {

    //TODO new armor type
    private String bonusAc;
    private String special;
    private String tempHP;

    public Armor() {

    }

    public Armor(ArrayList<String> list) {
        name = list.get(0);
        bonusAc = list.get(1);
        tempHP = list.get(2);
        special = list.get(3);
        cost = list.get(4);
        pcu = "-";
    }

    public String getBonusAc() {
        return bonusAc;
    }

    public String getSpecial() {
        return special;
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nBonus to AC: " + bonusAc
                + "\nSpecial: " + special
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "armor";
    }
}
