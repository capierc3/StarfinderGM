package Equipment.Computer;

public class SecurityModule extends ComputerModule {

    private String dcIncrease;

    public SecurityModule(String line) {
        super(line, "Upgrade");
        String[] split = line.split("\\?");
        dcIncrease = split[1];
        price = split[2];
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
