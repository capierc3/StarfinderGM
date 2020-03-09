package Ships;

public class CrewQuarter extends Part {

    public CrewQuarter(String line){
        super(line);
        String[] split = line.split("\\?");
        pcu = "-";
        partType = "Crew Quarter";
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nCost: "+cost;
    }
}
