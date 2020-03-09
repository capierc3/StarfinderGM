package Equipment.Computer;

public class ShockModule extends ComputerModule {

    private String dc;
    private String damage;

    public ShockModule(String line) {
        super("Shock grid?V?", "Countermeasures");
        String[] split = line.split("\\?");
        String feature = this.feature;
        this.feature = feature+" "+ split[0];
        this.dc = split[1];
        this.damage = split[2];
        this.price = split[3];
    }

    public String toString() {
        return "Type: " + type +
                "\nFeature: " + feature +
                "\nPrice: " + price +
                "\nDC: " + dc +
                "\nDamage: " + damage;
    }
}
