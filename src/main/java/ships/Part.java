package ships;

import java.util.Comparator;

public abstract class Part implements ShipComponent {

    protected String cost;
    protected String pcu;
    protected String partType;
    protected String name;

    protected Part(){

    }

    //For "None" option//
    protected Part(int i) {
        name = "None";
        cost = "-";
        pcu = "-";
    }

    public String getPcu() {
        return pcu;
    }

    public String getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    protected String toStringTop() {
        return "Name: " + name;
    }

    protected String toStringBTM() {
        return "\nPCU: " + pcu
                + "\nCost: " + cost;
    }

    public Integer getCostInt() {
        if (cost == null) {
            return 0;
        }
        if (cost.equalsIgnoreCase("-")) {
            return 0;
        }
        if (cost.contains(" × size category")) {
            return (-1 * Integer.parseInt(cost.replace(" × size category","")));
        }
        if (cost.contains("+ item level of weapon")) {
            return (-1 * Integer.parseInt(cost.replace("+ item level of weapon","")));
        }
        return Integer.parseInt(cost);
    }

    public Integer getPcuInt() {
        if (pcu == null) {
            return 0;
        }
        if (pcu.equalsIgnoreCase("-")) {
            return 0;
        }
        return Integer.parseInt(pcu);
    }

    public static Comparator<Part> compareCost() {
        return new Comparator<Part>() {
            @Override
            public int compare(Part o1, Part o2) {
                return o1.getCostInt().compareTo(o2.getCostInt());
            }
        };
    }

    public static Comparator<Part> comparePCU() {
        return new Comparator<Part>() {
            @Override
            public int compare(Part o1, Part o2) {
                return o1.getPcuInt().compareTo(o2.getPcuInt());
            }
        };
    }


}
