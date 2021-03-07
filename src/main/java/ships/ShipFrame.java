package ships;

import java.util.ArrayList;

/**
 * Class for the Ships frame object.
 *
 * @author Chase
 */
public class ShipFrame extends Part {

    private String name;
    private String size;
    private String maneuver;
    private String hp;
    private String dt;
    private String ct;
    private String mounts;
    private String bays;
    private String minCrew;
    private String maxCrew;
    private String cost;
    private String hpInc;
    private String source;
    private String other;

    public ShipFrame() {}

    public ShipFrame(ArrayList<String> list) {
        name = list.get(0);
        source = list.get(1);
        size = list.get(2);
        maneuver = list.get(3);
        hp = list.get(4);
        dt = list.get(5);
        ct = list.get(6);
        mounts = list.get(7);
        bays = list.get(8);
        minCrew = list.get(9);
        maxCrew = list.get(10);
        cost = list.get(11);
        other = list.get(12);
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getManeuver() {
        return maneuver;
    }

    public int[] getHp() {
        String[] split = hp.split(" ");
        int[] hpInc = {0,0};
        try {
            hpInc[0] = Integer.parseInt(split[1]);
            hpInc[1] = Integer.parseInt(split[3].replace(");",""));
        } catch (NumberFormatException e) {
            System.out.println("ERROR getHP SHIP FRAME");
        }
        return hpInc;
    }


    public Integer getDt() {
        try {
            return Integer.parseInt(dt.replaceAll("[^0-9]",""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

//    public Integer getCt() {
//        return getHp() / 5;
//    }

    public String getMounts() {
        return mounts;
    }

    public String getBays() {
        return bays.replaceAll("[^0-9]","");
    }

    public String getMinCrew() {
        return maxCrew.replaceAll("[^0-9]","");
    }

    public String getMaxCrew() {
        //String temp = maxCrew.replace(" ","");
        return maxCrew.replaceAll("[^0-9]","");
    }

    public String getCost() {
        return cost;
    }

    /**
     * getter for the pilot Mod int.
     * @return Integer
     */
    public Integer getPilotMod() {
        String turnPMod = maneuver.substring(maneuver.indexOf('(') + 1,maneuver.indexOf(')'));
        turnPMod = turnPMod.split(",")[0].replace(" Piloting","");
        if (turnPMod.contains("+")) {
            return Integer.parseInt(turnPMod.replace("+",""));
        } else if (turnPMod.contains("-")) {
            return  -1 * Integer.parseInt(turnPMod.replace("-",""));
        } else {
            return  0;
        }
    }

    /**
     * override of to return its name.
     * @return String
     */
    @Override
    public String toString() {
        return "Name: " + name
                + "\nSource: " + source
                + "\nSize: " + size
                + "\nManeuverability: " + maneuver
                + "\nHp: " + hp
                + "\nDT: " + dt
                + "\nCT: " + ct
                + "\nMounts: " + mounts
                + "\nBays: " + bays
                + "\nCrew: " + minCrew + " - " + maxCrew
                + "\nCost: " + cost
                + "\nOther: " + other;
    }

    @Override
    public String getTableName() {
        return "baseframes";
    }

    /**
     * returns the string for the parts info.
     * @return String
     */
    public String getInfo() {
        return "Size: " + size
                + "\nManeuverability: " + maneuver
                + "\nHP: " + hp + ", DT: " + dt + ", CT: " + ct
                + "\nMounts: " + mounts
                + "\nExpansion Bays: " + bays
                + "\nMinimum Crew: " + minCrew
                + "\nMaximum Crew: " + maxCrew
                + "\nCost: " + cost;
    }
}
