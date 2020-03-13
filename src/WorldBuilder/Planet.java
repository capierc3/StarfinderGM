package WorldBuilder;

import dice.Dice;

/**
 * The class that builds and stores the information of Planets. It extends the base class of Body
 * @author Chase Pierce
 * @version 1.0
 */
public class Planet extends Body {

    /**SQL information*/
    public static final String tableName = "Planets";
    public final String[] keys = {"Name","Type","Size","Radius","Temp","Orbit_Length","location","dist_from_sun","Atmosphere","Density"
            ,"Gravity","Circumference","Tilt","Rotation","Liquid","Orbiting_Bodies","System_Name"};

    /**the int value for the size of the planet for the size String*/
    private int planetSize;
    /**Planet density index*/
    private int DR;
    /**Planet tilt for seasons*/
    private String axilTilt;
    /**Degree of Planet tilt*/
    private int tiltDegree;
    /**Plant rotation in degrees*/
    private int rotation;
    /**retrograde spin or prograde*/
    private String rotationDir;
    /**Percent value of the amount of liquid on Planet*/
    private int liquidAmt;
    /**Type of liquid found on planet*/
    private String liquidType;
    /**Array of all the bodies found in the orbit of planet*/
    private String[] orbitingBodies;
    /**Thickness of the atmosphere*/
    private String atmoThickness;
    /**Atmosphere pressure rating (APR) of planet*/
    private double atmoRating;
    private String systemName;

    Planet(){

    }
    /**
     * Constructor that takes the type and name of the planet and creates the specs for the planet
     * @param type String
     * @param name String
     */
    public Planet(String type,String name){
        this.type = type;
        this.name = name;
        systemName = name;
        findSize();
        findAtmo();
        findDR();
        findAP();
        findTiltRotate();
        if (!type.equalsIgnoreCase("Gas Planet")){
            findLiquid();
        }
        findMoons();

    }

    //TODO moons and space stations
    /**
     * randomly creates the amount of things in orbit of the planet. Then creates String names of those objects.
     */
    private void findMoons(){
        int roll = Dice.Roller(1,20);
        if (roll<=10){
            orbitingBodies = new String[0];
        } else if (roll<=15){
            orbitingBodies = new String[1];
        } else if (roll<20){
            orbitingBodies = new String[roll-14];
        } else orbitingBodies = new String[Dice.Roller(2,4)];
        for (int i = 0; i < orbitingBodies.length; i++) {
            roll = Dice.Roller(1,20);
            if (roll<=2){
                orbitingBodies[i] = "Dust Cloud";
            } else if (roll==3){
                orbitingBodies[i] = "Gas Cloud";
            } else if (roll<=5){
                orbitingBodies[i] = "Natural Debris";
            } else if (roll<=7){
                orbitingBodies[i] = "Artificial Debris";
            } else if (roll<=16){
                orbitingBodies[i] = "Moon";
            } else if (roll<=18){
                int ringType = Dice.Roller(1,3)-1;
                String[] ringTypes = {"Ice","Rock","Ice/Rock Mix"};
                orbitingBodies[i] = "Ring of "+ringTypes[ringType];
            } else {
                orbitingBodies[i] = "Artificial Construction";
            }
        }
    }

    /**Randomly finds the percentage of the planet that has liquid. Then randomly assigns the type of liquid, if
     * earth like it picks H2O.
     */
    private void findLiquid(){
        int roll = Dice.Roller(1,12);
        if (roll==1) {
            liquidAmt = 0;
        } else if (roll==2){
            liquidAmt = Dice.Roller(1,5);
        } else if (roll<=11){
            int mod = ((roll-3)*10)+5;
            liquidAmt = Dice.Roller(1,10)+mod;
        } else {
            liquidAmt = Dice.Roller(1,5)+95;
        }
        if (atmosphere.equalsIgnoreCase("Earth-like/Standard")){
            liquidType = "H2O";
        } else {
            String[] types = {"H20","Ammonia","Bromine","Caesium","Francium","Gallium","Liquid Nitrogen",
                    "Liquid Oxygen","Mercury","Rubidium"};
            roll = Dice.Roller(1,20);
            if (roll<=11){
                liquidType = types[0];
            } else {
                liquidType = types[roll-11];
            }
        }
    }

