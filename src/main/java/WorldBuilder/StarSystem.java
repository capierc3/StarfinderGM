package WorldBuilder;

import dice.Dice;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

/**
 * Class that has the utilities to create a StarSystem and holds the created systems information
 *
 * @author Chase Pierce
 * @version 1.0
 */
public class StarSystem implements GalaxyDataBaseItem{

    public static final String[] keys = {"Name","Sector","X","Y","Z","Stars","Planets","Bodies","Size","Habitable_Zone","Population","ID"};
    public static final String tableName = "Systems";

    /**Name of the system**/
    private String name;
    /**Arrays that hold the systems stars, bodies, and an order Array of the bodies**/
    private Star[] stars;
    private ArrayList<Planet> planets;
    private ArrayList<Body> bodies;
    private ArrayList<Body> orderSystem;
    private ArrayList<StarSystem> nearBySystems;
    /**Size of the system in AUs**/
    private double size;
    /**Information on the habitable zone of the system if it has one.**/
    private double habitLow;
    private double habitHigh;
    /**The Population of the system**/
    private Sector.Population population;
    /**The location in the galaxy**/
    double x,y,z;
    String id;
    private String sector;

    /**
     * Empty Constructor for use with the database creation only
     */
    public StarSystem(){

    }
    /**
     * Constructor that creates the system based on inputted population and location.
     * @param population enum of population.
     * @param x location int for x.
     * @param y location int for y.
     */
    public StarSystem(String sectorName, Sector.Population population, int x, int y,int secX, int secY, int secZ) {
        this.population = population;
        findName();
        int starCount;
        int roll = Dice.Roller(1,20);
        if (roll < 14) {
            starCount = 1;
        } else if (roll < 19) {
            starCount = 2;
        } else if (roll < 20) {
            starCount = 3;
        } else starCount = 4;
        int negX = 1;
        int negY = 1;
        int negZ = 1;
        if (Dice.Roller(1,2)==1) negX = -1;
        if (Dice.Roller(1,2)==1) negY = -1;
        if (Dice.Roller(1,2)==1) negZ = -1;
        this.x = (((Dice.Roller(1,99.00)/100.00)+x)+(10*secX))*(negX);
        this.y = (((Dice.Roller(1,99.00)/100.00)+y)+(10*secY))*(negY);
        this.z = ((Dice.Roller(1,999)/100.0)+(10*secZ))*(negZ);
        createStars(starCount);
        createBodies();
        createSize();
        placeBodies();
        setBodyNames();
        createTemp();
        sector = sectorName;
        id = sectorName.toCharArray()[0]+name.toCharArray()[0]+ x + y + Double.toString(z);
    }

