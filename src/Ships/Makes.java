package Ships;

import java.util.ArrayList;

public class Makes {

    private String name;
    private String description;
    private String bonus;
    private ArrayList<ShipFrame> frames;

    public Makes(String line){
        String[] split = line.split("\\?");
        name = split[0];
        description = split[1];
        bonus = split[2];
    }
    private void findFrames(String s){
        frames = new ArrayList<>();
        String[] frameStrings = s.split(",");
        for (String frameString : frameStrings) {
            System.out.println(s);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getBonus() {
        return bonus;
    }
    public ArrayList<ShipFrame> getFrames() {
        return frames;
    }
}
