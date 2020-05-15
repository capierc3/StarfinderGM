package ships;

import java.util.ArrayList;

public class CrewQuarter extends Part {

    public CrewQuarter() {

    }

    public CrewQuarter(ArrayList<String> list) {
        name = list.get(0);
        source = list.get(1);
        cost = list.get(2);
        info = list.get(3);
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nCost: " + cost;
    }

    @Override
    public String getTableName() {
        return "quarters";
    }
}
