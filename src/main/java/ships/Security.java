package ships;

import java.util.ArrayList;

public class Security extends Part {

    public Security() {}

    public Security(ArrayList<String> list) {
        name = list.get(0);
        cost = list.get(1);
    }

    @Override
    public String toString() {
        return toStringTop()
               +  "\nCost: " + cost;
    }

    @Override
    public String getTableName() {
        return "security";
    }
}
