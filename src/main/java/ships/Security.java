package ships;

public class Security extends Part{

    public Security(String line){
        super(line);
        pcu = "-";
        partType = "Self Destruct System";
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nCost: "+cost;
    }
}