    /**
     * Randomly finds the tilt string description then finds a random tilt amount that represents that string in degrees
     */
    private void findTiltRotate(){
        //Tilt
        int roll = Dice.Roller(1,10);
        String[] tilts = {"None","Slight","Minor","Notable","Moderate","Large","Great","Severe","Huge","Extreme"};
        axilTilt = tilts[roll-1];
        tiltDegree = Dice.Roller(1,10)+(roll-2)*10;
        if (roll == 1) tiltDegree = 0;
        //Rotation(hrs per day)
        int rotationMod = planetSize+(Dice.Roller(1,10)-1);
        if (rotationMod<3){
            if (rotationMod==1){
                rotation = Dice.Roller(1,4);
            } else rotation = Dice.Roller(1,8);
        } else if (rotationMod<5){
            rotation = Dice.Roller(rotationMod-2,10);
        } else if (rotationMod<12){
            rotation = Dice.Roller(rotationMod-1,10);
        } else rotation = Dice.Roller(rotationMod,10);
        //Rotation Direction
        roll = Dice.Roller(1,100);
        if (roll<=70){
            rotationDir = "Prograde";
        } else rotationDir = "Retrograde";
    }
    /**Finds the density and gravity of the planet and sets them*/
    private void findDR() {
        DR = planetSize + Dice.Roller(1,10) - 1;
        String[][] drs = {
                {"Negligible", "0"},
                {"Very Low", "0.01 -> 0.04(1d4)"},
                {"Low", "0.05 -> 0.10(1d6+4)"},
                {"Light", "0.2 -> 0.4(1d3)"},
                {"Below Average", "0.5 -> 0.7 (1d3)"},
                {"Average", "0.8 -> 1.2(1d5)"},
                {"Above Average", "1.3 -> 1.7(1d5)"},
                {"Heavy", "1.8 -> 2.0(1d3)"},
                {"Very Heavy", "2.1 -> 2.5(1d4)"},
                {"Massive", "2.6 -> 2.7(1d2)"},
                {"Enormous", "2.8 -> 3.0(1d3)"},
                {"Extreme", "3.0 + 0.1"}
        };
        if (DR<12) {
            density = drs[DR-1][0];
        } else {
            density = drs[drs.length-1][0];
        }
        if (DR==1){
            gravity = 0;
        } else if (DR==2){
            gravity = Dice.Roller(1,4)/100.0;
        } else if (DR==3){
            gravity = (Dice.Roller(1,6)+4.0)/100;
        } else if (DR==4){
            gravity = (Dice.Roller(1,3)/10.0)+.1;
        } else if (DR==5){
            gravity = (Dice.Roller(1,3)/10.0)+.4;
        } else if (DR==6){
            gravity = (Dice.Roller(1,5)/10.0)+.7;
        } else if (DR==7){
            gravity = (Dice.Roller(1,5)/10.0)+1.2;
        } else if (DR==8){
            gravity = (Dice.Roller(1,3)/10.0)+1.7;
        } else if (DR==9){
            gravity = (Dice.Roller(1,4)/10.0)+2.0;
        } else if (DR==10){
            gravity = (Dice.Roller(1,2)/10.0)+2.5;
        } else if (DR==11){
            gravity = (Dice.Roller(1,3)/10.0)+2.7;
        } else {
            gravity = 3.0+(.1*planetSize);
        }
    }
    /**Finds and sets the atmospheric pressure of the planet*/
    private void findAP(){
        double AP = (Dice.Roller(1, 10) - 3) + (planetSize * .5) + (.5 * DR);
        String[][] thickness = {
                {"Negligible/None", "0"},
                {"Trace", "1"},
                {"Light", "2"},
                {"Thin", "4"},
                {"Thinner","6"},
                {"Below Standard", "8"},
                {"Standard", "10"},
                {"High", "20"},
                {"Thick", "40"},
                {"Slightly Dense", "80"},
                {"Dense", "100"},
                {"Very Dense", "200"},
                {"Super Dense", "400"},
                {"Ultra Dense", "40 points per AP"}
        };
        if (Math.floor(AP)>=thickness.length-1){
            atmoThickness = thickness[thickness.length-1][0];
            atmoRating = AP *40;
        } else {
            if ((int) AP<0) AP = 0;
            atmoThickness = thickness[(int) AP][0];
            atmoRating = Integer.parseInt(thickness[(int) AP][1]);
        }
    }
    /**Finds the type of Atmosphere the plant has and sets it*/
    private void findAtmo(){
        Dice d12 = new Dice(12);
        String[] types = {
                "Ammonia/Toxic",
                "Argon/Inert",
                "Carbon Dioxide/Greenhouse",
                "Chlorine/Corrosive",
                "Earth-like/Standard",
                "Helium/Inert",
                "Hydrogen/Combustive",
                "Icy/Cold",
                "Methane/Toxic",
                "Nitrogen/Suffocating",
                "Oxygen/Combustive",
                "Sulphur/Volcanic"};
        this.atmosphere = types[d12.Roll()-1];
    }
    /**Finds and sets the String size description and Double value of its circumference.*/
    private void findSize(){
        planetSize = Dice.Roller(1,10);
        if (type.equalsIgnoreCase("Dwarf Planet")) planetSize = Dice.Roller(1,3);
        if (planetSize <= 1){
            size = "Minuscule";
            circumference = Dice.Roller(1,10)+4;
        } else if (planetSize <= 2){
            size = "Tiny";
            circumference = Dice.Roller(2,10);
        } else if (planetSize <= 3){
            size = "Small";
            circumference = Dice.Roller(4,10);
        } else if (planetSize <= 6){
            size = "Average";
            circumference = Dice.Roller(10,10);
        } else if (planetSize <= 7){
            size = "Large";
            circumference = Dice.Roller(10,10)*2;
        } else if (planetSize <= 8){
            size = "Huge";
            circumference = Dice.Roller(10,10)*3;
        } else if (planetSize <= 9){
            size = "Enormous";
            circumference = Dice.Roller(10,10)*4;
        } else {
            size = "Massive";
            circumference = Dice.Roller(10,10)*5;
        }
    }

