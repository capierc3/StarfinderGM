package WorldBuilder;

import dice.Dice;

/**
 * Abstract class that holds all shared variables and methods for the different types of Bodies.
 * @author Chase Pierce
 * @version 1.0
 */
public abstract class Body implements Comparable,GalaxyDataBaseItem{

    public final String tableName = "Bodies";
    public final String[] keys = {"Name","Type","Size","Radius","Gravity","Temp","Orbit_Length","location","dist_from_sun","System_Name"};

    /**Body string Information*/
    protected String type;
    protected String name;
    protected String size;
    String atmosphere;
    String density;
    /**Body numeric information*/
    long circumference;
    long radius;
    protected double gravity;
    double temp;
    double orbitLength;
    int location;
    /**Distance from sun is in AUs*/
    Double distanceSun;
    public String systemName;

    /**Main Constructor that sets the orbit length of the body*/
    Body(){
        int roll = Dice.Roller(1,10);
        orbitLength = (Dice.Roller(10,10)*roll)/365.0;
    }

    /**
     * Setter for the temperature of the body
     * @param temp double
     */
    public void setTemp(double temp){
        this.temp=temp;
    }

    /**
     * Sets the location tag and distance to the sun value
     * @param loc int
     * @param distSun double
     */
    public void setLocation(int loc, double distSun){
        location = loc;
        distanceSun = distSun;
    }

    /**
     *Changes the name of the body by adding the value of its location after the name.
     * @param i int
     */
    public void setName(int i){
        this.name = name+" "+(i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null);
    }

    /**
     * Getter that returns the bodies name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getting that returns the bodies type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * getter that returns the distance to the sun value.
     * @return Double
     */
    public Double getDistanceSun() {
        return distanceSun;
    }

    public double getTemp() {
        return temp;
    }

    /**
     * Compares the Bodies by the distance form sun value
     * @param o Body
     * @return int
     */
    @Override
    public int compareTo(Object o) {
        try {
            Body b = (Body) o;
            //System.out.println(this.name+": "+this.type);
            return this.distanceSun.compareTo(b.distanceSun);
        } catch (ClassCastException e){
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Name: "+name+
                "\nSize: "+size+
                "\nType: "+type+
                "\nLocation: "+location+
                "\nDistance to Sun: "+(distanceSun*498.66)+"ls"+
                "\n                 "+String.format("%.2f",distanceSun)+"AU"+
                "\n                 "+(String.format("%.2f",SpaceTravel.TimeTo(distanceSun,1, SpaceTravel.distUnits.AU, SpaceTravel.timeUnits.Hours,false)))+" hours at 1g"+
                "\n                 "+(String.format("%.2f",SpaceTravel.TimeTo(distanceSun,1, SpaceTravel.distUnits.AU, SpaceTravel.timeUnits.Days,false)))+" days at 1g"+
                "\nTemperature: "+temp+"\u00B0" + "F"+
        "\nOrbit Length: "+String.format("%.2f",orbitLength)+" earth years";
    }

    @Override
    public String getSQLInsert() {
        return " INSERT INTO Bodies" + "(Name,Type,Size,Radius,Gravity,Temp,Orbit_Length,location,dist_from_sun,System_Name)" +
                "VALUES ('" + name.replace("'","?") + "','" + type + "','" + size + "','" + radius + "','" + gravity + "','" + temp + "','" + orbitLength + "','"
                + location + "','" + distanceSun +  "','" + systemName +"');";
    }

    public void readSQL(String[] values){
        name = values[0];
        type = values[1];
        size = values[2];
        radius = Long.parseLong(values[3]);
        gravity = Double.parseDouble(values[4]);
        temp = Double.parseDouble(values[5]);
        orbitLength = Double.parseDouble(values[6]);
        location = Integer.parseInt(values[7]);
        distanceSun = Double.parseDouble(values[8]);
        systemName = values[9];
    }
}
