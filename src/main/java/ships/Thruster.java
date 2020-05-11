package ships;

import java.util.ArrayList;

public class Thruster extends Part {

    private String size;
    private String speed;
    private String pilotingMod;

    public Thruster() {}

    public Thruster(ArrayList<String> list) {
        name = list.get(0);
        size = list.get(1);
        speed = list.get(2);
        pilotingMod = list.get(3);
        pcu = list.get(4);
        cost = list.get(5);
    }

    public String getSize() {
        return size;
    }

    public Integer getPilotMod() {
        if (pilotingMod.contains("+")) {
            return Integer.parseInt(pilotingMod.substring(1));
        } else  {
            return -1 * Integer.parseInt(pilotingMod.substring(1));
        }
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nSize: " + size
                + "\nSpeed: " + speed
                + "\nPiloting Modifier: " + pilotingMod
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "thrusters";
    }
}
