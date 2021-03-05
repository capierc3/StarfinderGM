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
    private Integer ac;
    private Integer tl;
    private Integer hp;
    private Integer dt;
    private Integer ct;
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
        return ac;
    }
    public Integer getTl() {
        return tl;
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
            case "Tiny":
                return 1;
            case "Small":
                return 2;
            case "Medium":
                return 3;
            case "Large":
                return 4;
            case "Huge":
                return 5;
            case "Gargantuan":
                return 6;
            case "Colossal":
                return 7;
        }
        return 0;
    }
    public int getSizeACTL(){
        switch (size){
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
            case "Colossal":
                return 8;
        }
        return 0;
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

    public void setComputer(ShipComputer computer) {
        this.computer = computer;
    }
    public void setCounterMeasures(CounterMeasures counterMeasures) {
        this.counterMeasures = counterMeasures;
        if (!counterMeasures.getBonus().equalsIgnoreCase("-")) {
            this.tl = tl + Integer.parseInt(counterMeasures.getBonus().replace("+", ""));
        }
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
        if (armor.getBonusAc().contains("+")){
            this.ac = ac+Integer.parseInt(armor.getBonusAc().replace("+",""));
        }
        if (armor.getSpecial().contains(",")){
            String tlBonus = armor.getSpecial().split(",")[0].replace("TL","");
            this.tl = tl - Integer.parseInt(tlBonus.replace("–","").replace(" ",""));
        }
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
        this.hp = frame.getHp();
        this.dt = frame.getDt();
        this.ct = frame.getCt();
        this.ac = 10 + getSizeACTL();
        this.tl = 10 + getSizeACTL();
        this.shieldTotal = 0;
        this.regenPerMin = 0;
        if (tier.getSpecial().equalsIgnoreCase("HP increase")){
            this.hp = hp + frame.getHpInc();
        }
        String turnPMod = frame.getManeuver().substring(frame.getManeuver().indexOf('(')+1,frame.getManeuver().indexOf(')'));
        this.turn = Integer.parseInt(turnPMod.split(",")[1].replace(" turn ",""));
        this.pilotMod = frame.getPilotMod();
    }
    public void addSecurity(Security security){
        securities.add(security);
    }
    public void setShields(Shields shields) {
        this.shields = shields;
        this.shieldTotal = Integer.parseInt(shields.getTotalSP());
        this.regenPerMin = Integer.parseInt(shields.getRegen().replace("/min.",""));
    }
}
