package ships;

import java.util.ArrayList;

public class Armor extends Part {

    private String bonusAc;
    private String special;

    public Armor() {
        super();
//        bonusAc = line.split("\\?")[1];
//        special = line.split("\\?")[2];
//        cost = line.split("\\?")[3];
        pcu = "-";
    }

    public Armor(ArrayList<String> list) {
        name = list.get(0);
        bonusAc = list.get(1);
        special = list.get(2);
        cost = list.get(3);
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
}
