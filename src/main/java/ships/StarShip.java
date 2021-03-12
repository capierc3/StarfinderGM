package ships;

import java.util.ArrayList;
import java.util.HashMap;
import Character.SFCharacter;

public class StarShip {

    private String name;
    private ShipTier tier;
    private String makeModel;
    private String size;
    private String length;
    private String weight;
    private ShipFrame frame;
    private String speed;
    private String maneuver;
    private String driftRating;
    private Integer dt;
    private Integer hpInc;
    private Integer shieldTotal;
    private Integer regenPerMin;
    private Integer pilotMod;
    private HashMap<String,ShipWeapon> weapons;
    private Thruster thrusters;
    private ShipComputer computer;
    private PowerCore powerCore;
    private CounterMeasures counterMeasures;
    private CrewQuarter crewQuarter;
    private Part[] bays;
    private ArrayList<Security> securities;
    private Shields shields;
    private Armor armor;
    private Drift driftEngine;
    private Sensor sensor;
    private SFCharacter[] crew;
    private int minCrew;
    private int maxCrew;
    private int turn;

    public StarShip(String name){
        this();
        this.name = name;
    }
    public StarShip(){
        securities = new ArrayList<>();
    }

    //getters and setters//
    public ShipTier getTier() {
        return tier;
    }
    public PowerCore getPowerCore() {
        return powerCore;
    }
    public String getName() {
        return name;
    }
    public String getMakeModel() {
        return makeModel;
    }
    public String getSize() {
        return size;
    }
    public String getLength() {
        return length;
    }
    public String getWeight() {
        return weight;
    }
    public ShipFrame getFrame() {
        return frame;
    }
    public String getSpeed() {
        return speed;
    }
    public String getManeuver() {
        return maneuver;
    }
    public String getDriftRating() {
        return driftRating;
    }
    public Integer getAc() {
        return 10 - getSizeACTL() + getArmorACTL()[0] + getShieldACTL()[0];
    }
    public Integer getTl() {
        return 10 - getSizeACTL() + getArmorACTL()[1] + getDCM_TL() + getShieldACTL()[1];
    }
    public Integer getHp() {
        int hpInc = 0;
        if (!tier.getTier().contains("/")) {
            hpInc = Integer.parseInt(tier.getTier()) / 4;
        }
        return frame.getHp()[0] + (hpInc * frame.getHp()[1]);
    }
    public Integer getDt() {
        return dt;
    }
    public Integer getCt() {
        return getHp() / 5;
    }
    public Integer getShieldTotal() {
        return shieldTotal;
    }
    public Integer getPilotMod() {
        return pilotMod;
    }
    public HashMap<String, ShipWeapon> getWeapons() {
        return weapons;
    }
    public Thruster getThrusters() {
        return thrusters;
    }
    public ShipComputer getComputer() {
        return computer;
    }
    public CounterMeasures getCounterMeasures() {
        return counterMeasures;
    }
    public CrewQuarter getCrewQuarter() {
        return crewQuarter;
    }
    public Part[] getBays() {
        return bays;
    }
    public ArrayList<Security> getSecurities() {
        return securities;
    }
    public Shields getShields() {
        return shields;
    }
    public Armor getArmor() {
        return armor;
    }
    public Drift getDriftEngine() {
        return driftEngine;
    }
    public int getSizeMod(){
        switch (size){
            case " Tiny":
                return 1;
            case " Small":
                return 2;
            case " Medium":
                return 3;
            case " Large":
                return 4;
            case " Huge":
                return 5;
            case " Gargantuan":
                return 6;
            case " Colossal":
                return 7;
        }
        return 0;
    }
    public int getSizeACTL(){
        switch (size.replace(" ","")){
            case "Tiny":
                return -2;
            case "Small":
                return -1;
            case "Medium":
                return 0;
            case "Large":
                return 1;
            case "Huge":
                return 2;
            case "Gargantuan":
                return 4;
            default:
                return 8;
        }
    }
    public Sensor getSensor() {
        return sensor;
    }
    public Integer getRegenPerMin() {
        return regenPerMin;
    }
    public int getTurn() {
        return turn;
    }
    private int[] getArmorACTL() {
        try {
            int[] acTl = {0, 0};
            if (armor.getBonusAc().contains("+")) {
                acTl[0] = Integer.parseInt(armor.getBonusAc().replace("+", ""));
            }
            if (!armor.getSpecial().equalsIgnoreCase("—")) {
                String tl = armor.getSpecial().split(",")[0];
                acTl[1] = Integer.parseInt(tl.replace(" TL", ""));
            }
            return acTl;
        } catch (NullPointerException e) {
            return new int[]{0,0};
        }
    }
    private int getDCM_TL() {
        try {
            if (!counterMeasures.getBonus().equalsIgnoreCase("-")) {
                return Integer.parseInt(counterMeasures.getBonus().replace("+", ""));
            }
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }
    private int[] getShieldACTL() {
        int[] acTl = {0,0};
        if (shields != null && shields.isDeflector()) {
            String[] acTLString = shields.getAc_tl().split("_");
            acTl[0] = Integer.parseInt(acTLString[0].replace(" AC",""));
            acTl[1] = Integer.parseInt(acTLString[1].replace(" TL",""));
        }
        return acTl;
    }
    public void setComputer(ShipComputer computer) {
        this.computer = computer;
    }
    public void setCounterMeasures(CounterMeasures counterMeasures) {
        this.counterMeasures = counterMeasures;
    }
    public void setTier(ShipTier tier) {
        this.tier = tier;
    }
    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }
    public void setWeapons(HashMap<String, ShipWeapon> weapons) {
        this.weapons = weapons;
    }
    public void setThrusters(Thruster thrusters) {
        this.thrusters = thrusters;
        thrusters.getPilotMod();
        this.pilotMod = frame.getPilotMod() + thrusters.getPilotMod();
    }
    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }
    public void setCrewQuarter(CrewQuarter crewQuarter) {
        this.crewQuarter = crewQuarter;
    }
    public boolean addBay(Part part) {
        for (int i = 0; i < bays.length; i++) {
            if (bays[i]==null){
                bays[i] = part;
                return true;
            }
        }
        return false;
    }
    public void replaceBay(Part part,int loc){
        bays[loc] = part;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArmor(Armor armor) {
        this.armor = armor;
    }
    public void setDriftEngine(Drift driftEngine) {
        this.driftEngine = driftEngine;
    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    public void setFrame(ShipFrame frame) {
        String[] mounts = frame.getMounts().split(",");
        weapons = new HashMap<>();
        String loc = "";
        for (String s : mounts) {
            if (Character.isDigit(s.charAt(1))) {
                String amt = s.substring(1, 2);
                String type = s.substring(3).replace(")","");
                if (amt.equalsIgnoreCase("1")) {
                    weapons.put(loc + " (" + type + ")", null);
                } else {
                    for (int i = 0; i < Integer.parseInt(amt); i++) {
                        weapons.put(loc + " " + (i + 1) + " (" + type + ")", null);
                    }
                }
            } else {
                loc = s.substring(0, s.indexOf('('))
                        .replace(" arc", "")
                        .replace(" ", "");
                String amtType;
                if (s.charAt(s.length() - 1) != ')') {
                    amtType = s.substring(s.indexOf('(') + 1, s.length() - 1);
                } else {
                    amtType = s.substring(s.indexOf('(') + 1, s.indexOf(')'));
                }
                String[] amtTypeArray;
                if (amtType.contains("/")) {
                    amtTypeArray = amtType.split("/");
                } else {
                    amtTypeArray = new String[]{amtType};
                }
                for (String s2 : amtTypeArray) {
                    String amt = s2.substring(0, 1);
                    String type = s2.substring(2);
                    if (amt.equalsIgnoreCase("1")) {
                        weapons.put(loc + " (" + type + ")", null);
                    } else {
                        for (int i = 0; i < Integer.parseInt(amt); i++) {
                            weapons.put(loc + " " + (i + 1) + " (" + type + ")", null);
                        }
                    }
                }
            }
        }
        this.maxCrew = Integer.parseInt(frame.getMaxCrew());
        this.minCrew = Integer.parseInt(frame.getMinCrew());
        this.crew = new SFCharacter[this.maxCrew];
        this.maneuver = frame.getManeuver();
        if (frame.getBays().equalsIgnoreCase("")){
            this.bays = new Part[0];
        } else if (frame.getBays().contains("(")){
            String temp = "";
            for (int i = 0; i < frame.getBays().length(); i++) {
                char c = frame.getBays().charAt(i);
                if (c=='(') break;
                temp = temp+c;
            }
            temp = temp.replace(" ","");
            this.bays = new Part[Integer.parseInt(temp)];
        } else {

            this.bays = new Part[Integer.parseInt(frame.getBays())];
        }
        this.size = frame.getSize();
        this.frame = frame;
        this.dt = frame.getDt();
        this.shieldTotal = 0;
        this.regenPerMin = 0;
        String turnPMod = frame.getManeuver().substring(frame.getManeuver().indexOf('(')+1,frame.getManeuver().indexOf(')'));
        this.turn = Integer.parseInt(turnPMod.split(",")[1].replace(" turn ",""));
        this.pilotMod = frame.getPilotMod();
    }
    public void addSecurity(Security security){
        securities.add(security);
    }
    public void setShields(Shields shields) {
        this.shields = shields;
        if (!shields.isDeflector()) {
            this.shieldTotal = Integer.parseInt(shields.getTotalSP());
            this.regenPerMin = Integer.parseInt(shields.getRegen().replace("/min.", ""));
        }
    }
}
