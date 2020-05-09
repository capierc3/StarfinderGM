package ships;

public class Drift extends Part{

    private String rating;
    private String minPCU;
    private String maxSize;

    public Drift(String line){

        String[] split = line.split("\\?");
        rating = split[1];
        minPCU = split[2];
        maxSize = split[3];
        pcu = "-";
        partType = "Drift Engine";
    }
    public Drift(int none){
        super(none);
        rating = "-";
        minPCU = "-";
        maxSize = "-";
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nRating: "+rating+
                "\nMin PCU: "+minPCU+
                "\nMax Siz: "+maxSize+
                "\nCost: "+cost;
    }
}
