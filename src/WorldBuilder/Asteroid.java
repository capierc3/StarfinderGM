package WorldBuilder;

import dice.Dice;

/**
 * Class that creates and holds information about the Body type Asteroid and Comet.
 * @author Chase Pierce
 * @version 1.0
 */
public class Asteroid extends Body{

    Asteroid(){}

    /**
     * Constructor that sets the type and name of the Asteroid/Comet and fills in the needed information.
     * @param type String
     * @param name String
     */
    public Asteroid(String type,String name){
        this.type = type;
        this.name = name+"'s "+type;
        systemName = name;
        if (type.equalsIgnoreCase("Comet")){ findCometSize();}
        else if (type.equalsIgnoreCase("Belt")){ findBeltSize();}
        else {findAstSize();}
    }

    /**
     *Sets the "Size" descriptions if the type is a Belt. String == "distance of belt,Density of belt,resource quality"
     */
    private void findBeltSize(){
        int roll = Dice.Roller(1, 3);
        if (roll <2){
            size = "Thin";
        } else if (roll <3){
            size = "Average";
        } else {
            size = "Thick";
        }
        roll = Dice.Roller(1,3);
        if (roll <2){
            size = size+", spread out,";
        } else if (roll <3){
            size = size+", Average,";
        } else {
            size = size+" Dense,";
        }
        roll = Dice.Roller(1,3);
        if (roll <2){
            size = size+", Poor";
        } else if (roll <3){
            size = size+", Average";
        } else {
            size = size+" Rich";
        }
    }

    /**
     * If its a comet it finds it's size, and sets the Body class size var.
     */
    private void findCometSize(){
        int sizeInt = Dice.Roller(1,5);
        if (sizeInt <= 1){
            size = "Small nucleus";
            radius = Dice.Roller(4,3850);
        } else if (sizeInt == 2){
            size = "Medium nucleus";
            radius = Dice.Roller(4,3850)+3858;
        } else if (sizeInt == 3){
            size = "Average nucleus";
            radius = Dice.Roller(4,3850)+7700;
        } else if(sizeInt == 4){
            size = "Large nucleus";
            radius = Dice.Roller(4,3850)+11550;
        } else  {
            size = "Huge nucleus";
            radius = Dice.Roller(4,3850)+(15400);
        }
        radius = radius + 750;
    }
    private String findCometCloud(){
        return "TBD";
    }

    /**
     * If its an asteroid it finds it's size, and sets the Body class size var.
     */
    private void findAstSize(){
        int sizeInt = Dice.Roller(1,7);
        if (sizeInt <= 1){
            size = "Small";
            //circumference = Dice.Roller(4,10);
        } else if (sizeInt <= 2){
            size = "Average";
            //circumference = Dice.Roller(10,10);
        } else if (sizeInt <= 3){
            size = "Large";
            //circumference = Dice.Roller(10,10)*2;
        } else if (sizeInt <= 4){
            size = "Huge";
            //circumference = Dice.Roller(10,10)*3;
        } else if (sizeInt <= 5){
            size = "Enormous";
            //circumference = Dice.Roller(10,10)*4;
        } else if (sizeInt <=6){
            size = "Massive";
            //circumference = Dice.Roller(10,10)*5;
        } else {
            size = "tiny";
        }
    }

    @Override
    public String toString() {
        String cloud = findCometCloud();
        return super.toString()+
                "\tRadius of Nucleus: "+radius+
                "\tCloud Size: "+cloud;
    }

    @Override
    public String getTableNames() {
        return tableName;
    }

    @Override
    public String[] getKeys() {
        return keys;
    }
}
