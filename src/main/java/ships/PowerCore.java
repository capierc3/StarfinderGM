package ships;

import java.util.ArrayList;

public class PowerCore extends Part {

    private String size;

    public PowerCore() {}

    public PowerCore(ArrayList<String> list) {
        name = list.get(0);
        size = list.get(1);
        pcu = list.get(2);
        cost = list.get(3);
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nSize: " + size
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "cores";
    }
}
