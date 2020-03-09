package WorldBuilder;
import dice.Dice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that has the utilities to build Sectors that are 10X10. Also holds the information of the sector.
 *
 * @author Chase
 * @version 1.0
 */
public class Sector {
    /** Gird that holds all the Systems in the Sector*/
    private StarSystem[][] grid;
    /** String that holds the name**/
    private String name;
    /** Position of the sector based on the origin of earth's Sector (0,0,0) **/
    private int x;
    private int y;
    private int z;
    /**Shows if sector is the origin or main sector*/
    boolean origin;

    public enum Population {POPULATED, COLONIES, RESEARCH, NONE}

    /**
     * constructor for the sector, takes an input on how the sector is populated and then builds a random amount of Star Systems
     * max 10 in a 10X10 sector.
     * @param population enum of population
     * @param x int x location
     * @param y int y location
     * @param z int z location
     */
    public Sector(Population population,int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        if (x == 0 && y==0 && z == 0){
            this.origin = true;
        } else {
            this.origin = false;
        }
        grid = new StarSystem[10][10];
        findName();
        int StarSystemCount = Dice.Roller(1,10)+20;
        for (int i = 0; i < StarSystemCount; i++) {
            int row = Dice.Roller(1,10)-1;
            int col = Dice.Roller(1,10)-1;
            boolean placed = false;
            while (!placed) {
                if (grid[row][col] == null) {
                    grid[row][col] = new StarSystem(population,col,row);
                    placed = true;
                } else {
                    row = Dice.Roller(1,10) - 1;
                    col = Dice.Roller(1,10) - 1;
                }
            }
        }
    }

    /**
     * Searches a text file of names and randomly picks one and sets it as the name of the sector.
     * Text file "StarNamesPrefix.txt" needed in the WorldBuilder directory.
     */
    private void findName(){
        File file = new File("WorldBuilder/StarNamesPrefix.txt");
        try {
            Scanner in = new Scanner(file);
            int roll= Dice.Roller(1,1000);
            int i = 1;
            String line = in.nextLine();
            while (in.hasNext()){
                if (i==roll){
                    this.name = line.replace(i+" ","");
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
     * Getter that returns the Sector's name.
     * @return String of the Sector's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that returns the grid array that holds all the Sector's Systems
     * @return StarSystem[][] grid
     */
    public StarSystem[][] getGrid() {
        return grid;
    }

    /**
     * Getter that returns the coordinates of the Sector
     * @return int[] x,y,z
     */
    public int[] getCoordinates(){
        return new int[]{x,y,z};
    }
}
