package ships;

public class ShipScales implements ShipComponent {

    private String size;
    private String length;
    private String weight;
    private String modifier;

    public ShipScales(String line){
        String[] split = line.split("\\?");
        size = split[0];
        length = split[1];
        weight = split[2];
        modifier = split[3];
    }

    public String toString(){
        return "Size: "+size+
                "\nLength: "+length+
                "\nWeight: "+weight+
                "\nAC & TL Modifier: "+modifier;
    }


}
