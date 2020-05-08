package ships;

public class Sensor extends Part {

    private String range;
    private String mod;

    public Sensor(String line){
        super(line);
        String[] split = line.split("\\?");
        range = split[1];
        mod = split[2];
        pcu = "-";
        partType = "Sensor";
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nRange: "+range+
                "\nModifier: "+mod+
                "\nCost: "+cost;
    }
}
