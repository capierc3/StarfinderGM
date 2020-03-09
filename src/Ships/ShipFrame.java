package Ships;

import java.util.ArrayList;

public class ShipFrame implements ShipComponent {

    private String name;
    private String size;
    private String maneuver;
    private Integer hp;
    private Integer dt;
    private Integer ct;
    private String mounts;
    private String bays;
    private String minCrew;
    private String maxCrew;
    private String cost;
    private Integer hpInc;

    public ShipFrame(ArrayList<String> strings){
        name = strings.get(0);
        size = strings.get(1).replace("Size ","");
        maneuver = strings.get(2).replace("Maneuverability ","");
        String[] split = strings.get(3).split(";");
        String hpString = split[0].replace("HP ","");
        int space = hpString.length();
        for (int i = 0; i < hpString.length(); i++) {
            if (hpString.charAt(i)==' '){
                space = i;
                break;
            }
        }
        hp = Integer.parseInt(hpString.substring(0,space));
        hpInc = Integer.parseInt(hpString.substring(space+2,hpString.length()-1)
                .replace("increment ",""));
        String dtString = split[1].replace(" DT ","");
        if (dtString.equalsIgnoreCase("—")){ dt = 0;}
        else dt = Integer.parseInt(dtString.replace(" ",""));
        ct = Integer.parseInt(split[2].replace(" CT ",""));
        mounts = strings.get(4).replace("Mounts ","");
        bays = strings.get(5).replace("Expansion Bays ","");
        split = strings.get(6).split(";");
        minCrew = split[0].replace("Minimum Crew ","");
        maxCrew = split[1].replace(" Maximum Crew ","");
        cost = strings.get(7).replace("Cost ","");
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
    public Integer getHp() {
        return hp;
    }
    public Integer getDt() {
        return dt;
    }
    public Integer getCt() {
        return ct;
    }
    public String getMounts() {
        return mounts;
    }
    public String getBays() {
        return bays;
    }
    public String getMinCrew() {
        return minCrew;
    }
    public String getMaxCrew() {
        return maxCrew;
    }
    public String getCost() {
        return cost;
    }
    public Integer getHpInc() {
        return hpInc;
    }
    public Integer getPilotMod(){
        String turnPMod = maneuver.substring(maneuver.indexOf('(')+1,maneuver.indexOf(')'));
        turnPMod = turnPMod.split(",")[0].replace(" Piloting","");
        if (turnPMod.contains("+")){
            return Integer.parseInt(turnPMod.replace("+",""));
        } else if (turnPMod.contains("-")){
            return  -1*Integer.parseInt(turnPMod.replace("-",""));
        } else return  0;
    }

    @Override
    public String toString() {
        return name;
    }
    public String getInfo() {
        return "Size: "+size+
                "\nManeuverability: "+maneuver+
                "\nHP: "+hp+", DT: "+dt+", CT: "+ct+
                "\nMounts: "+mounts+
                "\nExpansion Bays: "+ bays+
                "\nMinimum Crew: "+minCrew+
                "\nMaximum Crew: "+maxCrew+
                "\nCost: "+cost;
    }
}
