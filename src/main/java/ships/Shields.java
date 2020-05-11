package ships;

import java.util.ArrayList;

public class Shields extends Part {

    private String totalSP;
    private String regen;

    public Shields() {}

    public Shields(ArrayList<String> list) {
        name = list.get(0);
        totalSP = list.get(1);
        regen = list.get(2);
        pcu = list.get(3);
        cost = list.get(4);
    }

    public String getRegen() {
        return regen;
    }

    public String getTotalSP() {
        return totalSP;
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nTotal SP: " + totalSP
                + "\nRegen: " + regen
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "shields";
    }
}