    /**
     * Searches "txtFiles/WorldBuilder/StarNamesSuffix.txt" for a name of the system, picks a random one and sets it.
     */
    private void findName(){
        File file = new File("txtFiles/WorldBuilder/StarNamesSuffix.txt");
        try {
            Scanner in = new Scanner(file);
            int roll= Dice.Roller(1,1000);
            int i = 1;
            String line = in.nextLine();
            while (in.hasNext()){
                if (i==roll){
                    this.name = line.replace(i+" ","");
                    this.name = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
                    break;
                } else {
                    i++;
                    line = in.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets a location tag to name the bodies in the system.
     */
    private void setBodyNames(){
        int i = 1;
        for (Body b:orderSystem) {
            if (b!=null){
                b.setName(i);
                i++;
            }
        }
    }
    /**
     * Creates a random size for the system in AUs based on the amount of bodies in the system
     */
    private void createSize(){
        int sizeMod = Dice.Roller(1,20)+(bodies.size()+planets.size());
        if (sizeMod<7){
            double[] aus = {.25,.33,.5,.66,.75,1};
            size = aus[sizeMod-1];
        } else if (sizeMod<9){
            size = Dice.Roller(sizeMod-6,4);
        } else if (sizeMod<11){
            size = Dice.Roller(sizeMod-7,6);
        } else if (sizeMod==11){
            size = Dice.Roller(2,8);
        } else{
            size = Dice.Roller(sizeMod-10,10);
        }


    }
    /**
     * Creates a random amount of bodies in the system and then randomly creates each body.
     */
    private void createBodies(){
        int bodyNum = Dice.Roller(1,10);
        if (bodyNum == 10) bodyNum = 10+Dice.Roller(1,10);
        bodies = new ArrayList<>();
        planets = new ArrayList<>();
        for (int i = 0; i < bodyNum; i++) {
            int roll = Dice.Roller(1,20);
            if (roll <= 1) {
                bodies.add(new OtherBody("Anomaly",name));
            } else if (roll <=2){
                bodies.add(new OtherBody("Structure/Item",name));
            } else if (roll <=3){
                bodies.add(new Asteroid("Single/Group",name));
            } else if (roll <=6){
                bodies.add(new Asteroid("Belt",name));
            } else if (roll <=7){
                bodies.add(new Asteroid("Comet",name));
            } else if (roll <=8){
                bodies.add(new OtherBody("Dust Cloud",name));
            } else if (roll <=9){
                bodies.add(new OtherBody("Oort Cloud",name));
            } else if (roll <=12){
                planets.add(new Planet("Dwarf Planet",name));
            } else if (roll <=16){
                planets.add(new Planet("Gas Planet",name));
            } else {
                planets.add(new Planet("Terrestrial Planet",name));
            }
        }
    }

    /**
     * Randomly places the bodies in the system based on the habitable zones and types of bodies. Then sets the
     * bodies Distance to Sun variable for the created AU distance.
     */
    private void placeBodies(){
        String[] sTypes = {"M", "K", "G", "F", "A", "B", "O"};
        double[][] goldilocks = {
                {.51, 200.85},
                {107, 480},
                {351, 872.9},
                {607, 1890},
                {1357, 4948},
                {3731, 41133},
                {32565, 99018},
        };
        String sType = "";
        int typeLoc = 0;
        boolean main = false;
        for (Star s : stars) {
            if (s.type.contains("Main Sequence")) {
                main = true;
                for (int i = 0; i < sTypes.length; i++) {
                    sType = s.type.replace("Main Sequence(", "");
                    sType = sType.replace(")", "");
                    if (sType.equalsIgnoreCase(sTypes[i])) {
                        if (typeLoc < i) {
                            typeLoc = i;
                        }
                    }
                }
            }
        }
        ArrayList<String> fullTypes = new ArrayList<>() {{
            add("Anomaly");
            add("Structure/Item");
            add("Dwarf Planet");}};
        ArrayList<String> hotHabitTypes = new ArrayList<>(Arrays.asList("Single/Group","Belt","Terrestrial Planet"));
        ArrayList<Double> placedLocs = new ArrayList<>();
        double lsSize = SpaceTravel.AUtoLS(size);
        if (main){
            habitLow = goldilocks[typeLoc][0];
            habitHigh = goldilocks[typeLoc][1];
        } else {
            habitLow = 0;
            habitHigh = lsSize/2;
        }
        for (Body b:bodies) {
            if (fullTypes.contains(b.type)){
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,0,lsSize));
            } else if (hotHabitTypes.contains(b.type)){
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,0,habitHigh+((habitHigh-habitLow)/2)));
            } else {
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,habitHigh,lsSize));
            }
        }
        for (Body b:planets) {
            if (fullTypes.contains(b.type)){
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,0,lsSize));
            } else if (hotHabitTypes.contains(b.type)){
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,0,habitHigh+((habitHigh-habitLow)/2)));
            } else {
                b.distanceSun = SpaceTravel.LStoAU(placeInSystem(placedLocs,habitHigh,lsSize));
            }
        }
        orderSystem = bodies;
        orderSystem.addAll(planets);
        orderSystem.sort(Body::compareTo);
    }

    /**
     * Recursive method used by placeBodies() for finding a location to place the body.
     * @param locs arrayList of the already placed locations
     * @param low the lowest location that a body can be placed
     * @param high the highest location that a body can be placed
     * @return double of the location to place the body
     */
    private double placeInSystem(ArrayList<Double> locs,double low, double high){
        double dist = Dice.Roller(1,high-low)+low;
        if (!locs.contains(dist)){
            return dist;
        } else {
            return placeInSystem(locs,low,high);
        }
    }

    /**
     * Creates the stars for the system and if more then one it sets the additional stars to not be more then one larger
     * or smaller then the last one.
     * @param starCount amount of stars to create for the system
     */
    private void createStars(int starCount){
        stars = new Star[starCount];
        if (starCount==1){
            stars[0] = new Star(name);
        } else {
            stars[0] = new Star(name);
            for (int i = 1; i < stars.length; i++) {
                if (new Dice(2).Roll() == 1) {
                    stars[i] = new Star(stars[i - 1].getTypeNum(),i+1,name);
                } else {
                    if (new Dice(2).Roll() == 1){
                        stars[i] = new Star(stars[i-1].getTypeNum()-1,i+1,name);
                    } else stars[i] = new Star(stars[i-1].getTypeNum()+1,i+1,name);
                }
            }
        }
    }

    /**
     * Rolls a array of values to be used to find random temperatures for the bodies
     * @return double[] of rolled values.
     */
    private double[] RollTemps(){
        return new double[]{
                (401 + (Dice.Roller(4, 4) * 100)),
                300 + Dice.Roller(1, 100),
                200 + Dice.Roller(1, 100),
                100 + Dice.Roller(1, 100),
                50 + Dice.Roller(1, 50),
                Dice.Roller(1, 50),
                Dice.Roller(1, 50) * -1,
                (Dice.Roller(1, 50) + 50) * -1,
                (100 + Dice.Roller(1, 100)) * -1,
                (200 + Dice.Roller(1, 100)) * -1,
                (300 + Dice.Roller(1, 100)) * -1,
                Dice.Roller(4, 4) * -100,
        };
    }

    /**
     * Creates a random temperature for the system's bodies by locating it in the system then setting it based on the
     * habitable zone.
     */
    private void createTemp() {
        if (habitLow!=0){
            double prevTemp = 2002;
            double newTemp;
            for (Body b:orderSystem) {
                double lsDist = SpaceTravel.AUtoLS(b.distanceSun);
                double range;
                double rangeLow;
                int tableMod;
                if (lsDist<habitLow){
                    range = habitLow/4;
                    rangeLow = 0;
                    tableMod = -1;
                } else if (lsDist<=habitHigh){
                    range = (habitHigh-habitLow)/4;
                    rangeLow = habitLow;
                    tableMod = 3;
                } else {
                    range = (SpaceTravel.AUtoLS(size)-habitHigh)/4;
                    rangeLow = habitHigh;
                    tableMod = 7;
                }
                for (int i = 1; i <= 4; i++) {
                    double[] temps = RollTemps();
                    if (lsDist<=(range*i)+rangeLow) {
                        b.setTemp(temps[i+tableMod]);
                        break;
                    }
                }
            }
        }
    }

    /**
     * getter to return the system's name
     * @return string of the name
     */
    public String getName() {
        return name;
    }

    @Override
    public void readSQL(String[] values) {
       name = values[0];
       sector = values[1];
       x = Double.parseDouble(values[2]);
       y = Double.parseDouble(values[3]);
       z = Double.parseDouble(values[4]);
       //Needs to find its stars
        try {
            stars = GalaxyDataBase.findStar(name,Integer.parseInt(values[5]));
            planets = GalaxyDataBase.findPlanets(name);
            bodies = GalaxyDataBase.findBodies(name);
            orderSystem = bodies;
            orderSystem.addAll(planets);
            orderSystem.sort(Body::compareTo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       size = Double.parseDouble(values[8]);
       //"("+habitLow+"-"+habitHigh + ")"
       String[] hab = values[9].split("\\-");
       habitLow = Double.parseDouble(hab[0].replace("(",""));
       habitHigh = Double.parseDouble(hab[1].replace(")",""));
       if (values[10].equalsIgnoreCase(Sector.Population.POPULATED.toString())){
           population = Sector.Population.POPULATED;
       } else if (values[10].equalsIgnoreCase(Sector.Population.COLONIES.toString())){
           population = Sector.Population.COLONIES;
       } else if (values[10].equalsIgnoreCase(Sector.Population.NONE.toString())){
           population = Sector.Population.NONE;
       } else population = Sector.Population.RESEARCH;
       id = values[11];

    }

    /**
     * getter to return the system's size in AU
     * @return double of the system size
     */
    public double getSize() {
        return size;
    }
    /**
     * getter to return the system's stars
     * @return Star[]
     */
    public Star[] getStars() {
        return stars;
    }
    /**
     * getter to return the system's bodies
     * @return Body array list
     */
    public ArrayList<Body> getBodies() {
        return bodies;
    }

    /**
     * getter to return the system's Planets
     * @return planets arraylist
     */
    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    /**
     * getter to return the system's ordered body array
     * @return ArrayList<Body>
     */
    public ArrayList<Body> getOrderSystem() {
        return orderSystem;
    }

    /**
     * Getter to return the systems habitable zone
     * @return double
     */
    public double[] getHabitZone() {
        return new double[] {habitLow,habitHigh};
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public ArrayList<StarSystem> getNearBySystems() {
        return nearBySystems;
    }

    @Override
    public String getTableNames() {
        return "Systems";
    }

    @Override
    public String[] getKeys() {
        return keys;
    }

    @Override
    public String getSQLInsert() {
        return " INSERT INTO Systems" + "(Name,Sector,X,Y,Z,Stars,Planets,Bodies,Size,Habitable_Zone,Population,ID)" +
                "VALUES ('" + name + "','" + sector + "','" + x + "','" + y + "','" + z + "','" + stars.length + "','" + planets.size() + "','"+ bodies.size() + "','" + size + "','"
                + "("+habitLow+"-"+habitHigh + ")" + "','" + population + "','" + id + "');";
    }
}
