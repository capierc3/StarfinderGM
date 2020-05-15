package ships;

import java.util.ArrayList;

public class Sensor extends Part {

    private String range;
    private String mod;

    public Sensor() {}

    public Sensor(ArrayList<String> list) {
        name = list.get(0);
        range = list.get(1);
        mod = list.get(2);
        cost = list.get(3);
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nRange: " + range
                + "\nModifier: " + mod
                + "\nCost: " + cost;
    }

    @Override
    public String getTableName() {
        return "sensors";
    }
}
