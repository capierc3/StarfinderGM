package ships;

public class CrewQuarter extends Part {

    public CrewQuarter(String line){

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
