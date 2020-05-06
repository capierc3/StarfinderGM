package Equipment.Computer;

public class ComputerModule {

    protected String feature;
    protected String price;
    protected String type;

    public ComputerModule(String line,String type){
        String[] split = line.split("\\?");
        this.feature = split[0];
        this.price = split[1];
        this.type = type;
    }

    public String toString(){
        return "Type: "+type+
                "\nFeature: "+feature+
                "\nPrice: "+price;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }
}
