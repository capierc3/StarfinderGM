package Ships;

public class Shields extends Part {

    private String totalSP;
    private String regen;

    public Shields(String line){
        super(line);
        String[] split = line.split("\\?");
        name = name +" "+ split[1];
        totalSP = split[1];
        regen = split[2];
        partType = "Shield";
    }

    public String getRegen() {
        return regen;
    }

    public String getTotalSP() {
        return totalSP;
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nTotal SP: "+totalSP+
                "\nRegen: "+regen+
                toStringBTM();
    }
}
