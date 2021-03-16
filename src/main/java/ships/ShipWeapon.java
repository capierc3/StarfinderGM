package ships;

import java.util.ArrayList;

public class ShipWeapon extends Part {

    private String range;
    private String speed;
    private String dmg;
    private String special;
    private String type;

    public ShipWeapon() {}

    public ShipWeapon(ArrayList<String> list) {
        name = list.get(0);
        type = list.get(1);
        range = list.get(2);
        speed = list.get(3);
        dmg = list.get(4);
        pcu = list.get(5);
        cost = list.get(6);
        special = list.get(7);
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nType: " + type
                + "\nRange: " + range
                + "\nDamage: " + dmg
                + "\nSpeed: " + speed
                + "\nSpecial: " + special
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "weapons";
    }
}
