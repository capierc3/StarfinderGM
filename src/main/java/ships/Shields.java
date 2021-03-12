package ships;

import java.util.ArrayList;

public class Shields extends Part {

    private String totalSP;
    private String regen;
    private String dv;
    private String ac_tl;

    public Shields() {}

    public Shields(ArrayList<String> list) {
        name = list.get(0);
        totalSP = list.get(1);
        regen = list.get(2);
        dv = list.get(3);
        ac_tl = list.get(4);
        pcu = list.get(5);
        cost = list.get(6);
    }

    public String getRegen() {
        return regen;
    }

    public String getTotalSP() {
        return totalSP;
    }

    public String getAc_tl() {
        return ac_tl;
    }

    public String getDV() {
        return dv;
    }

    @Override
    public String toString() {
        if (isDeflector()) {
            return toStringTop()
                    + "\nDV: " + dv
                    + "\nAC/TL: " + ac_tl
                    + toStringBTM();
        } else {
            return toStringTop()
                    + "\nTotal SP: " + totalSP
                    + "\nRegen: " + regen
                    + toStringBTM();
        }
    }

    @Override
    public String getTableName() {
        return "shields";
    }

    public boolean isDeflector() {
        return totalSP.contains("—");
    }
}
