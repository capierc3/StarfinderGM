package Equipment.Computer;

public class SecurityModule extends ComputerModule {

    private String dcIncrease;

    public SecurityModule(String feature, String DC, String price, String type) {
        super(feature,price,type);
        dcIncrease = DC;
    }

    public String getDcIncrease() {
        return dcIncrease;
    }

    public String toString(){
        return "Type: "+type+
                "\nFeature: "+feature+
                "\nPrice: "+price+
                "\nDC Increase: "+dcIncrease;
    }
}
