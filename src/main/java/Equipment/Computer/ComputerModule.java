package Equipment.Computer;

public class ComputerModule {

    protected String feature;
    protected String price;
    protected String type;

    public ComputerModule(String feature, String price, String type){
        this.feature = feature;
        this.price = price;
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