    /**
     * Creates a string with string builder that makes up the description of the orbiting bodies of the planet
     * @return String
     */
    private String printOrbit(){
        StringBuilder sb = new StringBuilder();
        for (String s:orbitingBodies) {
            sb.append("\t").append(s).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return super.toString()+
                "\nAtmosphere: "+atmosphere+
                "\nAtmospheric Pressure: "+atmoThickness+"("+atmoRating+"APR)"+
                "\nPlanet Density: "+ density+
                "\nGravity: "+String.format("%.2f",gravity)+"g"+
                "\nCircumference: "+circumference+",000 km"+
                "\nTilt: "+axilTilt+"("+tiltDegree+"*)"+
                "\nRotation: "+rotation+" hr/day "+rotationDir+
                "\nLiquid: "+liquidType+" ("+liquidAmt+"%)"+
                "\nOrbiting Bodies: "+orbitingBodies.length+
                "\n"+printOrbit();
    }

    @Override
    public String getTableNames() {
        return tableName;
    }

    @Override
    public String[] getKeys() {
        return keys;
    }

    @Override
    public String getSQLInsert() {
        return " INSERT INTO Planets" + "(Name,Type,Size,Radius,Temp,Orbit_Length,location,dist_from_sun,Atmosphere,Density" +
                ",Gravity,Circumference,Tilt,Rotation,Liquid,Orbiting_Bodies,System_Name)" +
                "VALUES ('" + name + "','" + type + "','" + size + "','" + radius + "','" + temp + "','" + orbitLength + "','" + location + "','" +
                distanceSun + "','" +atmoThickness+"("+atmoRating+"APR)" + "','" + density + "','"+ gravity + "','" + circumference + "','"
                + axilTilt+"("+tiltDegree+"*)" + "','" + rotation+" hr/day "+rotationDir+ "','" + liquidType+" ("+liquidAmt+"%)" + "','" +orbitingBodies.length +"','" +systemName + "');";
    }

    public void readSQL(String[] values){
        name = values[0];
        type = values[1];
        size = values[2];
        radius = Long.parseLong(values[3]);
        temp = Double.parseDouble(values[4]);
        orbitLength = Double.parseDouble(values[5]);
        location = Integer.parseInt(values[6]);
        distanceSun = Double.parseDouble(values[7]);
        atmoThickness = values[8].split("\\(")[0];
        atmoRating = Double.parseDouble(values[8].split("\\(")[1].replace("APR)",""));
        density = values[9];
        gravity = Double.parseDouble(values[10]);
        circumference = Long.parseLong(values[11]);
        axilTilt = values[12].split("\\(")[0];
        tiltDegree = Integer.parseInt(values[12].split("\\(")[1].replace("*)",""));
        rotation = Integer.parseInt(values[13].split(" hr/day ")[0]);
        rotationDir = values[13].split(" hr/day ")[1];
        liquidType = values[14].split(" \\(")[0];
        liquidAmt = Integer.parseInt(values[14].split(" \\(")[1].replace("%)",""));
        //find orbiting bodies//
        //values[15]
        systemName = values[16];

    }
}
