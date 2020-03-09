package Ships;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class that stores all the parts needed and used by the spaceship objects.
 * Also stores the shipbuilder tools.
 */

public class ShipYard {
    //main part arrays
    private ArrayList<ShipTier> tiers;
    private ArrayList<ShipScales> scales;
    private ArrayList<ShipFrame> frames;
    private ArrayList<ExpansionBay> bays;
    private ArrayList<PowerCore> powerCores;
    private ArrayList<Sensor> sensors;
    private ArrayList<Shields> shields;
    private ArrayList<ShipComputer> computers;
    private ArrayList<ShipWeapon> weapons;
    private ArrayList<Thruster> thrusters;
    private ArrayList<CrewQuarter> quarters;
    private ArrayList<CounterMeasures> counterMeasures;
    private ArrayList<Drift> drifts;
    private ArrayList<Security> securities;
    private ArrayList<Armor> armors;
    private ArrayList<Makes> makes;
    //shipbuilder vars
    private Integer buildPoints;
    private Integer pcuLeft;
    private ArrayList<Part> cart;
    private StarShip ship;
    //fill vars
    private Scanner in;
    private String line;

    /**
     * constructor that fills the part arrays by reading the text file of information and parsing it into
     * parts. Uses the fillYard method after setting the text file.
     */
    public ShipYard(){
        try {
            in = new Scanner(new File("txtFiles/Ships.txt"));
            fillYard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Shipbuilder methods//
    public void newBuild(StarShip ship){
        this.ship = ship;
        buildPoints = Integer.parseInt(ship.getTier().getBuildPoints());
        pcuLeft = 0;
        cart = new ArrayList<>();
    }
    private void addPartBP(Integer bp){
        if (bp<0){
            bp = ship.getSizeMod()*(-1*bp);
        }
        buildPoints = buildPoints-bp;
    }
    private void addPartPCU(Integer pcu){
        pcuLeft = pcuLeft-pcu;
    }
    private void addPart(Integer bp,Integer pcu){
        addPartBP(bp);
        addPartPCU(pcu);
    }
    public void buyPart(Part part){
        if (part instanceof PowerCore){
            pcuLeft = part.getPcuInt();
            buildPoints = buildPoints - part.getCostInt();
            for (Part p : cart) {
                pcuLeft = pcuLeft - p.getPcuInt();
            }
            cart.add(part);
        } else {
            if (!part.getName().equalsIgnoreCase("none")) {
                if (part.getCost() == null) {
                    addPartPCU(part.getPcuInt());
                } else if (part.getPcu() == null) {
                    addPartBP(part.getCostInt());
                } else {
                    addPart(part.getCostInt(), part.getPcuInt());
                    cart.add(part);
                }
            }
        }
    }
    public void returnPart(Part part){
        if (part instanceof PowerCore){
            pcuLeft = 0;
            buildPoints = buildPoints + part.getCostInt();
            cart.remove(part);
        } else {
            if (part.getPcu() != null) {
                pcuLeft = pcuLeft + part.getPcuInt();
            }
            if (part.getCost() != null) {
                int bp = part.getCostInt();
                if (bp < 0) {
                    bp = ship.getSizeMod() * (-1 * bp);
                }
                buildPoints = buildPoints + bp;
            }
            cart.remove(part);
        }
    }

    //Shipbuilder getters
    public Integer getBuildPoints(){return buildPoints;}
    public Integer getPcuLeft() {
        return pcuLeft;
    }
    public ArrayList<Part> getCart() {
        return cart;
    }
    public ShipTier getTier(double level){
        if (level>=1){return tiers.get((int)level+2);}
        else if (level == 1/4) {return tiers.get(0);}
        else if (level == 1/3){ return tiers.get(1);}
        else return tiers.get(2);
    }
    public ArrayList<PowerCore> getCoresBySize(String size){
        char sizeC = size.charAt(0);
        ArrayList<PowerCore> parts = new ArrayList<>();
        for (int i = 0; i < powerCores.size(); i++) {
            if (powerCores.get(i).getSize().contains(Character.toString(sizeC))){
                parts.add(powerCores.get(i));
            }
        }
        parts.sort(Part.compareCost());
        return parts;
    }
    public ArrayList<Thruster> getThrustersBySize(String size){
        char sizeC = size.charAt(0);
        ArrayList<Thruster> partList = new ArrayList<>();
        for (Thruster t : thrusters) {
            if (t.getSize().equalsIgnoreCase(Character.toString(sizeC))){
                partList.add(t);
            }
        }
        return partList;
    }
    public ArrayList<ShipWeapon> getWeaponsByType(String type){
        ArrayList<ShipWeapon> weaponType = new ArrayList<>();
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i).getType().contains(type)){
                weaponType.add(weapons.get(i));
            }
        }
        return weaponType;
    }

    //Constructor Methods//
    private void fillYard(){
        while (in.hasNext()){
            nextLine();
            if (isTableNum(3)){
                inTiers();
            } else if (isTableNum(4)){
                inScale();
            } else if (isTableNum(5)){
                inPowerCore();
            } else if (isTableNum(6)){
                inThruster();
            } else if (isTableNum(7)){
                inComputer();
            } else if (isTableNum(8)){
                inCrew();
            } else if (isTableNum(9)){
                inDCM();
            } else if (isTableNum(10)){
                inDrift();
            } else if (isTableNum(11)){
                inExpansion();
            } else if (isTableNum(12)){
                inSelfDestruct();
            } else if (isTableNum(13)){
                inSensor();
            } else if (isTableNum(14)){
                inShields();
            } else if (isTableNum(15)){
                inWeapon("Direct-Fire,Light");
            } else if (isTableNum(16)){
                inWeapon("Tracking,Light");
            } else if (isTableNum(17)){
                inWeapon("Direct-Fire,Heavy");
            } else if (isTableNum(18)){
                inWeapon("Tracking,Heavy");
            } else if (isTableNum(19)){
                inWeapon("Direct-Fire,Capital");
            } else if (isTableNum(20)){
                inWeapon("Tracking,Capital");
            }
        }
        fillFrames();
        fillArmors();
        fillMakes();
    }
    private void inTiers(){
        tiers = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            tiers.add(new ShipTier(line));
            nextLine();
        }
    }
    private void inScale(){
        scales = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            scales.add(new ShipScales(line));
            nextLine();
        }
    }
    private void inPowerCore(){
        powerCores = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            powerCores.add(new PowerCore(line));
            nextLine();
        }
    }
    private void inThruster(){
        thrusters = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            thrusters.add(new Thruster(line));
            nextLine();
        }
    }
    private void inComputer(){
        computers = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            computers.add(new ShipComputer(line));
            nextLine();
        }
    }
    private void inCrew(){
        quarters = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            quarters.add(new CrewQuarter(line));
            nextLine();
        }
    }
    private void inDCM(){
        counterMeasures = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            counterMeasures.add(new CounterMeasures(line));
            nextLine();
        }
    }
    private void inDrift(){
        drifts = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            drifts.add(new Drift(line));
            nextLine();
        }
    }
    private void inExpansion(){
        bays = new ArrayList<>();
        ArrayList<String> desc = bayDescriptions();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            ExpansionBay bay = new ExpansionBay(line);
            for (int i = 0; i < desc.size(); i++) {
                String name = bay.name;
                if (name.contains("(")){
                    name = name.substring(0,name.indexOf('(')-1);
                }
                if (name.equalsIgnoreCase(desc.get(i).split("<>")[0])){
                    bay.setDescription(desc.get(i).split("<>")[1]);
                }
            }
            bays.add(bay);
            nextLine();
        }
    }
    private void inSelfDestruct(){
        securities = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            securities.add(new Security(line));
            nextLine();
        }
    }
    private void inSensor(){
        sensors = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            sensors.add(new Sensor(line));
            nextLine();
        }
    }
    private void inShields(){
        shields = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            shields.add(new Shields(line));
            nextLine();
        }
    }
    private void inWeapon(String type){
        if (weapons==null) weapons = new ArrayList<>();
        nextLine();
        while (!line.equalsIgnoreCase("")){
            weapons.add(new ShipWeapon(type,line));
            nextLine();
        }
    }
    private void fillFrames(){
        try {
            frames = new ArrayList<>();
            in = new Scanner(new File("txtFiles/frames.txt"));
            while (in.hasNext()) {
                nextLine();
                ArrayList<String> frame = new ArrayList<>();
                while (!line.equalsIgnoreCase("")){
                    frame.add(line);
                    nextLine();
                }
                frames.add(new ShipFrame(frame));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean isTableNum(int num){
        return line.equalsIgnoreCase(Integer.toString(num));
    }
    private void nextLine(){
        line = in.nextLine();
    }
    private void fillArmors(){
        armors = new ArrayList<>();
        String[] armorsStrings = {
                "Mk 1armor?+1?—?1 × size category",
        "Mk 2 armor?+2?—?2 × size category",
        "Mk 3 armor?+3?—?3 × size category",
        "Mk 4 armor?+4?—?5 × size category",
        "Mk 5 armor?+5?–1 TL?7 × size category",
        "Mk 6 armor?+6?–?1 TL?9 × size category",
        "Mk 7 armor?+7?–?1 TL?12 × size category",
        "Mk 8 armor?+8?–?1 TL?15 × size category",
        "Mk 9 armor?+9?–2 TL, +1 turn distance?18 × size category",
        "Mk 10 armor?+10?–2 TL, +1 turn distance ?21 × size category",
        "Mk 11 armor?+11?–2 TL, +1 turn distance ?25 × size category",
        "Mk 12 armor?+12?–3 TL, +2 turn distance ?30 × size category",
        "Mk 13 armor?+13?–3 TL, +2 turn distance ?35 × size category",
        "Mk 14 armor?+14?–3 TL, +2 turn distance ?40 × size category",
        "Mk 15 armor?+15?–4 TL, +3 turn distance ?45 × size category"};
        for (String s:armorsStrings) {
            armors.add(new Armor(s));
        }
    }
    private ArrayList bayDescriptions() {
        ArrayList<String> desc = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("txtFiles/ship2.txt"));
            while (scanner.hasNext()){
                desc.add(scanner.nextLine()+"<>"+scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return desc;
    }
    private void fillMakes(){
        makes = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File("txtFiles/makes.txt"));
            while (scan.hasNext()){
                makes.add(new Makes(scan.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Basic getters//
    public ArrayList<ShipTier> getTiers() {
        return tiers;
    }
    public ArrayList<ShipScales> getScales() {
        return scales;
    }
    public ArrayList<ShipFrame> getFrames() {
        return frames;
    }
    public ArrayList<ExpansionBay> getBays() {
        return bays;
    }
    public ArrayList<PowerCore> getPowerCores() {
        return powerCores;
    }
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
    public ArrayList<Shields> getShields() {
        return shields;
    }
    public ArrayList<ShipComputer> getComputers() {
        return computers;
    }
    public ArrayList<ShipWeapon> getWeapons() {
        return weapons;
    }
    public ArrayList<Thruster> getThrusters() {
        return thrusters;
    }
    public ArrayList<CrewQuarter> getQuarters() {
        return quarters;
    }
    public ArrayList<CounterMeasures> getCounterMeasures() {
        return counterMeasures;
    }
    public ArrayList<Drift> getDrifts() {
        return drifts;
    }
    public ArrayList<Security> getSecurities() {
        return securities;
    }
    public ArrayList<Armor> getArmors() {
        return armors;
    }
    public ArrayList<Makes> getMakes() {
        return makes;
    }
}