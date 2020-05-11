package ships;

import java.util.ArrayList;

public class ShipComputer extends Part {

    private String bonus;
    private String nodes;

    public ShipComputer() {}

    public ShipComputer(ArrayList<String> list) {
        name = list.get(0);
        bonus = list.get(1);
        nodes = list.get(2);
        pcu = list.get(3);
        cost = list.get(4);
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nBonus: " + bonus
                + "\nNodes: " + nodes
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "computers";
    }
}


