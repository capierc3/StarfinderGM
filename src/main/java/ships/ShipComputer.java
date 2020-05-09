package ships;

public class ShipComputer extends Part {

    private String bonus;
    private String nodes;

    public ShipComputer(String line){

        String[] split = line.split("\\?");
        bonus = split[1];
        nodes =split[2];
        partType = "Computer";
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nBonus: "+bonus+
                "\nNodes: "+nodes+
                toStringBTM();
    }
}


